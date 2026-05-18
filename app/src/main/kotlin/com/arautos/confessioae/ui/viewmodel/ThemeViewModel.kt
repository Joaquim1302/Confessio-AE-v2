package com.arautos.confessioae.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arautos.confessioae.data.repository.UserPreferences
import com.arautos.confessioae.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val userPrefs = UserPreferences(application)

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    init {
        viewModelScope.launch {
            userPrefs.getThemeMode.collect { mode ->
                _themeMode.value = mode
            }
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            _themeMode.value = mode
            userPrefs.saveThemeMode(mode)
        }
    }
}
