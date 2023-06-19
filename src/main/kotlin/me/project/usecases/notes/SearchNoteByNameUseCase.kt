package me.project.usecases.notes

import me.project.models.Note
import me.project.repositories.NoteRepository

class SearchNoteByNameUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(name: String?) : List<Note>? {
        return name?.let { repository.searchByName(it) }
    }
}