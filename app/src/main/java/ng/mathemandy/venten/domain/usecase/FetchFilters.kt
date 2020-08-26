package ng.mathemandy.venten.domain.usecase

import kotlinx.coroutines.flow.Flow
import ng.mathemandy.venten.data.model.Filter
import ng.mathemandy.venten.domain.executor.PostExecutionThread
import ng.mathemandy.venten.domain.repository.FilterRepository
import ng.mathemandy.venten.domain.usecase.base.FlowUseCase

class FetchFilters(
private  val filterRepository : FilterRepository,
postExecutionThread: PostExecutionThread
)  : FlowUseCase<Unit, List<Filter>>(postExecutionThread){

    override fun execute(params: Unit?): Flow<List<Filter>> {
        return  filterRepository.fetchFilters()
    }
}