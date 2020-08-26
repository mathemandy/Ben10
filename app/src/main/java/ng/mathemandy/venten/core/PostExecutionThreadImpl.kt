package ng.mathemandy.venten.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ng.mathemandy.venten.domain.executor.PostExecutionThread

class PostExecutionThreadImpl  : PostExecutionThread {
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val default: CoroutineDispatcher = Dispatchers.Default
}
