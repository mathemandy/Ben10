package ng.mathemandy.venten.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ng.mathemandy.venten.data.contract.FilterRemote
import ng.mathemandy.venten.data.model.Filter
import ng.mathemandy.venten.domain.repository.FilterRepository

class FilterRepositoryImpl  (
    private val filterRemote  : FilterRemote
) : FilterRepository {

    override fun fetchFilters(): Flow<List<Filter>> {
        return   flow {
            emit(filterRemote.fetchFilters())
        }
    }
}