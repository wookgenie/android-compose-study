package com.wookgenie.android.composestudy.feature.receipt.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wookgenie.android.composestudy.feature.receipt.domain.repository.ReceiptRepository

class ReceiptListViewModelFactory(
    private val repository: ReceiptRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReceiptListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReceiptListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
