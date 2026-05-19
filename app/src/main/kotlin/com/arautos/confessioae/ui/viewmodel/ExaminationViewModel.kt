package com.arautos.confessioae.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arautos.confessioae.data.model.ExamEntry
import com.arautos.confessioae.data.repository.ConfessioRepository
import com.arautos.confessioae.data.repository.ExaminationDataProvider
import com.arautos.confessioae.data.repository.UserPreferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ExaminationViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ConfessioRepository(application)
    private val userPrefs = UserPreferences(application)

    private val _selectedIds = MutableStateFlow<Set<String>>(emptySet())
    val selectedIds: StateFlow<Set<String>> = _selectedIds.asStateFlow()

    private val _customItems = MutableStateFlow<List<ExamEntry.Custom>>(emptyList())
    val customItems: StateFlow<List<ExamEntry.Custom>> = _customItems.asStateFlow()

    private val _lastConfessionDate = MutableStateFlow<Long?>(null)
    val lastConfessionDate: StateFlow<Long?> = _lastConfessionDate.asStateFlow()

    private val _userCondition = MutableStateFlow<String?>(null)
    val userCondition: StateFlow<String?> = _userCondition.asStateFlow()

    private val _confessedIds = MutableStateFlow<Set<String>>(emptySet())
    val confessedIds: StateFlow<Set<String>> = _confessedIds.asStateFlow()

    private val _explanations = MutableStateFlow<Map<String, String>>(emptyMap())
    val explanations: StateFlow<Map<String, String>> = _explanations.asStateFlow()

    private val _penitenceDone = MutableStateFlow(false)
    val penitenceDone: StateFlow<Boolean> = _penitenceDone.asStateFlow()

    init {
        viewModelScope.launch {
            repository.selectedIds.collect { ids ->
                _selectedIds.value = ids
            }
        }
        viewModelScope.launch {
            repository.customItemsJson.collect { json ->
                if (json != null) {
                    try {
                        _customItems.value = Json.decodeFromString<List<ExamEntry.Custom>>(json)
                    } catch (_: Exception) {
                        _customItems.value = emptyList()
                    }
                }
            }
        }
        viewModelScope.launch {
            repository.explanationsJson.collect { json ->
                if (json != null) {
                    try {
                        _explanations.value = Json.decodeFromString<Map<String, String>>(json)
                    } catch (_: Exception) {
                        _explanations.value = emptyMap()
                    }
                }
            }
        }
        viewModelScope.launch {
            userPrefs.getLastConfession.collect { date ->
                _lastConfessionDate.value = date
            }
        }
        viewModelScope.launch {
            userPrefs.getCondition.collect { condition ->
                _userCondition.value = condition
            }
        }
        viewModelScope.launch {
            userPrefs.getPenitenceDone.collect { done ->
                _penitenceDone.value = done
            }
        }
    }

    fun toggleItem(id: String) {
        viewModelScope.launch {
            val current = _selectedIds.value
            val next = if (current.contains(id)) current - id else current + id
            _selectedIds.value = next
            repository.updateSelectedIds(next)
        }
    }

    fun addCustomItem(text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            val newItem = ExamEntry.Custom(text = text)
            val updated = _customItems.value + newItem
            _customItems.value = updated
            repository.updateCustomItemsJson(Json.encodeToString(updated))
        }
    }

    fun updateCustomItem(id: String, newText: String) {
        if (newText.isBlank()) return
        viewModelScope.launch {
            val updated = _customItems.value.map {
                if (it.id == id) it.copy(text = newText) else it
            }
            _customItems.value = updated
            repository.updateCustomItemsJson(Json.encodeToString(updated))
        }
    }

    fun deleteCustomItem(id: String) {
        viewModelScope.launch {
            val updated = _customItems.value.filter { it.id != id }
            _customItems.value = updated
            repository.updateCustomItemsJson(Json.encodeToString(updated))
        }
    }

    fun updateLastConfessionDate(dateMillis: Long) {
        viewModelScope.launch {
            _lastConfessionDate.value = dateMillis
            userPrefs.saveLastConfession(dateMillis)
        }
    }

    fun updateUserCondition(condition: String) {
        viewModelScope.launch {
            _userCondition.value = condition
            userPrefs.saveCondition(condition)
        }
    }

    fun toggleConfessed(id: String) {
        val current = _confessedIds.value
        _confessedIds.value = if (current.contains(id)) current - id else current + id
    }

    fun updatePenitenceDone(done: Boolean) {
        viewModelScope.launch {
            _penitenceDone.value = done
            userPrefs.savePenitenceDone(done)
        }
    }

    fun saveExplanation(id: String, text: String) {
        viewModelScope.launch {
            val current = _explanations.value.toMutableMap()
            if (text.isBlank()) {
                current.remove(id)
            } else {
                current[id] = text
            }
            _explanations.value = current
            repository.updateExplanationsJson(Json.encodeToString(current))
        }
    }

    // Combine standard and custom items for the list screen
    fun getAllListEntries(): Flow<List<ExamEntry>> {
        return combine(selectedIds, customItems) { ids, customs ->
            val standardEntries = ExaminationDataProvider.items.asSequence()
                .filter { ids.contains(it.id) }
                .map { ExamEntry.Standard(it.id, it.text) }
                .toList()
            
            standardEntries + customs
        }
    }

    fun clearAllData() {
        viewModelScope.launch {
            _selectedIds.value = emptySet()
            _customItems.value = emptyList()
            _confessedIds.value = emptySet()
            _explanations.value = emptyMap()
            _penitenceDone.value = false
            userPrefs.savePenitenceDone(false)
            repository.clearAll()
        }
    }
}
