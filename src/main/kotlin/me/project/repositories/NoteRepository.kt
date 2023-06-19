package me.project.repositories

import me.project.models.Note
import org.jetbrains.exposed.sql.ResultRow

interface NoteRepository {
    suspend fun getListNotes(): List<Note>?
    suspend fun getNoteById(id: Int?): Note?
    suspend fun addNote(note: Note): Note?
}