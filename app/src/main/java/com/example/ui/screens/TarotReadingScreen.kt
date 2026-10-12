package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TarotCard
import com.example.ui.theme.MysticBorder
import com.example.ui.theme.MysticBorderGold
import com.example.ui.theme.MysticDarkBg
import com.example.ui.theme.MysticDarkBg2
import com.example.ui.theme.MysticGold
import com.example.ui.theme.MysticIndigo
import com.example.ui.theme.MysticPink
import com.example.ui.theme.MysticPrimary
import com.example.ui.theme.MysticPrimaryContainer
import com.example.ui.theme.MysticSurfaceCard
import com.example.ui.theme.MysticTextMuted
import com.example.ui.theme.MysticTextPrimary
import com.example.ui.theme.MysticTextSecondary

@Composable
fun TarotReadingScreen(
    deck: List<TarotCard>,
    selectedCards: List<TarotCard>,
    isRevealed: Boolean,
    onPickCard: (TarotCard) -> Unit,
    onReveal: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("tarot_reading_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Guide Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MysticSurfaceCard),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticPrimary, MysticGold))),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = "🎴", fontSize = 22.sp)
                        Text(
                            text = "เปิดไพ่ยิปซี 3 ใบ (อดีต • ปัจจุบัน • อนาคต)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MysticPink
                        )
                    }
                    Text(
                        text = "ตั้งจิตอธิษฐานนึกถึงคำถามในใจ จากนั้นเลือกไพ่ 3 ใบจากสำรับด้านล่าง",
                        fontSize = 12.sp,
                        color = MysticTextSecondary,
                        textAlign = TextAlign.Center
                    )

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MysticPrimaryContainer.copy(alpha = 0.5f),
                        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticPrimary, MysticPink)))
                    ) {
                        Text(
                            text = "เลือกแล้ว ${selectedCards.size}/3 ใบ",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedCards.size == 3) MysticGold else MysticPink,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Deck Cards (12 cards in 4x3 or 6x2 grid)
        if (!isRevealed) {
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MysticSurfaceCard.copy(alpha = 0.9f)),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(MysticBorder, MysticBorderGold))),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val cardRows = deck.take(12).chunked(4)
                        for (row in cardRows) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                for (card in row) {
                                    val isPicked = selectedCards.contains(card)
                                    val pickIndex = if (isPicked) selectedCards.indexOf(card) + 1 else 0

                                    Surface(
                                        shape = RoundedCornerShape(14.dp),
                                        color = if (isPicked) MysticPrimaryContainer else MysticDarkBg2,
                                        border = CardDefaults.outlinedCardBorder().copy(
                                            brush = if (isPicked) {
                                                Brush.linearGradient(listOf(MysticGold, MysticPink))
                                            } else {
                                                Brush.linearGradient(listOf(MysticBorder, MysticIndigo.copy(alpha = 0.3f)))
                                            }
                                        ),
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(86.dp)
                                            .clickable { onPickCard(card) }
                                            .testTag("tarot_card_${card.id}")
                                    ) {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            if (isPicked) {
                                                Icon(
                                                    imageVector = Icons.Default.Stars,
                                                    contentDescription = "Picked",
                                                    tint = MysticGold,
                                                    modifier = Modifier.size(24.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "ใบที่ $pickIndex",
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MysticGold
                                                )
                                            } else {
                                                Text(text = "✨", fontSize = 16.sp)
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "เลือกไพ่",
                                                    fontSize = 10.sp,
                                                    color = MysticTextMuted
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (selectedCards.size == 3) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = onReveal,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("reveal_tarot_button"),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MysticPrimary,
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "เปิดคำทำนายไพ่ยิปซี 3 ใบ",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Revealed Tarot Results
        if (isRevealed && selectedCards.size == 3) {
            item {
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MysticSurfaceCard),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(MysticGold, MysticPrimary))),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("tarot_result_card")
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "✨ ผลคำทำนายไพ่ยิปซีของคุณ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MysticPink
                            )
                            OutlinedButton(
                                onClick = onReset,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.testTag("reset_tarot_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = MysticTextSecondary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "สับไพ่ใหม่",
                                    fontSize = 11.sp,
                                    color = MysticTextSecondary
                                )
                            }
                        }

                        // Position 1: Past
                        TarotInterpretationCard(
                            positionLabel = "ใบที่ 1 (อดีต • สิ่งที่ผ่านมา)",
                            positionColor = MysticIndigo,
                            card = selectedCards[0],
                            interpretation = selectedCards[0].pastMeaning
                        )

                        // Position 2: Present
                        TarotInterpretationCard(
                            positionLabel = "ใบที่ 2 (ปัจจุบัน • สถานการณ์ที่เผชิญ)",
                            positionColor = MysticPink,
                            card = selectedCards[1],
                            interpretation = selectedCards[1].presentMeaning
                        )

                        // Position 3: Future
                        TarotInterpretationCard(
                            positionLabel = "ใบที่ 3 (อนาคต • บทสรุปและทางออก)",
                            positionColor = MysticGold,
                            card = selectedCards[2],
                            interpretation = selectedCards[2].futureMeaning
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

@Composable
private fun TarotInterpretationCard(
    positionLabel: String,
    positionColor: Color,
    card: TarotCard,
    interpretation: String
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MysticDarkBg.copy(alpha = 0.7f),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(positionColor.copy(alpha = 0.4f), Color.Transparent)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = positionLabel,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = positionColor
                )
                Text(
                    text = "${card.romanNumeral} • ${card.iconSymbol}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MysticTextMuted
                )
            }

            Text(
                text = "${card.nameEn} (${card.nameTh})",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MysticTextPrimary
            )

            Text(
                text = card.keywords,
                fontSize = 11.sp,
                color = MysticTextMuted
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = interpretation,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = MysticTextSecondary
            )

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MysticSurfaceCard,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "💡 คำแนะนำ: ${card.advice}",
                    fontSize = 11.sp,
                    color = MysticGold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                )
            }
        }
    }
}
