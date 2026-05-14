package com.arautos.confessioae.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arautos.confessioae.data.model.ExamEntry
import com.arautos.confessioae.data.model.ExaminationItem
import com.arautos.confessioae.data.repository.ConfessioRepository
import com.arautos.confessioae.data.repository.ExaminationDataProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ExaminationViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ConfessioRepository(application)

    private val _selectedIds = MutableStateFlow<Set<String>>(emptySet())
    val selectedIds: StateFlow<Set<String>> = _selectedIds.asStateFlow()

    private val _customItems = MutableStateFlow<List<ExamEntry.Custom>>(emptyList())
    val customItems: StateFlow<List<ExamEntry.Custom>> = _customItems.asStateFlow()

    private val _isLocked = MutableStateFlow(false)
    val isLocked: StateFlow<Boolean> = _isLocked.asStateFlow()

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
                    } catch (e: Exception) {
                        _customItems.value = emptyList()
                    }
                }
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

    fun getSelectedItems(): List<ExaminationItem> {
        return ExaminationDataProvider.items.filter { _selectedIds.value.contains(it.id) }
    }

    // Combine standard and custom items for the list screen
    fun getAllListEntries(): Flow<List<ExamEntry>> {
        return combine(selectedIds, customItems) { ids, customs ->
            val standardEntries = ExaminationDataProvider.items
                .filter { ids.contains(it.id) }
                .map { ExamEntry.Standard(it.id, it.text) }
            
            standardEntries + customs + ExamEntry.PermanentAdd
        }
    }

    fun clearAllData() {
        viewModelScope.launch {
            _selectedIds.value = emptySet()
            _customItems.value = emptyList()
            repository.clearAll()
        }
    }

    fun toggleLock() {
        _isLocked.value = !_isLocked.value
    }
}
