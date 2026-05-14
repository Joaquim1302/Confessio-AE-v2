package com.arautos.confessioae.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arautos.confessioae.data.model.Category
import com.arautos.confessioae.data.model.ExaminationItem
import com.arautos.confessioae.data.repository.ExaminationDataProvider
import com.arautos.confessioae.ui.components.ConfessioButton
import com.arautos.confessioae.ui.viewmodel.ExaminationViewModel

/**
 * Tela Inicial: Boas-vindas e orientações sobre o sacramento.
 */
@Composable
fun HomeScreen(onNavigateToExame: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        // Início da moldura com fundo colorido e bordas
        Surface(
            color = Color(0xFFEEEAE4),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                Column(
                    modifier = Modifier.padding(vertical = 32.dp, horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "O Sacramento da Confissão",
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "“Vinde a Mim todos vós que estais cansados e oprimidos e Eu vos aliviarei” (Mt 11,28)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
            }
        }
        // Fim da moldura

        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Para fazer uma boa confissão",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val listItems = listOf(
                    "1" to "Ter confiança no perdão de Deus",
                    "2" to "Fazer bem o exame de consciência",
                    "3" to "Ter dor e arrependimento dos próprios pecados",
                    "4" to "Fazer o propósito de nunca mais os cometer",
                    "5" to "Dizer os próprios pecados ao confessor, sem esconder nenhum, evitando divagações",
                    "6" to "Referir, em relação aos pecados graves, quanto possível, o seu número",
                    "7" to "Aceitar a penitência imposta pelo confessor"
                )
                listItems.forEach { (number, text) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = number,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFFA97F1A),
                                fontSize = MaterialTheme.typography.bodySmall.fontSize * 0.7f,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.width(10.dp)
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            ConfessioButton(
                text = "Exame de Consciência",
                onClick = onNavigateToExame
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Texto original: Arautos do Evangelho",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Tela de Exame: Fluxo guiado para o exame de consciência por categorias.
 */
@Composable
fun ExameScreen(viewModel: ExaminationViewModel, onFinish: () -> Unit) {
    val categories = Category.entries
    var currentStep by remember { mutableIntStateOf(0) }
    val category = categories[currentStep]
    val items = ExaminationDataProvider.items.filter { it.category == category }
    val selectedIds by viewModel.selectedIds.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
    /**    LinearProgressIndicator(
            progress = { (currentStep + 1) / categories.size.toFloat() },
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        )
    */
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentStep > 0) {
                IconButton(onClick = { currentStep-- }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                }
            }
            Text(
                text = category.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = if (currentStep > 0) 8.dp else 0.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    category.description,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(items) { item ->
                ExaminationItemRow(
                    item = item,
                    isSelected = selectedIds.contains(item.id),
                    onToggle = { viewModel.toggleItem(item.id) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                ConfessioButton(
                    text = if (currentStep < categories.size - 1) "Próximo" else "Ver Minha Lista",
                    onClick = {
                        if (currentStep < categories.size - 1) currentStep++
                        else onFinish()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ExaminationItemRow(item: ExaminationItem, isSelected: Boolean, onToggle: () -> Unit) {
    Card(
        onClick = onToggle,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Checkbox(checked = isSelected, onCheckedChange = { onToggle() })
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(item.text, style = MaterialTheme.typography.bodyLarge)
                item.note?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        it,
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Tela de Lista: Exibe os pecados selecionados para auxiliar na confissão.
 */
@Composable
fun ListaScreen(viewModel: ExaminationViewModel, onClear: () -> Unit) {
    val selectedItems = viewModel.getSelectedItems()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            "Minha Lista para Confissão",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        if (selectedItems.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhum item marcado.", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(selectedItems) { item ->
                    Text(
                        "• ${item.text}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                    ConfessioButton(
                        text = "Apagar todos os dados",
                        onClick = { 
                            viewModel.clearAllData()
                            onClear()
                        },
                        containerColor = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Aviso: Seus dados estão salvos apenas localmente.",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

/**
 * Tela Sobre: Informações institucionais e configurações de privacidade.
 */
@Composable
fun SobreScreen(viewModel: ExaminationViewModel, onClear: () -> Unit) {
    val isLocked by viewModel.isLocked.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Sobre o Aplicativo",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            "Este aplicativo foi desenvolvido para auxiliar fiéis católicos na preparação para o Sacramento da Confissão.",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            "“Texto original do exame de consciência: Arautos do Evangelho”",
            style = MaterialTheme.typography.titleMedium,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            "Aplicativo sem fins lucrativos desenvolvido para auxiliar fiéis na preparação para o Sacramento da Confissão.",
            style = MaterialTheme.typography.bodyMedium
        )
        
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Bloquear ao iniciar (Simulado)", style = MaterialTheme.typography.bodyLarge)
            Switch(checked = isLocked, onCheckedChange = { viewModel.toggleLock() })
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        ConfessioButton(
            text = "Excluir Todos os Dados Locais",
            onClick = { 
                viewModel.clearAllData()
                onClear()
            },
            containerColor = MaterialTheme.colorScheme.error
        )
    }
}
