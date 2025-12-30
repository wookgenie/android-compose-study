package com.wookgenie.android.composestudy.feature.receipt.domain.repository

import com.wookgenie.android.composestudy.feature.receipt.presentation.list.RcptGb
import com.wookgenie.android.composestudy.feature.receipt.presentation.list.ReceiptDateGroupUi
import com.wookgenie.android.composestudy.feature.receipt.presentation.list.ReceiptItemUi

class MockReceiptRepository : ReceiptRepository {
    override fun getReceiptGroups(): List<ReceiptDateGroupUi> {
        return createMockReceiptGroups()
    }

    private fun createMockReceiptGroups(): List<ReceiptDateGroupUi> {
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
}
