package ng.mathemandy.venten.remote

import ng.mathemandy.venten.data.model.Filter
import retrofit2.http.GET


interface  ApiService {
    @GET("filter.json")
    suspend fun  fetchFilters(): List<Filter>

}