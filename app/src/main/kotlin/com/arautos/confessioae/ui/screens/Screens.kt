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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.arautos.confessioae.data.model.Category
import com.arautos.confessioae.data.model.ExamEntry
import com.arautos.confessioae.data.model.ExaminationItem
import com.arautos.confessioae.data.repository.ExaminationDataProvider
import com.arautos.confessioae.ui.components.ConfessioButton
import com.arautos.confessioae.ui.components.ExameCategoryBar
import com.arautos.confessioae.ui.viewmodel.ExaminationViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.delay

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
fun ExameScreen(viewModel: ExaminationViewModel, onFinish: () -> Unit) {
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

        // Parte 3: Conteúdo (Descrição, Itens e Botão de Navegação)
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
                    text = if (currentStep < categories.size - 1) "Próximo" else "Ir para Roteiro",
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
            containerColor = if (isSelected) Color(0xFFDBD9D2) else MaterialTheme.colorScheme.surface
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
fun GuidedConfessionScreen(viewModel: ExaminationViewModel, onClear: () -> Unit, onFinish: () -> Unit) {
    val entries by viewModel.getAllListEntries().collectAsState(initial = emptyList())
    val lastDate by viewModel.lastConfessionDate.collectAsState()
    val userCondition by viewModel.userCondition.collectAsState()
    
    var showAcolhimento by remember { mutableStateOf(false) }
    var penitenciaCumprida by rememberSaveable { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
       /* Text(
            "ROTEIRO",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFA97F1A),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )*/

        // 1. BLOCO “PREPARAÇÃO”
        ConfessionSectionTitle("Preparação")

        // 1.1 Sinal da Cruz
        Text(
            "Em nome do Pai, do Filho e do Espírito Santo. Amém.",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        // 1.2 Sinal da Cruz
        Text(
            "Padre, eu pequei.",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )

        // 1.3 Tempo desde a última confissão
        LastConfessionItem(
            lastDate = lastDate,
            onDateSelected = { viewModel.updateLastConfessionDate(it) }
        )

        // 1.4 Minha condição
        ConditionItem(
            condition = userCondition,
            onConditionSelected = { viewModel.updateUserCondition(it) }
        )

        // 2. BLOCO “CONFISSÃO DOS PECADOS”
        ConfessionSectionTitle("Confissão dos Pecados")

        if (entries.none { it is ExamEntry.Standard || it is ExamEntry.Custom }) {
            Text(
                "Nenhum pecado selecionado no exame.",
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                color = Color.Gray
            )
        } else {
            entries.filter { it is ExamEntry.Standard || it is ExamEntry.Custom }.forEach { entry ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, Color(0xFFDBD9D2))
                ) {
                    Text(
                        text = entry.text,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        // 5. TEXTO FINAL DOS PECADOS
        Text(
            "Estes são os meus pecados.",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // 6. ATO DE CONTRIÇÃO
        ConfessionSectionTitle("Ato de Contrição")
        ExpandableTextItem(
            title = "Ato de contrição",
            buttonText = "Mostrar oração",
            content = "Meu Deus, eu me arrependo de todo o coração de Vos ter ofendido, porque sois tão bom e amável. Prometo, com a vossa graça, não tornar a pecar e evitar as ocasiões de pecado. Amém."
        )

        // 7. ABSOLVIÇÃO (PADRE)
        ConfessionSectionTitle("Absolvição")
        ExpandableTextItem(
            title = "Absolvição",
            buttonText = "Mostrar fórmula",
            content = "Deus, Pai de misericórdia, que pela morte e ressurreição de seu Filho reconciliou o mundo consigo e enviou o Espírito Santo para remissão dos pecados, te conceda, pelo ministério da Igreja, o perdão e a paz. E eu te absolvo dos teus pecados, em nome do Pai, do Filho e do Espírito Santo."
        )

        // 8. SINAL DA CRUZ FINAL
        ExpandableTextItem(
            title = "Sinal da Cruz",
            content = "Em nome do Pai, do Filho e do Espírito Santo. Amém.",
            icon = Icons.Default.Check
        )

        // 9. PENITÊNCIA
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

        Spacer(modifier = Modifier.height(24.dp))

        // 10. BOTÃO FINAL
        Button(
            onClick = {
                viewModel.updateLastConfessionDate(System.currentTimeMillis())
                showAcolhimento = true
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA97F1A)),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Recebi a absolvição", style = MaterialTheme.typography.titleMedium, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(
            onClick = onClear,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Limpar exame e recomeçar", color = MaterialTheme.colorScheme.error)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }

    if (showAcolhimento) {
        AcolhimentoEspiritualScreen(onDismiss = {
            showAcolhimento = false
            viewModel.clearAllData()
            onFinish()
        })
    }
}

@Composable
fun ConfessionSectionTitle(title: String) {
    Text(
        title.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        color = Color(0xFFA97F1A),
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color(0xFFDBD9D2))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null) {
                    Icon(icon, contentDescription = null, tint = Color(0xFFA97F1A), modifier = Modifier.size(20.dp))
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
                    border = BorderStroke(1.dp, Color(0xFFA97F1A))
                ) {
                    Text(buttonText, color = Color(0xFFA97F1A))
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
    
    val dateText = calculateTimeSinceLastConfession(lastDate)

    Card(
        onClick = { showDatePicker = true },
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, Color(0xFFDBD9D2))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                /*Text("Tempo desde a última Confissão", style = MaterialTheme.typography.labelMedium, color = Color.Gray)*/
                Text(dateText, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
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
                }) { Text("Confirmar") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancelar") }
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
        border = BorderStroke(1.dp, Color(0xFFDBD9D2))
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
            modifier = Modifier.scale(0.7f),
            title = { Text("Selecione sua condição") },
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
                            Text(option)
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
                        Text("Outra…")
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
                    Text(if (isCustom) "Confirmar" else "Fechar")
                }
            },
            dismissButton = {
                if (isCustom) {
                    TextButton(onClick = { showDialog = false }) { Text("Cancelar") }
                }
            }
        )
    }
}

fun calculateTimeSinceLastConfession(lastDateMillis: Long?): String {
    if (lastDateMillis == null) return "Faz n dias que não Confesso."
    
    val now = Calendar.getInstance()
    val last = Calendar.getInstance().apply { timeInMillis = lastDateMillis }
    
    // Zerar horas para comparação de dias
    now.set(Calendar.HOUR_OF_DAY, 0); now.set(Calendar.MINUTE, 0); now.set(Calendar.SECOND, 0); now.set(Calendar.MILLISECOND, 0)
    last.set(Calendar.HOUR_OF_DAY, 0); last.set(Calendar.MINUTE, 0); last.set(Calendar.SECOND, 0); last.set(Calendar.MILLISECOND, 0)
    
    val diffMillis = now.timeInMillis - last.timeInMillis
    val diffDays = (diffMillis / (1000 * 60 * 60 * 24)).toInt()

    return when {
        diffDays < 7 -> "Faz $diffDays ${if (diffDays == 1) "dia" else "dias"} que não Confesso."
        diffDays < 60 -> {
            val weeks = diffDays / 7
            "Faz $weeks ${if (weeks == 1) "semana" else "semanas"} que não Confesso."
        }
        diffDays < 730 -> {
            val months = diffDays / 30
            "Faz $months ${if (months == 1) "mês" else "meses"} que não Confesso."
        }
        else -> {
            val years = diffDays / 365
            "Faz $years ${if (years == 1) "ano" else "anos"} que não Confesso."
        }
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
            color = Color(0xFF1A1A1A)
        ) {
            var startAnimation by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                startAnimation = true
            }

            val alpha by animateFloatAsState(
                targetValue = if (startAnimation) 1f else 0f,
                animationSpec = tween(durationMillis = 2000),
                label = "alpha"
            )

            Box(modifier = Modifier.fillMaxSize()) {
                // Imagem de fundo (Usando icone_570 como placeholder já que a imagem específica não foi encontrada)
                Image(
                    painter = painterResource(id = com.arautos.confessioae.R.drawable.icone_570),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(alpha * 0.4f),
                    contentScale = ContentScale.Fit
                )
                
                // Overlay gradiente dourado suave
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFA97F1A).copy(alpha = 0.1f * alpha),
                                    Color.Transparent,
                                    Color(0xFFA97F1A).copy(alpha = 0.2f * alpha)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .alpha(alpha),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Deus te acolhe com amor.",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color(0xFFD4AF37),
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        "Seu Roteiro foi concluído.\nA misericórdia do Senhor renovou seu coração.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(48.dp))
                    
                    Text(
                        "Vá em paz e cumpra sua penitência.\nO Senhor caminha com você.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Italic
                    )
                    
                    Spacer(modifier = Modifier.height(64.dp))
                    
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA97F1A)),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text("Amém", modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp))
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
fun SobreScreen(viewModel: ExaminationViewModel, onClear: () -> Unit) {
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


        Text(
            "Aplicativo sem fins lucrativos desenvolvido para auxiliar fiéis na preparação para o Sacramento da Confissão.",
            style = MaterialTheme.typography.bodyMedium
        )

        


      /*  ConfessioButton(
            text = "Excluir Todos os Dados",
            onClick = { 
                viewModel.clearAllData()
                onClear()
            },
            containerColor = MaterialTheme.colorScheme.error
        )*/
    }
}
