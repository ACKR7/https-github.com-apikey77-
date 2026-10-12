package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.VideoCameraFront
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.data.model.VipInsightItem
import com.example.ui.theme.MysticBorder
import com.example.ui.theme.MysticBorderGold
import com.example.ui.theme.MysticDarkBg
import com.example.ui.theme.MysticGold
import com.example.ui.theme.MysticGoldDark
import com.example.ui.theme.MysticGoldContainer
import com.example.ui.theme.MysticIndigo
import com.example.ui.theme.MysticPink
import com.example.ui.theme.MysticPrimary
import com.example.ui.theme.MysticPrimaryContainer
import com.example.ui.theme.MysticSurface
import com.example.ui.theme.MysticSurfaceCard
import com.example.ui.theme.MysticSurfaceVariant
import com.example.ui.theme.MysticTextMuted
import com.example.ui.theme.MysticTextPrimary
import com.example.ui.theme.MysticTextSecondary

@Composable
fun MahaTopAppBar(
    isVip: Boolean,
    onVipClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("maha_top_app_bar"),
        color = MysticSurface.copy(alpha = 0.95f),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(MysticPrimary, MysticPink, MysticIndigo)
                            )
                        )
                        .padding(2.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_mahaduang_logo),
                        contentDescription = "Maha Duang Logo",
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                    )
                }

                Column {
                    Text(
                        text = "มหาดวง AI",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MysticTextPrimary
                    )
                    Text(
                        text = "สิริมงคล & โชคชะตาแม่นยำ",
                        fontSize = 11.sp,
                        color = MysticTextMuted
                    )
                }
            }

            if (isVip) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MysticGoldContainer.copy(alpha = 0.8f),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticGold, MysticPrimary))),
                    modifier = Modifier.testTag("vip_active_badge")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Stars,
                            contentDescription = "VIP Member",
                            tint = MysticGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "VIP สมาชิก",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MysticGold
                        )
                    }
                }
            } else {
                Button(
                    onClick = onVipClick,
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MysticGold,
                        contentColor = Color(0xFF1C1300)
                    ),
                    contentPadding = ButtonDefaults.TextButtonContentPadding,
                    modifier = Modifier
                        .height(34.dp)
                        .testTag("upgrade_vip_top_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.WorkspacePremium,
                        contentDescription = "VIP Upgrade",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "อัปเกรด VIP",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun AdMobBannerSimulator(
    onCloseClick: () -> Unit,
    onVipClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("admob_banner_simulator"),
        color = Color(0xF00D081F),
        tonalElevation = 8.dp,
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticPrimary.copy(alpha = 0.3f), MysticIndigo.copy(alpha = 0.3f))))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MysticGold
                ) {
                    Text(
                        text = "AdMob",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
                Column {
                    Text(
                        text = "Banner Ad (ca-app-pub-3940256099942544/6300978111)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MysticPink,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "สนับสนุนนักพัฒนา • อัปเกรดเพื่อซ่อนโฆษณา",
                        fontSize = 10.sp,
                        color = MysticTextMuted
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MysticGold.copy(alpha = 0.15f),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticGold, MysticGoldDark))),
                    modifier = Modifier.clickable { onVipClick() }
                ) {
                    Text(
                        text = "👑 ปิดโฆษณา (VIP)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = MysticGold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                IconButton(
                    onClick = onCloseClick,
                    modifier = Modifier
                        .size(28.dp)
                        .testTag("close_banner_ad_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Ad Banner",
                        tint = MysticTextMuted,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun RewardedAdDialog(
    countdown: Int,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = {}) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MysticSurfaceCard),
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(MysticPrimary, MysticPink))),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("rewarded_ad_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MysticPrimaryContainer)
                        .border(1.dp, MysticPrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.VideoCameraFront,
                        contentDescription = "Playing Rewarded Ad",
                        tint = MysticPink,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Text(
                    text = "กำลังเล่นโฆษณาสนับสนุน...",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MysticPink,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Google AdMob Rewarded Video กำลังเล่น โปรดรอสักครู่เพื่อรับคำทำนายเชิงลึกโดยอัตโนมัติ",
                    fontSize = 12.sp,
                    color = MysticTextSecondary,
                    textAlign = TextAlign.Center
                )

                val progress = (countdown.toFloat() / 5f).coerceIn(0f, 1f)
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = MysticPrimary,
                    trackColor = MysticSurfaceVariant
                )

                Text(
                    text = if (countdown > 0) "เหลือเวลาอีก $countdown วินาที" else "✨ ปลดล็อกสำเร็จแล้ว!",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MysticGold
                )
            }
        }
    }
}

@Composable
fun VipUpgradeDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MysticSurfaceCard),
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(MysticGold, MysticPrimary))),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .testTag("vip_upgrade_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.size(24.dp))
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(listOf(MysticGold, Color(0xFFD97706)))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WorkspacePremium,
                            contentDescription = "VIP Crown",
                            tint = Color(0xFF1E1133),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MysticTextMuted
                        )
                    }
                }

                Text(
                    text = "อัปเกรดเป็นสมาชิก VIP",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MysticGold
                )

                Text(
                    text = "ปลดล็อกคำทำนายความรักและการเงินแบบรายเดือน พร้อมซ่อนโฆษณา AdMob ทั้งหมดทันที",
                    fontSize = 12.sp,
                    color = MysticTextSecondary,
                    textAlign = TextAlign.Center
                )

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MysticDarkBg.copy(alpha = 0.8f),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticBorderGold, MysticBorder)))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        VipBenefitRow(text = "ดูดวงความรัก & การเงินรายเดือน 360 องศา")
                        VipBenefitRow(text = "ไม่มีโฆษณา AdMob แบนเนอร์กวนใจ")
                        VipBenefitRow(text = "ปลดล็อกคำแนะนำเชิงลึก 12 ราศีอัตโนมัติ")
                        VipBenefitRow(text = "ปรึกษา AI ผู้หยั่งรู้โชคชะตาได้ไม่จำกัด")
                    }
                }

                Button(
                    onClick = onConfirm,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("confirm_vip_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MysticGold,
                        contentColor = Color(0xFF1C1300)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.WorkspacePremium,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "ยืนยันสมัครแพ็กเกจ VIP (99฿/เดือน)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun VipBenefitRow(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = MysticGold,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            fontSize = 12.sp,
            color = MysticTextPrimary
        )
    }
}

@Composable
fun VipDetailDialog(
    item: VipInsightItem,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MysticSurfaceCard),
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(MysticGold, MysticPrimary))),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .testTag("vip_detail_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
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
                        Text(text = item.icon, fontSize = 24.sp)
                        Column {
                            Text(
                                text = item.title,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MysticPink
                            )
                            Text(
                                text = item.tag,
                                fontSize = 10.sp,
                                color = MysticGold
                            )
                        }
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MysticTextMuted
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MysticDarkBg.copy(alpha = 0.8f),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticBorder, MysticBorderGold)))
                ) {
                    Text(
                        text = item.fullContent,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = MysticTextPrimary,
                        modifier = Modifier.padding(14.dp)
                    )
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MysticPrimary)
                ) {
                    Text("รับทราบคำทำนาย", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
