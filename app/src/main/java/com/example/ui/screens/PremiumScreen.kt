package com.example.ui.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VipContentRepository
import com.example.data.model.VipInsightItem
import com.example.ui.theme.MysticBorder
import com.example.ui.theme.MysticBorderGold
import com.example.ui.theme.MysticDarkBg
import com.example.ui.theme.MysticGold
import com.example.ui.theme.MysticGoldContainer
import com.example.ui.theme.MysticIndigo
import com.example.ui.theme.MysticPink
import com.example.ui.theme.MysticPrimary
import com.example.ui.theme.MysticSurfaceCard
import com.example.ui.theme.MysticTextMuted
import com.example.ui.theme.MysticTextPrimary
import com.example.ui.theme.MysticTextSecondary

@Composable
fun PremiumScreen(
    isVip: Boolean,
    onOpenVipModal: () -> Unit,
    onOpenItem: (VipInsightItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = VipContentRepository.items

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("premium_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero VIP Banner
        item {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MysticSurfaceCard),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticGold, MysticPrimary))),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MysticGoldContainer.copy(alpha = 0.6f),
                        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticGold, MysticPrimary)))
                    ) {
                        Text(
                            text = "บริการพรีเมียมรายเดือน (Ad-Free & VIP)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MysticGold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                        )
                    }

                    Text(
                        text = "ปลดล็อกดวงชะตาเจาะลึก 360 องศา",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MysticGold,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "เข้าถึงคำทำนายดวงความรัก การเงิน ฤกษ์มงคล และบทสวดเสริมบารมี พร้อมซ่อนโฆษณาทั้งหมด",
                        fontSize = 12.sp,
                        color = MysticTextSecondary,
                        textAlign = TextAlign.Center
                    )

                    if (isVip) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = MysticDarkBg.copy(alpha = 0.8f),
                            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticGold, MysticGold)))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = MysticGold,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "คุณคือสมาชิก VIP แล้ว: ปลดล็อกครบทุกฟีเจอร์",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MysticGold
                                )
                            }
                        }
                    } else {
                        Button(
                            onClick = onOpenVipModal,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("premium_subscribe_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MysticGold,
                                contentColor = Color(0xFF1E1133)
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.WorkspacePremium,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "สมัครสมาชิก VIP ทันที (99฿/เดือน)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Feature list
        items(items) { item ->
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MysticSurfaceCard.copy(alpha = 0.85f)),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(MysticBorderGold, MysticBorder))),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (isVip) {
                            onOpenItem(item)
                        } else {
                            onOpenVipModal()
                        }
                    }
                    .testTag("vip_item_${item.tag}")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = item.icon, fontSize = 20.sp)
                            Text(
                                text = item.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MysticPink
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MysticGoldContainer.copy(alpha = 0.5f),
                            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticGold, MysticPrimary)))
                        ) {
                            Text(
                                text = "VIP",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MysticGold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = item.subtitle,
                        fontSize = 12.sp,
                        color = MysticTextSecondary
                    )

                    Button(
                        onClick = {
                            if (isVip) onOpenItem(item) else onOpenVipModal()
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isVip) MysticPrimary else MysticGold.copy(alpha = 0.2f),
                            contentColor = if (isVip) Color.White else MysticGold
                        )
                    ) {
                        Icon(
                            imageVector = if (isVip) Icons.Default.LockOpen else Icons.Default.Lock,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isVip) "เปิดอ่านคำทำนาย" else "ปลดล็อกคำทำนาย (สำหรับ VIP)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
