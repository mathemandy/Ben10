package ng.mathemandy.venten.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_car.*
import ng.mathemandy.venten.R
import ng.mathemandy.venten.data.model.Car
import ng.mathemandy.venten.presentation.AppStatus
import ng.mathemandy.venten.presentation.ui.adapters.CarsAdapter
import org.koin.android.ext.android.inject

class CarsFragment : BaseFragment(), CarsAdapter.Interaction {

    private val viewmodel: CarsViewModel by inject()

    private val adapter: CarsAdapter by lazy { CarsAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val filter = CarsFragmentArgs.fromBundle(requireArguments()).filter
        viewmodel.tryToParseFilters(filter)
        initdataListener()
        setUpRecyclerview()
    }

    private fun initdataListener() {

        viewmodel.mFetchCarsLiveData.observe(viewLifecycleOwner, Observer {


            when (it.status) {
                AppStatus.LOADING -> {
                    renderLoadingState()
                }
                AppStatus.SUCCESS -> {
                    ctitle.text = "car Owners (" + it.data?.count() + ")"
                    it.data?.let { it1 -> adapter.swapData(it1, null) }
                    renderSuccessState()
                }

                AppStatus.FAILED -> {
                    if (adapter.itemCount > 0) {
                        it.message?.let { it1 -> renderDataAvailableErrorState(it1) }
                    } else {
                        it.message?.let { it1 -> renderNoDataErrorState(it1) }

                    }
                }

                AppStatus.EMPTY -> {

                    renderEmptyState()
                }
            }
        })

    }



    private fun renderLoadingState() {
        empty_state.isVisible = false
        loading_view.isVisible = true
    }

    private fun renderEmptyState() {
        loading_view.isVisible = false
        empty_state.setImage(context?.let {
            AppCompatResources.getDrawable(
                it,
                R.drawable.pizza_box_big
            )
        })
        empty_state.isVisible = true
        empty_state.isButtonVisible = false
        empty_state.setCaption("No Cars Owner from the selected filter")
    }

    private fun renderDataAvailableErrorState(msg: String) {
        loading_view.isVisible = false
        empty_state.isVisible = false
        view?.let { it1 -> showSnackBar(it1, msg, true) }

    }

    private fun renderSuccessState() {
        loading_view.isVisible = false
        empty_state.isVisible = false
    }

    private fun renderNoDataErrorState(msg: String) {
        loading_view.isVisible = false
        empty_state.isVisible = true
        empty_state.setImage(this.context?.let {
            AppCompatResources.getDrawable(
                it,
                R.drawable.broken_mug_big
            )
        })
        empty_state.setCaption(msg)
        empty_state.setTitle("An Error Occurred")
    }


    override fun itemClicked(item: Car) {

    }

    private fun setUpRecyclerview() {
        cars_rv.adapter = adapter
        cars_rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }


}