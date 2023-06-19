package me.project.usecases.notes

import me.project.models.Note
import me.project.repositories.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note?) : Note? {
        return note?.let { repository.addNote(it) }
    }
}