package ng.mathemandy.venten.presentation

import android.content.Context
import ng.mathemandy.venten.presentation.fake.FakeCars
import ng.mathemandy.venten.presentation.ui.filterBy
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import ng.mathemandy.venten.data.model.Car


class CarsViewModelTest  {

    @Test
    fun `check that cars are returned`() {
        val filtered  = mutableListOf(FakeCars.car1, FakeCars.car2).filterBy(DummyData.filter)
        assertThat(filtered).isNotEmpty()
    }


    @Test
    fun `check that filter returns  correct data`() {
       testData { car ->
           assertThat(car.email).isEqualTo(FakeCars.car1.email)
           assertThat(car.bio).isEqualTo(FakeCars.car1.bio)
           assertThat(car.car_color).isEqualTo(FakeCars.car1.car_color)
           assertThat(car.car_model).isEqualTo(FakeCars.car1.car_model)
           assertThat(car.car_color).isEqualTo(FakeCars.car1.car_color)
           assertThat(car.car_model_year).isEqualTo(FakeCars.car1.car_model_year)
           assertThat(car.first_name).isEqualTo(FakeCars.car1.first_name)
           assertThat(car.job_title).isEqualTo(FakeCars.car1.job_title)
           assertThat(car.last_name).isEqualTo(FakeCars.car1.last_name)
       }
    }

    @Test
    fun `check that no cars are returned when start year is greater than end year`() {
        val filtered  = mutableListOf(FakeCars.car1, FakeCars.car2, FakeCars.car3).filterBy(DummyData.filter1)
        assertThat(filtered).isEmpty()
    }

    @Test
    fun `check that query search is not case sensitive`() {
        val filtered  = mutableListOf(FakeCars.car1, FakeCars.car2).filterBy(DummyData.filter2)
        assertThat(filtered).isEmpty()
    }





    private  fun testData(car: (Car) -> Unit) {
        val cars =  mutableListOf(FakeCars.car1, FakeCars.car2).filterBy(DummyData.filter)
        car(cars.first())
    }



}
