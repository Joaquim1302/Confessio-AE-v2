package com.arautos.confessioae.data.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
sealed class ExamEntry {
    abstract val id: String
    abstract val text: String

    @Serializable
    data class Standard(val itemId: String, override val text: String) : ExamEntry() {
        override val id: String = itemId
    }

    @Serializable
    data class Custom(
        override val id: String = UUID.randomUUID().toString(),
        override val text: String,
    ) : ExamEntry()

    @Serializable
    object PermanentAdd : ExamEntry() {
        override val id: String = "permanent_add_item"
        override val text: String = "pecado não relacionado ou uma dúvida"
    }
}
