package me.project.repositories

import me.project.models.Note
import org.jetbrains.exposed.sql.Database

interface NoteRepository {
    val instance: Database?
    suspend fun getListNotes(): List<Note>?
    suspend fun getNoteById(id: Int?): Note?
    suspend fun addNote(note: Note): Note?
    suspend fun updateNote(note: Note): Boolean?
    suspend fun searchByName(name: String): List<Note>?
}