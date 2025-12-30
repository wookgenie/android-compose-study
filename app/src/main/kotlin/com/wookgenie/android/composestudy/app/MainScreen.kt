package com.wookgenie.android.composestudy.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.wookgenie.android.composestudy.feature.receipt.presentation.route.ReceiptsRoute

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(MainTab.RECEIPTS) }

    Scaffold() { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            when (selectedTab) {
//                MainTab.HOME -> HomeScreen()
                MainTab.RECEIPTS -> ReceiptsRoute(
                    onItemClick = { /* 상세로 */ }
                )
//                MainTab.REPORT -> ReportScreen()
//                MainTab.SETTINGS -> SettingsScreen()
            }
        }

    }
}
