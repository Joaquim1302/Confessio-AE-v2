package com.arautos.confessioae.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.arautos.confessioae.ui.theme.ConfessioAETheme
import com.arautos.confessioae.ui.theme.Ink
import com.arautos.confessioae.ui.theme.Montserrat

/**
 * Componente de botão padronizado do aplicativo.
 * 
 * Decisão de Design: O uso de [RectangleShape] e tipografia em caixa alta visa evocar uma 
 * estética solene e sóbria, adequada para um aplicativo de natureza religiosa e introspectiva.
 * A ausência de sombras (elevation = 0.dp) reforça o estilo minimalista e plano do projeto.
 */
@Composable
fun ConfessioButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Ink,
    contentColor: Color = Color.White,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp), // Reduzido de 56.dp para 48.dp para padronizar a altura
        shape = RectangleShape,
        contentPadding = PaddingValues(0.dp), // Remove padding interno para garantir altura exata de 48.dp
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp,
        )
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelLarge.copy(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp,
                fontSize = 14.sp
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFAF7F2)
@Composable
fun ConfessioButtonPreview() {
    ConfessioAETheme {
        ConfessioButton(
            text = "Exame de Consciência",
            onClick = {}
        )
    }
}
