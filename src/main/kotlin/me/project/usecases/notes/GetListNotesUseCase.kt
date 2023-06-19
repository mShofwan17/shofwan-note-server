package me.project.usecases.notes

import me.project.models.Note
import me.project.repositories.NoteRepository

class GetListNotesUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke() : List<Note>? {
        return repository.getListNotes()
    }
}