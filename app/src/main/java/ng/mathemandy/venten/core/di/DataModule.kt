package ng.mathemandy.venten.core.di

import ng.mathemandy.venten.data.repository.FilterRepositoryImpl
import ng.mathemandy.venten.domain.repository.FilterRepository
import org.koin.dsl.module

val  dataModule  = module {

    single <FilterRepository> {   FilterRepositoryImpl(get()) }
}