package com.arautos.confessioae.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
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
import com.arautos.confessioae.ui.theme.CormorantInfant
import com.arautos.confessioae.ui.theme.ThemeMode
import com.arautos.confessioae.ui.viewmodel.ExaminationViewModel
import com.arautos.confessioae.ui.viewmodel.ThemeViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.platform.LocalLocale
import kotlinx.coroutines.launch

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

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val isAtEnd by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val lastVisibleItem = visibleItemsInfo.lastOrNull()
                // Verifica se o último item visível é o penúltimo ou o último (contando o item da mini barra)
                lastVisibleItem != null && lastVisibleItem.index >= layoutInfo.totalItemsCount - 2
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ExameCategoryBar(
            selectedCategory = category,
            onCategorySelected = { newCategory ->
                currentStep = categories.indexOf(newCategory)
                coroutineScope.launch {
                    listState.scrollToItem(0)
                }
            }
        )

        LazyColumn(
            state = listState,
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
                AnimatedVisibility(
                    visible = isAtEnd,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        NavigationMiniBar(
                            currentCategory = category,
                            showNextAnimation = true, // Sempre animar quando visível no fim
                            onPrevious = {
                                if (currentStep > 0) {
                                    currentStep--
                                    coroutineScope.launch {
                                        listState.scrollToItem(0)
                                    }
                                }
                            },
                            onNext = {
                                if (currentStep < categories.size - 1) {
                                    currentStep++
                                    coroutineScope.launch {
                                        listState.scrollToItem(0)
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationMiniBar(
    currentCategory: Category,
    showNextAnimation: Boolean,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    val categories = Category.entries
    val currentIndex = categories.indexOf(currentCategory)
    val hasPrevious = currentIndex > 0
    val hasNext = currentIndex < categories.size - 1

    val categoryName = when (currentCategory) {
        Category.DEUS -> "Deus"
        Category.PROXIMO -> "Próximo"
        Category.CONSIGO -> "Comigo"
    }

    val infiniteTransition = rememberInfiniteTransition(label = "next_animation")
    val nextScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (showNextAnimation && hasNext) 1.3f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Seta Esquerda
            Box(modifier = Modifier.size(48.dp)) {
                if (hasPrevious) {
                    IconButton(onClick = onPrevious) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Anterior",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Nome da Categoria
            Text(
                text = categoryName,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = CormorantInfant,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )

            // Seta Direita
            Box(modifier = Modifier.size(48.dp)) {
                if (hasNext) {
                    IconButton(
                        onClick = onNext,
                        modifier = Modifier.scale(nextScale)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Próximo",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
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
    val explanations by viewModel.explanations.collectAsState()

    var showAcolhimento by remember { mutableStateOf(false) }
    var showCustomDialog by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<ExamEntry.Custom?>(null) }
    var showExplanationDialog by remember { mutableStateOf(false) }
    var explanationEntryId by remember { mutableStateOf<String?>(null) }
    var explanationSinText by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ConfessionSectionTitle("ANTES DE CONFESSAR")
        LastConfessionItem(
            lastDate = lastDate,
            onDateSelected = { viewModel.updateLastConfessionDate(it) }
        )
        ConditionItem(
            condition = userCondition,
            onConditionSelected = { viewModel.updateUserCondition(it) }
        )

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
                    text = "pecado não relacionado ou uma dúvida",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }

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
                        ConfessionSinItem(
                            entry = entry,
                            isConfessed = confessedIds.contains(entry.id),
                            explanation = explanations[entry.id],
                            onToggleConfessed = { viewModel.toggleConfessed(entry.id) },
                            onAddEditExplanation = {
                                explanationEntryId = entry.id
                                explanationSinText = entry.text
                                showExplanationDialog = true
                            }
                        )
                    }
                    is ExamEntry.Custom -> {
                        ConfessionSinItem(
                            entry = entry,
                            isConfessed = confessedIds.contains(entry.id),
                            explanation = explanations[entry.id],
                            onToggleConfessed = { viewModel.toggleConfessed(entry.id) },
                            onAddEditExplanation = {
                                explanationEntryId = entry.id
                                explanationSinText = entry.text
                                showExplanationDialog = true
                            },
                            onEditCustom = {
                                editingItem = entry
                                showCustomDialog = true
                            },
                            onDeleteCustom = { viewModel.deleteCustomItem(entry.id) }
                        )
                    }
                    else -> {}
                }
            }
        }

        Text(
            "... e estes são os meus pecados.",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.fillMaxWidth()
        )

        ConfessionSectionTitle("Ato de Contrição")
        ExpandableTextItem(
            title = "Ato de contrição",
            content = "Meu Deus, eu me arrependo de todo o coração de Vos ter ofendido, porque sois tão bom e amável. Prometo, com a vossa graça, não tornar a pecar e evitar as ocasiões de pecado. Amém."
        )

        ConfessionSectionTitle("Absolvição (oração feita pelo padre)")
        Text(
            "Em nome do Pai, do Filho\ne do Espírito Santo.\nAmém.",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.fillMaxWidth()
        )

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

    if (showExplanationDialog && explanationEntryId != null) {
        ExplanationDialog(
            sinText = explanationSinText,
            initialExplanation = explanations[explanationEntryId] ?: "",
            onDismiss = {
                showExplanationDialog = false
                explanationEntryId = null
            },
            onSave = { text ->
                viewModel.saveExplanation(explanationEntryId!!, text)
                showExplanationDialog = false
                explanationEntryId = null
            }
        )
    }
}

@Composable
fun ConfessionSinItem(
    entry: ExamEntry,
    isConfessed: Boolean,
    explanation: String?,
    onToggleConfessed: () -> Unit,
    onAddEditExplanation: () -> Unit,
    onEditCustom: (() -> Unit)? = null,
    onDeleteCustom: (() -> Unit)? = null
) {
    val hasExplanation = !explanation.isNullOrBlank()
    Card(
        onClick = onToggleConfessed,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Checkbox(
                checked = isConfessed,
                onCheckedChange = { onToggleConfessed() }
            )
            Column(modifier = Modifier.weight(1f).padding(top = 12.dp)) {
                if (hasExplanation) {
                    Text(
                        text = explanation,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = entry.text,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    )
                } else {
                    Text(
                        text = entry.text,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onAddEditExplanation) {
                    Icon(
                        imageVector = if (hasExplanation) Icons.Default.Edit else Icons.Default.Add,
                        contentDescription = "Adicionar/Editar explicação",
                        tint = if (hasExplanation) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                    )
                }
                if (onEditCustom != null) {
                    IconButton(onClick = onEditCustom) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
                    }
                }
                if (onDeleteCustom != null) {
                    IconButton(onClick = onDeleteCustom) {
                        Icon(Icons.Default.Delete, contentDescription = "Remover", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
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
                modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded },
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
                    SimpleDateFormat("dd/MMM/yyyy", LocalLocale.current.platformLocale).format(Date(lastDate))
                } else {
                    "Toque para selecionar a data"
                }
                Text(text = dateDisplay, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
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
                }) { Text("Confirmar", fontSize = 18.sp) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancelar", fontSize = 18.sp) }
            },
            modifier = Modifier.scale(0.8f)
        ) {
            DatePicker(state = datePickerState)
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
                            modifier = Modifier.fillMaxWidth().clickable {
                                isCustom = false
                                onConditionSelected(option)
                                showDialog = false
                            }.padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = !isCustom && condition == option, onClick = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(option, fontSize = 18.sp)
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { isCustom = true }.padding(vertical = 12.dp),
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
                    if (isCustom && customText.isNotBlank()) onConditionSelected(customText)
                    showDialog = false
                }) { Text(if (isCustom) "Confirmar" else "Fechar", fontSize = 18.sp) }
            },
            dismissButton = {
                if (isCustom) {
                    TextButton(onClick = { showDialog = false }) { Text("Cancelar", fontSize = 18.sp) }
                }
            }
        )
    }
}

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
            var startAnimation by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) { startAnimation = true }
            val alpha by animateFloatAsState(
                targetValue = if (startAnimation) 1f else 0f,
                animationSpec = tween(durationMillis = 2000),
                label = "alpha"
            )
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = com.arautos.confessioae.R.drawable.deus_te_acolhe_com_amor2),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().scale(0.9f),
                    contentScale = ContentScale.Fit
                )
                Box(
                    modifier = Modifier.fillMaxSize().background(
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
                    modifier = Modifier.fillMaxSize().padding(24.dp).alpha(alpha),
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
            ) { Text("Salvar", fontSize = 20.sp) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar", fontSize = 20.sp) }
        }
    )
}

