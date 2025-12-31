package com.wookgenie.android.composestudy.feature.receipt.presentation.route

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wookgenie.android.composestudy.feature.receipt.domain.repository.MockReceiptRepository
import com.wookgenie.android.composestudy.feature.receipt.presentation.list.ReceiptItemUi
import com.wookgenie.android.composestudy.feature.receipt.presentation.list.ReceiptListContract
import com.wookgenie.android.composestudy.feature.receipt.presentation.list.ReceiptListScreen
import com.wookgenie.android.composestudy.feature.receipt.presentation.list.ReceiptListViewModel
import com.wookgenie.android.composestudy.feature.receipt.presentation.list.ReceiptListViewModelFactory
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ReceiptsRoute(
    snackbarHostState: SnackbarHostState,
    onItemClick: (ReceiptItemUi) -> Unit
) {
    val repository = remember { MockReceiptRepository() }
    val factory = remember { ReceiptListViewModelFactory(repository) }
    val vm: ReceiptListViewModel = viewModel(factory = factory)

    LaunchedEffect(Unit) {
        vm.onIntent(ReceiptListContract.Intent.Enter)
    }

    vm.collectSideEffect { effect ->
        when (effect) {
            is ReceiptListContract.SideEffect.ShowMessage -> {
                snackbarHostState.showSnackbar(effect.message)
            }

            is ReceiptListContract.SideEffect.ShowRetry -> {
                val result = snackbarHostState.showSnackbar(
                    message = effect.message,
                    actionLabel = "다시 시도"
                )
                if (result == SnackbarResult.ActionPerformed) {
                    vm.onIntent(ReceiptListContract.Intent.Retry)
                }
            }
        }
    }

    val state = vm.collectAsState().value

    ReceiptListScreen(
        state = state,
        onIntent = vm::onIntent,
        onItemClick = { item ->
            vm.onIntent(ReceiptListContract.Intent.ClickItem(item))
            onItemClick(item)
        }
    )
}
