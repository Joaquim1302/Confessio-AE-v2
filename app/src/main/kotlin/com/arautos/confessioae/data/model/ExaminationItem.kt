package com.arautos.confessioae.data.model

data class ExaminationItem(
    val id: String,
    val text: String,
    val category: Category,
    val note: String? = null,
)

enum class Category(val description: String) {
    DEUS("Da minha relação com Deus:"),
    PROXIMO("Da minha relação para com meu próximo:"),
    CONSIGO("Do meu respeito para comigo:")
}
