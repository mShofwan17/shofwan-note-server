package me.project.di

import io.ktor.server.application.*
import me.project.module
import org.koin.ktor.plugin.Koin

fun Application.configKoin(){
    install(Koin){
        modules(
            databaseModule,
            repositoryModule,
            useCaseModule,
        )
    }
}