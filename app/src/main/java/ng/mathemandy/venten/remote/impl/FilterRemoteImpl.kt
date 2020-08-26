package ng.mathemandy.venten.remote.impl

import ng.mathemandy.venten.data.model.Filter
import ng.mathemandy.venten.remote.ApiService
import ng.mathemandy.venten.data.contract.FilterRemote

class FilterRemoteImpl (val apiService: ApiService) : FilterRemote {
    override suspend fun fetchFilters(): List<Filter> {
        return apiService.fetchFilters()
    }
}