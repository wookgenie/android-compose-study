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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
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
    groups: List<ReceiptDateGroupUi>,
//    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    onItemClick: (ReceiptItemUi) -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (groups.isEmpty()) {
            EmptyReceiptsView(
                text = "내역이 없습니다",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            ReceiptGroupedList(
                groups = groups,
                onItemClick = onItemClick,
                modifier = Modifier.fillMaxSize()

            )
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

@Preview(showBackground = true)
@Composable
private fun ReceiptListScreenPreview_Data() {
    val mock = remember { createMockReceiptGroups() }

    MaterialTheme {
        ReceiptListScreen(
            groups = mock,
//            onAddClick = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ReceiptListScreenPreview_Empty() {
    MaterialTheme {
        ReceiptListScreen(
            groups = emptyList(),
//            onAddClick = {}
        )
    }
}

fun createMockReceiptGroups(): List<ReceiptDateGroupUi> {
    var index = 0

    fun item(
        name: String,
        amount: String,
        use: String,
        badge: String,
        gb: RcptGb,
        approved: Boolean
    ) = ReceiptItemUi(
        id = "item-${index++}",
        bzaqNm = name,
        trscAmtText = amount,
        useUsagNm = use,
        badgeText = badge,
        rcptGb = gb,
        isApproved = approved
    )

    return listOf(
        ReceiptDateGroupUi(
            dateKey = "20251017",
            headerText = "10월 17일 금요일",
            items = listOf(
                item("테스트 결제", "1원", "개인경비", "간이영수증", RcptGb.G0, true),
                item("스타벅스 강남점", "12,000원", "", "국민카드 1234", RcptGb.G1, false),
                item("맥도날드", "8,500원", "식비", "국민카드 5678", RcptGb.G1, false),
                item("편의점", "3,200원", "", "간이영수증", RcptGb.G0, false),
                item("카카오택시", "14,300원", "교통비", "국민카드 1234", RcptGb.G1, true)
            )
        ),
        ReceiptDateGroupUi(
            dateKey = "20251016",
            headerText = "10월 16일 목요일",
            items = listOf(
                item("서점", "18,000원", "도서비", "국민카드 9876", RcptGb.G1, false),
                item("식당", "9,000원", "", "간이영수증", RcptGb.G0, false),
                item("주유소", "52,000원", "교통비", "국민카드 9876", RcptGb.G1, true),
                item("커피빈", "6,500원", "", "국민카드 1234", RcptGb.G1, false),
                item("베이커리", "7,800원", "식비", "간이영수증", RcptGb.G0, false)
            )
        ),
        ReceiptDateGroupUi(
            dateKey = "20251015",
            headerText = "10월 15일 수요일",
            items = listOf(
                item("택시", "11,200원", "교통비", "국민카드 5678", RcptGb.G1, true),
                item("식당", "10,000원", "", "간이영수증", RcptGb.G0, false),
                item("편의점", "2,900원", "개인경비", "국민카드 1234", RcptGb.G1, false),
                item("문구점", "4,500원", "", "간이영수증", RcptGb.G0, false)
            )
        ),
        ReceiptDateGroupUi(
            dateKey = "20251014",
            headerText = "10월 14일 화요일",
            items = listOf(
                item("패스트푸드", "7,300원", "식비", "국민카드 9876", RcptGb.G1, false),
                item("커피숍", "5,500원", "", "간이영수증", RcptGb.G0, false),
                item("약국", "12,400원", "의료비", "국민카드 1234", RcptGb.G1, true),
                item("마트", "26,800원", "", "국민카드 9876", RcptGb.G1, false)
            )
        ),
        ReceiptDateGroupUi(
            dateKey = "20251013",
            headerText = "10월 13일 월요일",
            items = listOf(
                item("서점", "22,000원", "도서비", "국민카드 5678", RcptGb.G1, false),
                item("식당", "8,000원", "", "간이영수증", RcptGb.G0, false),
                item("카페", "4,800원", "", "국민카드 1234", RcptGb.G1, false),
                item("택시", "15,600원", "교통비", "국민카드 5678", RcptGb.G1, true),
                item("편의점", "3,100원", "", "간이영수증", RcptGb.G0, false)
            )
        )
    )
}