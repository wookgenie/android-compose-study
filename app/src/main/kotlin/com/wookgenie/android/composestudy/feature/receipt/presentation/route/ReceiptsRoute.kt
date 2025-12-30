package com.wookgenie.android.composestudy.feature.receipt.presentation.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wookgenie.android.composestudy.feature.receipt.domain.repository.MockReceiptRepository
import com.wookgenie.android.composestudy.feature.receipt.presentation.list.ReceiptItemUi
import com.wookgenie.android.composestudy.feature.receipt.presentation.list.ReceiptListScreen
import com.wookgenie.android.composestudy.feature.receipt.presentation.list.ReceiptListViewModel
import com.wookgenie.android.composestudy.feature.receipt.presentation.list.ReceiptListViewModelFactory

@Composable
fun ReceiptsRoute(
    onItemClick: (ReceiptItemUi) -> Unit
) {
    val repository = remember { MockReceiptRepository() }
    val factory = remember { ReceiptListViewModelFactory(repository) }

    val vm: ReceiptListViewModel = viewModel (factory = factory)

    ReceiptListScreen(
        groups = vm.groups,
        onItemClick = onItemClick
    )
}