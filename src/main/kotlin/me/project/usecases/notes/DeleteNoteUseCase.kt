package me.project.usecases.notes

import me.project.repositories.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int?) : Boolean? {
        return id?.let { repository.deleteNote(it) }
    }
}