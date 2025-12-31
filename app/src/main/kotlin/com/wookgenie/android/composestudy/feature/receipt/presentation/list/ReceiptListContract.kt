package com.wookgenie.android.composestudy.feature.receipt.presentation.list

object ReceiptListContract {

    data class State(
        val isLoading: Boolean = false,
        val groups: List<ReceiptDateGroupUi> = emptyList(),
        val errorMessage: String? = null
    )

    sealed interface Intent {
        data object Enter : Intent
        data object Retry : Intent
        data object Refresh : Intent
        data class ClickItem(val item: ReceiptItemUi) : Intent
    }

    sealed interface SideEffect {
        data class ShowMessage(val message: String) : SideEffect
    }
}
