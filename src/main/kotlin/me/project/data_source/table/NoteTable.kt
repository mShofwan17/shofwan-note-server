package me.project.data_source.table

import org.jetbrains.exposed.dao.id.IntIdTable

private const val TABLE_NAME ="tb_note"
object NoteTable : IntIdTable(TABLE_NAME) {
    val title = varchar("title",255)
    val description = largeText("description")
    val timestamp = varchar("timestamp",255)
}