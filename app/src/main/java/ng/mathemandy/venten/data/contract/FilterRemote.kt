package ng.mathemandy.venten.data.contract

import ng.mathemandy.venten.data.model.Filter


interface  FilterRemote {
    suspend fun  fetchFilters(): List<Filter>
}