package com.arautos.confessioae.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arautos.confessioae.R
import com.arautos.confessioae.data.model.Category

@Composable
fun ExameCategoryBar(
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
) {
    val categories = Category.entries
    val cormorantItalic = FontFamily(Font(R.font.cormorant_infant_italic))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        categories.forEach { category ->
            val isSelected = category == selectedCategory
            val displayName = when (category) {
                Category.DEUS -> "Deus"
                Category.PROXIMO -> "Próximo"
                Category.CONSIGO -> "Comigo"
            }

            Surface(
                onClick = { onCategorySelected(category) },
                modifier = Modifier.weight(1f),
                shape = RectangleShape,
                color = if (isSelected) Color(0xFFEAE1CA) else MaterialTheme.colorScheme.surface,
                contentColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                border = BorderStroke(
                    width = 1.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                ),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(vertical = 8.dp),
                ) {
                    Text(
                        text = displayName,
                        fontFamily = cormorantItalic,
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}
