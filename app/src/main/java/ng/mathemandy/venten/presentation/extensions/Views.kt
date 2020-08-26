package ng.mathemandy.venten.presentation.extensions

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import ng.mathemandy.venten.presentation.ui.SimpleEmptyStateView

val SimpleEmptyStateView.clicks: Flow<Unit>
    get() = callbackFlow {
        val listener: () -> Unit = {
            safeOffer(Unit)
            Unit
        }
        buttonClickListener = listener
        awaitClose { buttonClickListener = null }
    }.conflate().debounce(200)


fun <E> SendChannel<E>.safeOffer(value: E): Boolean = !isClosedForSend && try {
    offer(value)
} catch (e: CancellationException) {
    false
}