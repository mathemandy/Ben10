package ng.mathemandy.venten.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ng.mathemandy.venten.data.model.Filter
import ng.mathemandy.venten.domain.usecase.FetchFilters


class FilterViewModel  (val fetchFilters: FetchFilters): ViewModel(){


    val mFetchFiltersLiveData = MutableLiveData<AppResource<List<Filter>>>(AppResource.empty())

    init {
        getFilters()
    }

    fun getFilters()  = viewModelScope.launch{
        mFetchFiltersLiveData.postValue(AppResource.loading())
        val filter  = fetchFilters.execute()
        filter
            .handleErrors()
            .collect {
            processFilters(it)
        }


    }




    fun processFilters(filters : List<Filter>){

            if (filters.isNotEmpty()){
                mFetchFiltersLiveData.postValue(AppResource.success(filters))
            }else {
                mFetchFiltersLiveData.postValue(AppResource.empty())
            }


    }

    fun <T> Flow<T>.handleErrors(): Flow<T> = flow {
        try {
            collect { value -> emit(value) }
        } catch (e: Throwable) {
            mFetchFiltersLiveData.postValue(AppResource.failed(e.message))
        }
    }
}