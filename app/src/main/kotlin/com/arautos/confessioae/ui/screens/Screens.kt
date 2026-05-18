package com.arautos.confessioae.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.arautos.confessioae.data.model.Category
import com.arautos.confessioae.data.model.ExamEntry
import com.arautos.confessioae.data.model.ExaminationItem
import com.arautos.confessioae.data.repository.ExaminationDataProvider
import com.arautos.confessioae.ui.components.ExameCategoryBar
import com.arautos.confessioae.ui.theme.ThemeMode
import com.arautos.confessioae.ui.viewmodel.ExaminationViewModel
import com.arautos.confessioae.ui.viewmodel.ThemeViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Tela Inicial: Boas-vindas e orientações sobre o sacramento.
 */
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        // Início da moldura com fundo colorido e bordas
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
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
                                color = MaterialTheme.colorScheme.primary,
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
            /*
            Spacer(modifier = Modifier.height(16.dp))
            ConfessioButton(
                text = "Exame de Consciência",
                onClick = onNavigateToExame
            )
            */
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
fun ExameScreen(viewModel: ExaminationViewModel) {
    val categories = Category.entries
    var currentStep by remember { mutableIntStateOf(0) }
    val category = categories[currentStep]
    val items = ExaminationDataProvider.items.filter { it.category == category }
    val selectedIds by viewModel.selectedIds.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Parte 1: Barra de Opções Horizontal Fixa
        ExameCategoryBar(
            selectedCategory = category,
            onCategorySelected = { newCategory ->
                currentStep = categories.indexOf(newCategory)
            }
        )

        // Parte 2: Conteúdo (Descrição, Itens e Botão de Navegação)
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

            item {/*
                Spacer(modifier = Modifier.height(24.dp))
                ConfessioButton(
                    text = if (currentStep < categories.size - 1) "Próximo" else "Ir para Roteiro",
                    onClick = {
                        if (currentStep < categories.size - 1) currentStep++
                        else onFinish()
                    }
                )*/
                Spacer(modifier = Modifier.height(10.dp))
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
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface
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
 * Tela de Roteiro para Confissão: Guia espiritual passo a passo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidedConfessionScreen(viewModel: ExaminationViewModel, onFinish: () -> Unit) {
    val entries by viewModel.getAllListEntries().collectAsState(initial = emptyList())
    val lastDate by viewModel.lastConfessionDate.collectAsState()
    val userCondition by viewModel.userCondition.collectAsState()
    val confessedIds by viewModel.confessedIds.collectAsState()
    
    var showAcolhimento by remember { mutableStateOf(value = false) }

    var showCustomDialog by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<ExamEntry.Custom?>(null) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // 1. BLOCO “PREPARAÇÃO”
        ConfessionSectionTitle("ANTES DE CONFESSAR")

        // 1.1 Tempo desde a última confissão
        LastConfessionItem(
            lastDate = lastDate,
            onDateSelected = { viewModel.updateLastConfessionDate(it) }
        )

        // 1.2 Condição
        ConditionItem(
            condition = userCondition,
            onConditionSelected = { viewModel.updateUserCondition(it) }
        )

        // 2. BLOCO “Início”
        ConfessionSectionTitle("Início")

        Text(
            "Em nome do Pai, do Filho\ne do Espírito Santo.\nAmém.",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            "Padre, eu pequei.",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.fillMaxWidth()
        )

        if (lastDate != null) {
            Text(
                "Faz ${calculateTimeSinceLastConfession(lastDate)} que não confesso",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (!userCondition.isNullOrBlank()) {
            Text(
                "Sou $userCondition",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 2. BLOCO “CONFISSÃO DOS PECADOS”
        ConfessionSectionTitle("Confissão dos Pecados")

        if (entries.isEmpty()) {
            Text(
                "Nenhum pecado selecionado no exame.",
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                color = Color.Gray
            )
        } else {
            entries.forEach { entry ->
                when (entry) {
                    is ExamEntry.Standard -> {
                        val isConfessed = confessedIds.contains(entry.id)
                        Card(
                            onClick = { viewModel.toggleConfessed(entry.id) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                        ) {
                            Row(
                                modifier = Modifier.padding(6.dp).fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = isConfessed,
                                    onCheckedChange = { viewModel.toggleConfessed(entry.id) }
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = entry.text,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontStyle = FontStyle.Italic,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                    is ExamEntry.Custom -> {
                        val isConfessed = confessedIds.contains(entry.id)
                        Card(
                            onClick = { viewModel.toggleConfessed(entry.id) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                        ) {
                            Row(
                                modifier = Modifier.padding(6.dp).fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = isConfessed,
                                    onCheckedChange = { viewModel.toggleConfessed(entry.id) }
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = entry.text,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontStyle = FontStyle.Italic,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                IconButton(onClick = { 
                                    editingItem = entry
                                    showCustomDialog = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
                                }
                                IconButton(onClick = { viewModel.deleteCustomItem(entry.id) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Remover", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                    is ExamEntry.PermanentAdd -> {
                        Card(
                            onClick = {
                                editingItem = null
                                showCustomDialog = true
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = entry.text,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontStyle = FontStyle.Normal,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. TEXTO FINAL DOS PECADOS
        Text(
            "... e estes são os meus pecados.",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.fillMaxWidth()
        )

        // 4. ATO DE CONTRIÇÃO
        ConfessionSectionTitle("Ato de Contrição")
        ExpandableTextItem(
            title = "Ato de contrição",
            /*buttonText = "Mostrar oração",*/
            content = "Meu Deus, eu me arrependo de todo o coração de Vos ter ofendido, porque sois tão bom e amável. Prometo, com a vossa graça, não tornar a pecar e evitar as ocasiões de pecado. Amém."
        )

        // 5. ABSOLVIÇÃO (PADRE)
        ConfessionSectionTitle("Absolvição (oração feita pelo padre)")

        // 6. SINAL DA CRUZ FINAL
        Text(
            "Em nome do Pai, do Filho\ne do Espírito Santo.\nAmém.",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.fillMaxWidth()
        )

        // 7. PENITÊNCIA
        /*
        ConfessionSectionTitle("Penitência")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { penitenciaCumprida = !penitenciaCumprida },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = penitenciaCumprida, onCheckedChange = { penitenciaCumprida = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text("Penitência cumprida", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(24.dp))*/

        // 8. RECEBI A ABSOLVIÇÃO
        Button(
            onClick = {
                viewModel.updateLastConfessionDate(System.currentTimeMillis())
                showAcolhimento = true
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Recebi a absolvição", style = MaterialTheme.typography.titleMedium, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 9. BOTÃO FINAL
        /*
        TextButton(
            onClick = onClear,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Limpar exame e recomeçar", color = MaterialTheme.colorScheme.error)
        }
        */
        Spacer(modifier = Modifier.height(32.dp))
    }

    if (showAcolhimento) {
        AcolhimentoEspiritualScreen(onDismiss = {
            viewModel.clearAllData()
            onFinish()
        })
    }

    if (showCustomDialog) {
        CustomItemDialog(
            initialText = editingItem?.text ?: "",
            onDismiss = {
                showCustomDialog = false
                editingItem = null
            },
            onSave = { text ->
                if (editingItem != null) {
                    viewModel.updateCustomItem(editingItem!!.id, text)
                } else {
                    viewModel.addCustomItem(text)
                }
                showCustomDialog = false
                editingItem = null
            }
        )
    }
}

@Composable
fun ConfessionSectionTitle(title: String) {
    Text(
        title.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
fun ExpandableTextItem(
    title: String,
    content: String,
    buttonText: String? = null,
    icon: ImageVector? = null
) {
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null) {
                    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Icon(
                    if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
            
            if (buttonText != null && !expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.align(Alignment.Start),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text(buttonText, color = MaterialTheme.colorScheme.primary)
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        content,
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LastConfessionItem(lastDate: Long?, onDateSelected: (Long) -> Unit) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    
    Card(
        onClick = { showDatePicker = true },
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Última confissão:", style = MaterialTheme.typography.labelMedium)
                val dateDisplay = if (lastDate != null) {
                    SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault()).format(Date(lastDate))
                } else {
                    "Toque para selecionar a data"
                }
                Text(
                    text = dateDisplay,
                    style = MaterialTheme.typography.bodyLarge, 
                    fontWeight = FontWeight.Bold
                )
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { onDateSelected(it) }
                    showDatePicker = false
                }) { Text("Confirmar", fontSize = MaterialTheme.typography.labelMedium.fontSize * 2 ) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancelar", fontSize = MaterialTheme.typography.labelMedium.fontSize * 2 ) }
            },
            modifier = Modifier.scale(0.7f)
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}

@Composable
fun ConditionItem(condition: String?, onConditionSelected: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var isCustom by remember { mutableStateOf(false) }
    var customText by remember { mutableStateOf(condition ?: "") }

    val options = listOf(
        "Solteiro(a)", "Casado(a)", "Casado(a) com filhos",
        "Viúvo(a)", "Viúvo(a) com filhos",
        "Religioso(a)", "Seminarista"
    )

    Card(
        onClick = { 
            showDialog = true 
            isCustom = condition != null && condition !in options
        },
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Sou:", style = MaterialTheme.typography.labelMedium)
                Text(condition ?: "Toque para selecionar", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            modifier = Modifier.scale(0.9f),
            title = { Text("Selecione sua condição", fontSize = 26.sp) },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    options.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    isCustom = false
                                    onConditionSelected(option)
                                    showDialog = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = !isCustom && condition == option, onClick = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(option, fontSize = 18.sp)
                        }
                    }

                    // Opção Personalizada
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isCustom = true }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = isCustom, onClick = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Outra…", fontSize = 18.sp)
                    }

                    if (isCustom) {
                        OutlinedTextField(
                            value = customText,
                            onValueChange = { customText = it },
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                            placeholder = { Text("Descreva sua condição") },
                            singleLine = true
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (isCustom && customText.isNotBlank()) {
                        onConditionSelected(customText)
                    }
                    showDialog = false
                }) {
                    Text(if (isCustom) "Confirmar" else "Fechar", fontSize = 18.sp)
                }
            },
            dismissButton = {
                if (isCustom) {
                    TextButton(onClick = { showDialog = false }) { Text("Cancelar", fontSize = 18.sp) }
                }
            }
        )
    }
}

fun calculateTimeSinceLastConfession(lastDateMillis: Long?): String {
    if (lastDateMillis == null) return "n dias"
    
    val now = Calendar.getInstance()
    val last = Calendar.getInstance().apply { timeInMillis = lastDateMillis }
    
    // Inicializar horas para comparação de dias
    now.set(Calendar.HOUR_OF_DAY, 0); now.set(Calendar.MINUTE, 0); now.set(Calendar.SECOND, 0); now.set(Calendar.MILLISECOND, 0)
    last.set(Calendar.HOUR_OF_DAY, 0); last.set(Calendar.MINUTE, 0); last.set(Calendar.SECOND, 0); last.set(Calendar.MILLISECOND, 0)
    
    val diffMillis = now.timeInMillis - last.timeInMillis
    val diffDays = (diffMillis / (1000 * 60 * 60 * 24)).toInt()

    return when {
        diffDays < 7 -> "$diffDays ${if (diffDays == 1) "dia" else "dias"}"
        diffDays < 60 -> {
            val weeks = diffDays / 7
            "$weeks ${if (weeks == 1) "semana" else "semanas"}"
        }
        diffDays < 730 -> {
            val months = diffDays / 30
            "$months meses"
        }
        else -> {
            val years = diffDays / 365
            "$years anos"
        }
    }
}
/**
 * Tela de Absolvição
 */
@Composable
fun AcolhimentoEspiritualScreen(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            var startAnimation by remember { mutableStateOf(value = false) }
            LaunchedEffect(Unit) {
                startAnimation = true
            }

            val alpha by animateFloatAsState(
                targetValue = if (startAnimation) 1f else 0f,
                animationSpec = tween(durationMillis = 2000),
                label = "alpha"
            )

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = com.arautos.confessioae.R.drawable.deus_te_acolhe_com_amor2),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(0.9f),
                        //.alpha(alpha * 0.8f),
                    contentScale = ContentScale.Fit
                )

                // Overlay gradiente dourado suave
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f * alpha),
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f * alpha)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .alpha(alpha),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        "Deus te acolhe com amor.",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        "Seu Roteiro foi concluído.\nA misericórdia do Senhor renovou seu coração.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        "Vá em paz e cumpra sua penitência.\nO Senhor caminha com você.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Italic
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text("Amém", modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp), color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}

/**
 * Tela de Sobre: Informações institucionais e configurações de privacidade.
 */
@Composable
fun CustomItemDialog(
    initialText: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var text by remember { mutableStateOf(initialText) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.scale(0.9f),
        title = { Text(if (initialText.isEmpty()) "Adicionar Pecado ou Dúvida" else "Editar Pecado ou Dúvida", fontSize = 26.sp) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth().height(150.dp),
                placeholder = { Text("Descreva aqui seu pecado ou dúvida…", fontSize = 20.sp) },
                minLines = 3
            )
        },
        confirmButton = {
            TextButton(
                onClick = { if (text.isNotBlank()) onSave(text) },
                enabled = text.isNotBlank()
            ) {
                Text("Salvar", fontSize = 20.sp)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", fontSize = 20.sp)
            }
        }
    )
}

@Composable
fun SobreScreen(themeViewModel: ThemeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val themeMode by themeViewModel.themeMode.collectAsState()

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
            "Este aplicativo foi desenvolvido para auxiliar fiéis católicos na preparação para o Sacramento da Confissão, através de um Roteiro acolhedor.",
            style = MaterialTheme.typography.bodyLarge
        )

        Image(
            painter = painterResource(id = com.arautos.confessioae.R.drawable.icone_570),
            contentDescription = "Ícone Arautos",
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            "“Texto original do exame de consciência: Arautos do Evangelho”",
            style = MaterialTheme.typography.titleMedium,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.primary
        )

        // Seção de Tema
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Tema",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                ThemeOption("Claro", themeMode == ThemeMode.LIGHT) {
                    themeViewModel.setThemeMode(ThemeMode.LIGHT)
                }
                ThemeOption("Escuro", themeMode == ThemeMode.DARK) {
                    themeViewModel.setThemeMode(ThemeMode.DARK)
                }
                ThemeOption("Android", themeMode == ThemeMode.SYSTEM) {
                    themeViewModel.setThemeMode(ThemeMode.SYSTEM)
                }
            }
        }

        Text(
            "Aplicativo sem fins lucrativos desenvolvido para auxiliar fiéis na preparação para o Sacramento da Confissão.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ThemeOption(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}
