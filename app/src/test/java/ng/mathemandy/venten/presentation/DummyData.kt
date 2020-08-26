package ng.mathemandy.venten.presentation

import ng.mathemandy.venten.data.model.Filter

object DummyData  {


    val filter  =  Filter(1, 1996, 2000, "Male",  listOf("China", "Thailand"), listOf("Yellow", "Maroon"))
    val filter2  =  Filter(1, 1996, 2000, "male",  listOf("china", "Thailand"), listOf("yellow", "mAroon"))
    val filter1  =  Filter(1, 2000, 2000, "Male",  listOf("China", "Thailand", "France"), listOf("Yellow", "Maroon", "Green"))

}