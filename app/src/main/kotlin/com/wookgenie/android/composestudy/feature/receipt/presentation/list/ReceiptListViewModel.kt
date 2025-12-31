package com.wookgenie.android.composestudy.feature.receipt.presentation.list

import androidx.lifecycle.ViewModel
import com.wookgenie.android.composestudy.feature.receipt.domain.repository.ReceiptRepository
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class ReceiptListViewModel(
    private val repository: ReceiptRepository
) : ViewModel(), ContainerHost<ReceiptListContract.State, ReceiptListContract.SideEffect> {

    override val container = container<ReceiptListContract.State, ReceiptListContract.SideEffect>(
        initialState = ReceiptListContract.State()
    )

    fun onIntent(intent: ReceiptListContract.Intent) = when (intent) {
        ReceiptListContract.Intent.Enter -> load()
        ReceiptListContract.Intent.Retry -> load()
        ReceiptListContract.Intent.Refresh -> load()
        is ReceiptListContract.Intent.ClickItem -> {
            // 필요 시 SideEffect로 처리
            intent {
                postSideEffect(
                    ReceiptListContract.SideEffect.ShowMessage(intent.item.bzaqNm)
                )
            }
        }
    }

    private fun load() = intent {
        reduce { state.copy(isLoading = true, errorMessage = null) }

        runCatching { repository.getReceiptGroups() }
            .onSuccess { groups ->
                reduce { state.copy(isLoading = false, groups = groups) }
            }
            .onFailure { t ->
                reduce {
                    state.copy(
                        isLoading = false,
                        errorMessage = t.message ?: "불러오기 실패"
                    )
                }
                postSideEffect(ReceiptListContract.SideEffect.ShowMessage("불러오기 실패"))
            }
    }
}
