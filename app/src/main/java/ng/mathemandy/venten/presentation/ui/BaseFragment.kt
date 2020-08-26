package ng.mathemandy.venten.presentation.ui

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ng.mathemandy.venten.R

open class BaseFragment  : Fragment() {


    fun showSnackBar(rootView: View, text: String, isError: Boolean = false, duration: Int = Snackbar.LENGTH_SHORT){
        val snackBar = Snackbar.make(rootView, text, duration)
        val param = snackBar.view.layoutParams as FrameLayout.LayoutParams
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
        if (isError) snackBarLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)) else snackBarLayout.setBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.colorAccent))
        snackBarLayout.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).setTextColor(
            if (isError) ContextCompat.getColor(requireContext(), R.color.colorAccent) else ContextCompat.getColor(requireContext(), R.color.colorAccent)
        )
        param.gravity = Gravity.TOP
        snackBar.view.layoutParams = param
        snackBar.duration = Snackbar.LENGTH_LONG
        snackBar.show()
    }


}