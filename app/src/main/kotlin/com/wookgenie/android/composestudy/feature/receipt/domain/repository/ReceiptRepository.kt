package com.wookgenie.android.composestudy.feature.receipt.domain.repository

import com.wookgenie.android.composestudy.feature.receipt.presentation.list.ReceiptDateGroupUi

interface ReceiptRepository {
    fun getReceiptGroups(): List<ReceiptDateGroupUi>
}
