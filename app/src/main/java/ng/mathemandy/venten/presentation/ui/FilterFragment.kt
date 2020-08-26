package ng.mathemandy.venten.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.simple_empty_state_view.*
import ng.mathemandy.venten.R
import ng.mathemandy.venten.data.model.Car
import ng.mathemandy.venten.data.model.Filter
import ng.mathemandy.venten.presentation.AppStatus
import ng.mathemandy.venten.presentation.FilterViewModel
import ng.mathemandy.venten.presentation.ui.adapters.FilterAdapter
import org.koin.android.ext.android.inject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset

class FilterFragment  : BaseFragment(), FilterAdapter.Interaction {

    private val filterViewModel : FilterViewModel by inject()


    private val adapter: FilterAdapter by lazy { FilterAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDataListener()
        setUpRecyclerview()
    }

    override fun itemClicked(item: Filter) {
        val actions  = FilterFragmentDirections.actionFiltersFragmentToCarsFragment(item)
        findNavController().navigate(actions)
    }

    private fun setUpRecyclerview() {
        retry_btn.setOnClickListener {
            filterViewModel.getFilters()
            renderLoadingState()
        }

        filter_rv.adapter = adapter
        filter_rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
//        adapter.swapData( MutableList(2){
//            Filter(it, 1995, 2000, "Female",  listOf("China"), listOf("Yellow"))
//        }, null)
//        renderSuccessState()

    }


    private fun  initDataListener() {
        filterViewModel.mFetchFiltersLiveData.observe(viewLifecycleOwner, {
            when (it.status) {

                AppStatus.LOADING -> {
                    renderLoadingState()
                }
                AppStatus.SUCCESS -> {
                    it.data?.let { it1 -> adapter.swapData(it1, null) }
                    renderSuccessState()
                }

                AppStatus.FAILED -> {

                    if (adapter.itemCount > 0){
                        it.message?.let { it1 ->  renderDataAvailableErrorState(it1) }

                    }else {
                        it.message?.let { it1 -> renderNoDataErrorState(it1) }

                    }


                }

                AppStatus.EMPTY -> {

                    renderEmptyState()
                }
            }

        })


    }


    private fun renderRefreshState() {
        empty_state.isVisible = false
        loading_view.isVisible  = false
    }

    private fun renderLoadingState() {
        empty_state.isVisible = false
        loading_view.isVisible  = true
    }
    private fun renderEmptyState() {
        loading_view.isVisible  = false
    empty_state.setImage(context?.let { getDrawable(it, R.drawable.ic_empty) })
        empty_state.isVisible = true
        empty_state.setTitle("No Filters Available")
}

    private fun renderDataAvailableErrorState(msg: String) {
        loading_view.isVisible  = false
        empty_state.isVisible = false
        view?.let { it1 -> showSnackBar(it1, msg, true) }

    }

    private fun renderSuccessState() {
        loading_view.isVisible  = false
        empty_state.isVisible = false
    }

    private fun renderNoDataErrorState(msg: String) {
        loading_view.isVisible  = false
        empty_state.isVisible = true
        empty_state.setImage(this.context?.let { getDrawable(it, R.drawable.broken_mug_big) })
        empty_state.setCaption(msg)
        empty_state.setTitle("An Error Occurred")
    }



}


