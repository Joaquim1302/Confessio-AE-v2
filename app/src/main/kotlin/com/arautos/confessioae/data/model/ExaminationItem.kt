package com.arautos.confessioae.data.model

data class ExaminationItem(
    val id: String,
    val text: String,
    val category: Category,
    val note: String? = null,
)

enum class Category(val title: String, val description: String) {
    DEUS("Relação com Deus", "Da minha relação com Deus:"),
    PROXIMO("Relação com o Próximo", "Da minha relação para com meu próximo:"),
    CONSIGO("Respeito para comigo", "Do meu respeito para comigo:")
}
