package me.project.presentation.note

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.project.base.BasePresentation
import me.project.models.Note
import me.project.usecases.notes.*

object NotePresentation : BasePresentation() {
    fun getAllNotes(
        route: Route,
        getListNotesUseCase: GetListNotesUseCase,
        searchNoteByNameUseCase: SearchNoteByNameUseCase
    ) {
        route.get {
            tryResponse(this) { httpCode ->
                val title = call.request.queryParameters["title"]
                if (title.isNullOrEmpty()) {
                    getListNotesUseCase()?.let {
                        call.respond(
                            message = onSuccess(it),
                            status = httpCode
                        )
                    } ?: call.emptyResult()
                } else {
                    call.respond(
                        message = onSuccess(searchNoteByNameUseCase(title)),
                        status = httpCode
                    )
                }
            }
        }
    }

    fun getNoteById(
        route: Route,
        getNoteByIdUseCase: GetNoteByIdUseCase
    ) {
        route.get("{id?}") {
            tryResponse(this) { httpCode ->
                val id = call.parameters["id"]
                getNoteByIdUseCase(id?.toInt())?.let {
                    call.respond(
                        message = onSuccess(it),
                        status = httpCode
                    )
                } ?: call.emptyResult()

            }
        }
    }

    fun addNote(
        route: Route,
        addNoteUseCase: AddNoteUseCase
    ) {
        route.post {
            tryResponse(this) { httpCode ->
                val note = call.receive<Note>()
                call.respond(
                    message = onSuccess(addNoteUseCase(note = note)),
                    status = httpCode
                )
            }
        }
    }

    fun updateNote(
        route: Route,
        updateNoteUseCase: UpdateNoteUseCase,
        getNoteByIdUseCase: GetNoteByIdUseCase
    ) {
        route.put("{id?}") {
            tryResponse(this) {
                val id = call.parameters["id"]
                val note = call.receive<Note>().copy(id = id?.toInt())
                val updateNote = updateNoteUseCase(note = note)
                if (updateNote == true) {
                    call.respond(
                        message = onSuccess(getNoteByIdUseCase(note.id)),
                        status = it
                    )
                } else updateNote?.let { call.failedUpdateData(note.id) } ?: call.emptyResult()

            }
        }
    }

    fun deleteNote(
        route: Route,
        deleteNoteUseCase: DeleteNoteUseCase
    ) {
        route.delete("{id?}") {
            tryResponse(this) {
                val id = call.parameters["id"]
                deleteNoteUseCase(id?.toInt()).also { isSuccess ->
                    if (isSuccess == true) call.respond(
                        message = onSuccess("Berhasil menghapus Data dengan ID : $id")
                    )
                    else call.failedUpdateData(id?.toInt())
                }
            }
        }
    }
}