package me.project.models

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int? = 0,
    val title: String? = null,
    val description: String? = null,
    val dateTime: Int? = null
)