@Composable
fun ExplanationDialog(
    sinText: String,
    initialExplanation: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var text by remember { mutableStateOf(initialExplanation) }
    val speechLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
            if (spokenText != null) {
                text = if (text.isBlank()) spokenText else "$text $spokenText"
            }
        }
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = sinText, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 100.dp),
                    label = { Text("Sua observação") },
                    placeholder = { Text("Toque no microfone para ditar ou digite aqui...") }
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    FilledTonalIconButton(
                        onClick = {
                            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                            }
                            try { speechLauncher.launch(intent) } catch (e: Exception) {}
                        }
                    ) { Icon(Icons.Default.Mic, contentDescription = "Ditar") }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onSave(text) }) { Text("Salvar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@Composable
fun SobreScreen(themeViewModel: ThemeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val themeMode by themeViewModel.themeMode.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Sobre o Aplicativo", style = MaterialTheme.typography.titleLarge)
        Text("Este aplicativo foi desenvolvido para auxiliar fiéis católicos na preparação para o Sacramento da Confissão, através de um Roteiro acolhedor.", style = MaterialTheme.typography.bodyLarge)
        Image(
            painter = painterResource(id = com.arautos.confessioae.R.drawable.icone_570),
            contentDescription = "Ícone Arautos",
            modifier = Modifier.size(250.dp).align(Alignment.CenterHorizontally)
        )
        Text("“Texto original do exame de consciência: Arautos do Evangelho”", style = MaterialTheme.typography.titleMedium, fontStyle = FontStyle.Italic, color = MaterialTheme.colorScheme.primary)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Tema", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))
                ThemeOption("Claro", themeMode == ThemeMode.LIGHT) { themeViewModel.setThemeMode(ThemeMode.LIGHT) }
                ThemeOption("Escuro", themeMode == ThemeMode.DARK) { themeViewModel.setThemeMode(ThemeMode.DARK) }
                ThemeOption("Android", themeMode == ThemeMode.SYSTEM) { themeViewModel.setThemeMode(ThemeMode.SYSTEM) }
            }
        }
        Text("Aplicativo sem fins lucrativos desenvolvido para auxiliar fiéis na preparação para o Sacramento da Confissão.", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ThemeOption(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}

fun calculateTimeSinceLastConfession(lastDateMillis: Long?): String {
    if (lastDateMillis == null) return "n dias"
    val now = Calendar.getInstance()
    val last = Calendar.getInstance().apply { timeInMillis = lastDateMillis }
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
