package com.wookgenie.android.composestudy.feature.receipt.presentation.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wookgenie.android.composestudy.feature.receipt.domain.repository.ReceiptRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class ReceiptListViewModel(
    private val repository: ReceiptRepository
) : ViewModel() {

    var state by mutableStateOf(ReceiptListContract.State())
        private set

    private val _sideEffect = MutableSharedFlow<ReceiptListContract.SideEffect>()
    val sideEffect: SharedFlow<ReceiptListContract.SideEffect> = _sideEffect

    fun dispatch(intent: ReceiptListContract.Intent) {
        when (intent) {
            is ReceiptListContract.Intent.Enter -> {
                load()
            }

            is ReceiptListContract.Intent.Refresh -> {
                load()
            }

            is ReceiptListContract.Intent.Retry -> {
                load()
            }

            is ReceiptListContract.Intent.ClickItem -> {
            }
        }
    }

    private fun load() {
        state = state.copy(isLoading = true, errorMessage = null)

        runCatching {
            repository.getReceiptGroups()
        }.onSuccess { groups ->
            state = state.copy(isLoading = false, groups = groups)
        }.onFailure { t ->
            state = state.copy(
                isLoading = false,
                errorMessage = t.message ?: "불러오기 실패"
            )
            viewModelScope.launch {
                _sideEffect.emit(
                    ReceiptListContract.SideEffect.ShowMessage("불러오기 실패")
                )
            }
        }
    }
}
