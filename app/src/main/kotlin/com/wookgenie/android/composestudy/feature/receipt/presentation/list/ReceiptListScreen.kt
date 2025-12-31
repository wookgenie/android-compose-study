package com.wookgenie.android.composestudy.feature.receipt.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wookgenie.android.composestudy.core.ui.theme.Pretendard
import com.wookgenie.android.composestudy.core.ui.theme.color_2B2B2B
import com.wookgenie.android.composestudy.core.ui.theme.color_489A7C
import com.wookgenie.android.composestudy.core.ui.theme.color_548DDE
import com.wookgenie.android.composestudy.core.ui.theme.color_7E7E7E
import com.wookgenie.android.composestudy.core.ui.theme.color_9E9E9E
import com.wookgenie.android.composestudy.core.ui.theme.color_ECECEC
import com.wookgenie.android.composestudy.core.ui.theme.color_F5F8FF

enum class RcptGb { G0, G1 }

data class ReceiptItemUi(
    val id: String,
    val bzaqNm: String,
    val trscAmtText: String,
    val useUsagNm: String,
    val badgeText: String,
    val rcptGb: RcptGb,
    val isApproved: Boolean
)

data class ReceiptDateGroupUi(
    val dateKey: String,
    val headerText: String,
    val items: List<ReceiptItemUi>
)


@Composable
fun ReceiptListScreen(
    state: ReceiptListContract.State,
    onIntent: (ReceiptListContract.Intent) -> Unit,
    modifier: Modifier = Modifier,
    onItemClick: (ReceiptItemUi) -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {

        // Content 영역: 에러 / 빈값 / 리스트
        when {
            state.errorMessage != null -> {
                ErrorView(
                    message = state.errorMessage,
                    onRetry = { onIntent(ReceiptListContract.Intent.Retry) },
                    modifier = Modifier.fillMaxSize()
                )
            }

            state.groups.isEmpty() -> {
                EmptyReceiptsView(
                    text = "내역이 없습니다",
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> {
                ReceiptGroupedList(
                    groups = state.groups,
                    onItemClick = { item ->
                        onItemClick(item)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // 로딩 오버레이 (API 붙일 때 자연스럽게 동작)
        if (state.isLoading) {
            LoadingOverlay(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun ReceiptGroupedList(
    groups: List<ReceiptDateGroupUi>,
    onItemClick: (ReceiptItemUi) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 15.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        groups.forEachIndexed { index, group ->
            item {
                DateHeader(text = group.headerText)
            }

            items(
                items = group.items,
                key = { it.id }
            ) { item ->
                ReceiptItemCard(
                    item = item,
                    onClick = { onItemClick(item) }
                )
            }

            if (index < groups.lastIndex) {
                item {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = color_ECECEC
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "불러오기 실패",
                style = TextStyle(
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = color_2B2B2B
                )
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = message,
                style = TextStyle(
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    color = color_9E9E9E
                )
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "다시 시도",
                style = TextStyle(
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = color_548DDE
                ),
                modifier = Modifier
                    .clickable(onClick = onRetry)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun LoadingOverlay(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.08f)),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun DateHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = color_2B2B2B
        ),
        modifier = modifier.padding(top = 15.dp, bottom = 4.dp)
    )
}

@Composable
fun ReceiptItemCard(
    item: ReceiptItemUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = Color.Transparent,
        shadowElevation = 0.dp,
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 9.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.bzaqNm,
                    style = TextStyle(
                        fontFamily = Pretendard,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = color_2B2B2B
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(3.dp))

                Text(
                    text = item.trscAmtText,
                    style = TextStyle(
                        fontFamily = Pretendard,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = color_2B2B2B
                    )
                )
            }

            Spacer(modifier = Modifier.height(11.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = Pretendard,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = color_7E7E7E
                            )
                        ) {
                            val use = item.useUsagNm.trim()
                            if (use.isNotEmpty()) {
                                append(use)
                                append(" | ")
                            }
                        }

                        withStyle(
                            style = SpanStyle(
                                fontFamily = Pretendard,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = if (item.rcptGb == RcptGb.G0) color_489A7C else color_2B2B2B
                            )
                        ) {
                            append(item.badgeText)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (item.isApproved) {
                    Text(
                        text = "결의완료",
                        style = TextStyle(
                            fontFamily = Pretendard,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            color = color_548DDE
                        ),
                        modifier = modifier
                            .background(
                                color = color_F5F8FF,
                                shape = RoundedCornerShape(3.dp)
                            )
                            .padding(
                                horizontal = 10.dp,
                                vertical = 4.dp
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyReceiptsView(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = color_9E9E9E
            )
        )
    }
}