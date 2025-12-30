package com.wookgenie.android.composestudy.feature.receipt.presentation.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.wookgenie.android.composestudy.feature.receipt.domain.repository.ReceiptRepository

class ReceiptListViewModel(
    private val repository: ReceiptRepository
) : ViewModel() {

    var groups by mutableStateOf<List<ReceiptDateGroupUi>>(emptyList())
        private set

    init {
        load()
    }

    fun load() {
        groups = repository.getReceiptGroups()
    }
}
