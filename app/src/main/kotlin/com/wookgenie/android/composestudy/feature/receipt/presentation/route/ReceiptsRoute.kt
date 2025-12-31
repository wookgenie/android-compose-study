package com.wookgenie.android.composestudy.feature.receipt.presentation.route

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

@Composable
fun ReceiptsRoute(
    onItemClick: (ReceiptItemUi) -> Unit
) {
    // STEP 2(수동 DI) 방식 예시:
    val repository = remember { MockReceiptRepository() }
    val factory = remember { ReceiptListViewModelFactory(repository) }
    val vm: ReceiptListViewModel = viewModel(factory = factory)

    LaunchedEffect(Unit) {
        vm.dispatch(ReceiptListContract.Intent.Enter)
    }

    ReceiptListScreen(
        state = vm.state,
        onIntent = { vm.dispatch(it) },
        onItemClick = { item ->
            vm.dispatch(ReceiptListContract.Intent.ClickItem(item))
            onItemClick(item)
        }
    )
}