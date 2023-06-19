package me.project.models

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int? = 0,
    val title: String?,
    val description: String?,
    val dateTime: Int?
)
