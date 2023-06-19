package me.project.usecases.notes

import me.project.models.Note
import me.project.repositories.NoteRepository

class UpdateNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note?) : Boolean? {
        return note?.let { repository.updateNote(it) }
    }
}