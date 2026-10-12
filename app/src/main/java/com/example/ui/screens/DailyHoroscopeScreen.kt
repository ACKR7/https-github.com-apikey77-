package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ZodiacRepository
import com.example.data.model.ZodiacSign
import com.example.ui.theme.MysticBorder
import com.example.ui.theme.MysticBorderGold
import com.example.ui.theme.MysticDarkBg
import com.example.ui.theme.MysticGold
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
fun DailyHoroscopeScreen(
    selectedZodiac: ZodiacSign,
    isDeepInsightUnlocked: Boolean,
    onSelectZodiac: (ZodiacSign) -> Unit,
    onWatchRewardedAd: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("daily_horoscope_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero cosmic banner
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                colors = CardDefaults.cardColors(containerColor = MysticSurfaceVariant),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticPrimary, MysticGold)))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.bg_mystic_nebula),
                        contentDescription = "Cosmic Constellation Banner",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Transparent, Color(0xD00A0618))
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = MysticGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "ดวงชะตา 12 ราศีประจำวันนี้",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MysticTextPrimary
                            )
                        }
                        Text(
                            text = "ถอดรหัสพลังงานดวงดาว วิเคราะห์งาน เงิน และความรัก",
                            fontSize = 11.sp,
                            color = MysticTextSecondary
                        )
                    }
                }
            }
        }

        // Zodiac Selector Grid
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MysticSurfaceCard.copy(alpha = 0.85f)),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(MysticBorder, MysticBorderGold))),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "แตะเลือกราศีของคุณ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MysticGold
                    )

                    // 12 signs in 4x3 grid
                    val signs = ZodiacRepository.allSigns
                    val rows = signs.chunked(4)

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        for (row in rows) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                for (sign in row) {
                                    val isSelected = sign.id == selectedZodiac.id
                                    Surface(
                                        shape = RoundedCornerShape(14.dp),
                                        color = if (isSelected) MysticPrimaryContainer else MysticDarkBg.copy(alpha = 0.7f),
                                        border = CardDefaults.outlinedCardBorder().copy(
                                            brush = if (isSelected) {
                                                Brush.linearGradient(listOf(MysticPrimary, MysticGold))
                                            } else {
                                                Brush.linearGradient(listOf(MysticBorder, Color.Transparent))
                                            }
                                        ),
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable { onSelectZodiac(sign) }
                                            .testTag("zodiac_item_${sign.id}")
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = sign.symbol,
                                                fontSize = 20.sp
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = sign.thaiName.replace("ราศี", ""),
                                                fontSize = 11.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                color = if (isSelected) MysticGold else MysticTextSecondary,
                                                maxLines = 1
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Selected Zodiac Horoscope Details
        item {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MysticSurfaceCard),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(MysticPrimary.copy(alpha = 0.5f), MysticIndigo.copy(alpha = 0.4f)))),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("zodiac_result_card")
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = selectedZodiac.symbol,
                                fontSize = 32.sp
                            )
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = selectedZodiac.thaiName,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MysticPink
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MysticSurfaceVariant
                                    ) {
                                        Text(
                                            text = selectedZodiac.element,
                                            fontSize = 10.sp,
                                            color = MysticIndigo,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = selectedZodiac.dateRange,
                                    fontSize = 11.sp,
                                    color = MysticTextMuted
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MysticPrimaryContainer.copy(alpha = 0.6f),
                            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticPrimary, MysticPink)))
                        ) {
                            Text(
                                text = "ความแม่นยำ 98%",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MysticPink,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // 3 Category Cards: Work, Finance, Love
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        PredictionDetailBox(
                            icon = Icons.Default.Work,
                            iconColor = MysticIndigo,
                            title = "การงาน & หน้าที่",
                            content = selectedZodiac.work
                        )
                        PredictionDetailBox(
                            icon = Icons.Default.MonetizationOn,
                            iconColor = MysticGold,
                            title = "การเงิน & โชคลาภ",
                            content = selectedZodiac.finance
                        )
                        PredictionDetailBox(
                            icon = Icons.Default.Favorite,
                            iconColor = MysticPink,
                            title = "ความรัก & ครอบครัว",
                            content = selectedZodiac.love
                        )
                    }

                    // Lucky metrics
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MysticDarkBg.copy(alpha = 0.8f),
                        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticBorderGold, MysticBorder)))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "🎨 สีมงคล: ${selectedZodiac.luckyColor}",
                                    fontSize = 12.sp,
                                    color = MysticTextSecondary
                                )
                                Text(
                                    text = "🔢 เลขมงคล: ${selectedZodiac.luckyNumber}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MysticGold
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "🧭 ทิศมงคล: ${selectedZodiac.auspiciousDirection}",
                                    fontSize = 11.sp,
                                    color = MysticTextMuted
                                )
                                Text(
                                    text = "⏰ ฤกษ์ดี: ${selectedZodiac.auspiciousTime}",
                                    fontSize = 11.sp,
                                    color = MysticTextMuted
                                )
                            }
                        }
                    }

                    // Deep Insight Unlocked or Rewarded Ad Button
                    if (isDeepInsightUnlocked) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MysticGoldContainer.copy(alpha = 0.25f),
                            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticGold, MysticPrimary))),
                            modifier = Modifier.testTag("deep_insight_unlocked_box")
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LockOpen,
                                        contentDescription = null,
                                        tint = MysticGold,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "คำแนะนำเชิงลึก (Deep Astrological Insight)",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MysticGold
                                    )
                                }
                                Text(
                                    text = selectedZodiac.deepInsight,
                                    fontSize = 12.sp,
                                    lineHeight = 18.sp,
                                    color = MysticTextPrimary
                                )
                                Text(
                                    text = "✨ เคล็ดลับเสริมดวง: ${selectedZodiac.luckyRitual}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MysticPink
                                )
                            }
                        }
                    } else {
                        Button(
                            onClick = onWatchRewardedAd,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("watch_rewarded_ad_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MysticPrimary,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayCircleFilled,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ดูโฆษณา (Rewarded Video) เพื่อปลดล็อกคำแนะนำเชิงลึก",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
private fun PredictionDetailBox(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    title: String,
    content: String
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MysticDarkBg.copy(alpha = 0.6f),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(iconColor.copy(alpha = 0.3f), Color.Transparent)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = iconColor
                )
            }
            Text(
                text = content,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = MysticTextSecondary
            )
        }
    }
}
