package me.project.di

import me.project.data_source.DatabaseFactory
import me.project.repositories.NoteRepository
import me.project.repositories.NoteRepositoryImpl
import me.project.usecases.notes.*
import org.koin.dsl.module

val databaseModule = module {
    single<DatabaseFactory> { DatabaseFactory() }
}

val repositoryModule = module {
    single<NoteRepository> { NoteRepositoryImpl(get()) }
}

val useCaseModule = module {
    single<GetListNotesUseCase> { GetListNotesUseCase(get()) }
    single<GetNoteByIdUseCase> { GetNoteByIdUseCase(get()) }
    single<AddNoteUseCase> { AddNoteUseCase(get()) }
    single<UpdateNoteUseCase> { UpdateNoteUseCase(get()) }
    single<SearchNoteByNameUseCase> { SearchNoteByNameUseCase(get()) }
    single<DeleteNoteUseCase> { DeleteNoteUseCase(get()) }
}
