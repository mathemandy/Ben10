package ng.mathemandy.venten.presentation.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ng.mathemandy.venten.data.model.Car
import ng.mathemandy.venten.data.model.Filter
import ng.mathemandy.venten.presentation.AppResource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset

class CarsViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext


    val mFetchCarsLiveData = MutableLiveData<AppResource<List<Car>>>()


    fun tryToParseFilters(filter: Filter) = viewModelScope.launch {
        mFetchCarsLiveData.postValue(AppResource.loading())

        val filteredList = retrieveFileFromLocal(filter)
        filteredList
            .handleErrors()
            .collect {
                processCars(it)
            }


    }


    private suspend fun retrieveFileFromLocal(filter: Filter): Flow<List<Car>> {

        return flow {

            val inputStream = context.resources.assets.open("Venten/car_ownsers_data.csv")
            val reader = BufferedReader(InputStreamReader(inputStream, Charset.forName("UTF-8")))
            var line: String? = ""

            val carList = mutableListOf<Car>()


            while (reader.readLine().also {
                    line = it
                } != null) {
                val st: List<String>? = line?.split(",")
                    val car = Car(
                        id = st?.get(0),
                        first_name = st?.get(1),
                        last_name = st?.get(2),
                        email = st?.get(3),
                        country = st?.get(4),
                        car_model = st?.get(5),
                        car_model_year = st?.get(6),
                        car_color = st?.get(7),
                        gender = st?.get(8),
                        job_title = st?.get(9),
                        bio = st?.get(10)
                    )
                    carList.add(car)
                }


            carList.removeFirst()
            emit(carList.filterBy(filter))

        }.flowOn(Dispatchers.IO)

    }

    private fun processCars(filters: List<Car>) {
        if (filters.isNotEmpty()) {
            mFetchCarsLiveData.postValue(AppResource.success(filters))
        } else {
            mFetchCarsLiveData.postValue(AppResource.empty())
        }

    }

    fun <T> Flow<T>.handleErrors(): Flow<T> = flow {
        try {
            collect { value -> emit(value) }
        } catch (e: Throwable) {
            e.printStackTrace()
            mFetchCarsLiveData.postValue(AppResource.failed(e.message))
        }
    }
}

 fun  MutableList<Car>.filterBy(filter: Filter): List<Car> {
    val filtered =  this.filter {
        val isSameGender  = filter.gender.equals(it.gender, ignoreCase = true)
        val containsColor  =   filter.colors.containsIgnoringCase(it.car_color ?:  "")
        val containsCountry  =    filter.countries.containsIgnoringCase(it.country ?: "")
        val isGreaterOrEqualToStarYear  = it.car_model_year?.toIntOrNull() ?:0  >= filter.start_year
        val isLesserThanOrEqualToEndYear  = it.car_model_year?.toIntOrNull() ?: 0 <= filter.end_year

        isSameGender && containsColor && containsCountry && (isGreaterOrEqualToStarYear && isLesserThanOrEqualToEndYear)
        }

    return filtered
}

private fun  List<String>.containsIgnoringCase(carColor: String): Boolean {
    this.forEach {
        if (it.contains(carColor, ignoreCase = true)){
            return true
        }
    }
        return false
}

