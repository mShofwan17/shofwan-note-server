package me.project.usecases.notes

import me.project.models.Note
import me.project.repositories.NoteRepository

class GetNoteByIdUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int?) : Note?{
        return repository.getNoteById(id)
    }
}