package com.arautos.confessioae.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arautos.confessioae.ui.components.ConfessioTopBar
import com.arautos.confessioae.ui.components.NavigationTabs
import com.arautos.confessioae.ui.viewmodel.ExaminationViewModel

/**
 * Tela Principal: Gerencia o Scaffold, a TopBar e a navegação entre as abas.
 */
@Composable
fun MainScreen() {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val viewModel: ExaminationViewModel = viewModel()

    Scaffold(
        topBar = {
            // Início da área superior fixa (Título, Subtítulo e Abas)
            Column {
                ConfessioTopBar()
                NavigationTabs(
                    selectedTab = selectedTab,
                ) {
                    selectedTab = it
                }
            }
            // Fim da área superior fixa
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            when (selectedTab) {
                0 -> HomeScreen()
                1 -> ExameScreen(viewModel) { selectedTab = 2 }
                2 -> GuidedConfessionScreen(viewModel) { selectedTab = 0 }
                3 -> SobreScreen()
            }
        }
    }
}
