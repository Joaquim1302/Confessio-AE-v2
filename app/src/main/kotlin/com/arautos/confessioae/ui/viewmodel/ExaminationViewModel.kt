package com.arautos.confessioae.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arautos.confessioae.data.model.ExaminationItem
import com.arautos.confessioae.data.repository.ExaminationDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Gerencia o estado do exame de consciência e preferências de privacidade.
 * 
 * Decisão Arquitetural: O ViewModel mantém apenas os IDs dos itens selecionados para otimizar 
 * a memória e facilitar a reativação do estado. Os dados completos dos itens são resolvidos 
 * sob demanda através do [ExaminationDataProvider].
 */
class ExaminationViewModel : ViewModel() {
    private val _selectedIds = MutableStateFlow<Set<String>>(emptySet())
    /**
     * Conjunto de IDs dos pecados identificados pelo fiel.
     * Usamos um Set para garantir unicidade e performance em buscas.
     */
    val selectedIds: StateFlow<Set<String>> = _selectedIds.asStateFlow()

    private val _isLocked = MutableStateFlow(false)
    /**
     * Estado da trava de privacidade. 
     * Implementado como um simulacro para validação de UX antes da integração com BiometricPrompt.
     */
    val isLocked: StateFlow<Boolean> = _isLocked.asStateFlow()

    fun toggleItem(id: String) {
        _selectedIds.value = if (_selectedIds.value.contains(id)) {
            _selectedIds.value - id
        } else {
            _selectedIds.value + id
        }
    }

    fun getSelectedItems(): List<ExaminationItem> {
        return ExaminationDataProvider.items.filter { _selectedIds.value.contains(it.id) }
    }

    fun clearAllData() {
        _selectedIds.value = emptySet()
    }

    fun toggleLock() {
        _isLocked.value = !_isLocked.value
    }
}
