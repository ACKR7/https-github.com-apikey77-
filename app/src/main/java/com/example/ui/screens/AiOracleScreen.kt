package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FortuneHistoryItem
import com.example.ui.theme.MysticBorder
import com.example.ui.theme.MysticBorderGold
import com.example.ui.theme.MysticDarkBg
import com.example.ui.theme.MysticGold
import com.example.ui.theme.MysticIndigo
import com.example.ui.theme.MysticPink
import com.example.ui.theme.MysticPrimary
import com.example.ui.theme.MysticPrimaryContainer
import com.example.ui.theme.MysticSurfaceCard
import com.example.ui.theme.MysticSurfaceVariant
import com.example.ui.theme.MysticTextMuted
import com.example.ui.theme.MysticTextPrimary
import com.example.ui.theme.MysticTextSecondary

@Composable
fun AiOracleScreen(
    userName: String,
    birthDate: String,
    question: String,
    selectedCategory: String,
    isLoading: Boolean,
    aiResponse: String?,
    historyList: List<FortuneHistoryItem>,
    onNameChange: (String) -> Unit,
    onBirthDateChange: (String) -> Unit,
    onQuestionChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onAskOracle: () -> Unit,
    onSelectHistory: (FortuneHistoryItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("การงาน", "การเงิน", "ความรัก", "โชคลาภ", "ทั่วไป")

    val samplePrompts = listOf(
        "ช่วงนี้งานโปรเจกต์ใหญ่จะสำเร็จราบรื่นไหม",
        "ความรักกับคนคุยปัจจุบันจะมีเกณฑ์พัฒนาไหม",
        "ทิศทางโชคลาภและการเงินเดือนนี้เป็นอย่างไร",
        "มีเกณฑ์โยกย้ายหรือเปลี่ยนงานใหม่เร็วๆ นี้ไหม"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("ai_oracle_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Intro Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MysticSurfaceCard),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticIndigo, MysticPrimary))),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "🔮", fontSize = 24.sp)
                        Column {
                            Text(
                                text = "ปรึกษา AI ผู้หยั่งรู้โชคชะตา",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MysticPink
                            )
                            Text(
                                text = "ผสานศาสตร์โหราศาสตร์โบราณกับ Gemini AI เพื่อคำทำนายเฉพาะตัว",
                                fontSize = 11.sp,
                                color = MysticTextSecondary
                            )
                        }
                    }

                    // Category chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(categories) { cat ->
                            val isSelected = cat == selectedCategory
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) MysticPrimary else MysticDarkBg,
                                border = CardDefaults.outlinedCardBorder().copy(
                                    brush = if (isSelected) Brush.horizontalGradient(listOf(MysticGold, MysticPrimary))
                                    else Brush.horizontalGradient(listOf(MysticBorder, Color.Transparent))
                                ),
                                modifier = Modifier
                                    .clickable { onCategoryChange(cat) }
                                    .testTag("category_chip_$cat")
                            ) {
                                Text(
                                    text = cat,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Color.White else MysticTextSecondary,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    // User info inputs
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = userName,
                            onValueChange = onNameChange,
                            placeholder = { Text("ชื่อเล่น เช่น น้องพลอย", fontSize = 11.sp, color = MysticTextMuted) },
                            label = { Text("ชื่อเล่น", fontSize = 11.sp, color = MysticTextMuted) },
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("ai_name_input"),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MysticPrimary,
                                unfocusedBorderColor = MysticBorder,
                                focusedTextColor = MysticTextPrimary,
                                unfocusedTextColor = MysticTextPrimary
                            )
                        )

                        OutlinedTextField(
                            value = birthDate,
                            onValueChange = onBirthDateChange,
                            placeholder = { Text("เช่น 15 ส.ค.", fontSize = 11.sp, color = MysticTextMuted) },
                            label = { Text("วันเกิด (ถ้ามี)", fontSize = 11.sp, color = MysticTextMuted) },
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("ai_birth_input"),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MysticPrimary,
                                unfocusedBorderColor = MysticBorder,
                                focusedTextColor = MysticTextPrimary,
                                unfocusedTextColor = MysticTextPrimary
                            )
                        )
                    }

                    // Question prompt input
                    OutlinedTextField(
                        value = question,
                        onValueChange = onQuestionChange,
                        placeholder = {
                            Text(
                                "พิมพ์คำถามที่สงสัย เช่น ช่วงนี้โปรเจกต์งานจะผ่านไปด้วยดีไหม...",
                                fontSize = 12.sp,
                                color = MysticTextMuted
                            )
                        },
                        label = { Text("คำถามหรือเรื่องที่ต้องการทำนาย", fontSize = 11.sp, color = MysticTextMuted) },
                        minLines = 3,
                        maxLines = 5,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("ai_question_input"),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MysticPrimary,
                            unfocusedBorderColor = MysticBorder,
                            focusedTextColor = MysticTextPrimary,
                            unfocusedTextColor = MysticTextPrimary
                        )
                    )

                    // Quick suggestion pills
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "💡 ตัวอย่างคำถามยอดฮิต:",
                            fontSize = 11.sp,
                            color = MysticTextMuted
                        )
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(samplePrompts) { prompt ->
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = MysticDarkBg.copy(alpha = 0.6f),
                                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticBorder, Color.Transparent))),
                                    modifier = Modifier.clickable { onQuestionChange(prompt) }
                                ) {
                                    Text(
                                        text = prompt,
                                        fontSize = 10.sp,
                                        color = MysticIndigo,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Submit Button
                    Button(
                        onClick = onAskOracle,
                        enabled = !isLoading,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("ask_ai_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MysticPrimary,
                            contentColor = Color.White
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("กำลังสื่อสารกับจักรวาล...", fontSize = 13.sp)
                        } else {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "เริ่มทำนายดวงด้วย AI",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // AI Response Box
        if (aiResponse != null) {
            item {
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MysticSurfaceCard),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(MysticGold, MysticPrimary))),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("ai_response_card")
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = null,
                                tint = MysticGold,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "คำทำนายเฉพาะบุคคลจาก AI Oracle",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MysticGold
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = MysticDarkBg.copy(alpha = 0.7f),
                            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticBorder, Color.Transparent)))
                        ) {
                            Text(
                                text = aiResponse,
                                fontSize = 12.sp,
                                lineHeight = 19.sp,
                                color = MysticTextPrimary,
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                }
            }
        }

        // History list
        if (historyList.isNotEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MysticSurfaceCard.copy(alpha = 0.8f)),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticBorder, Color.Transparent))),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = null,
                                tint = MysticTextMuted,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "ประวัติคำถามที่เคยปรึกษา",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MysticTextSecondary
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            historyList.take(5).forEach { item ->
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MysticDarkBg.copy(alpha = 0.5f),
                                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticBorder, Color.Transparent))),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onSelectHistory(item) }
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = item.question,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = MysticPink,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.weight(1f)
                                            )
                                            Text(
                                                text = item.category,
                                                fontSize = 10.sp,
                                                color = MysticGold
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = item.answer,
                                            fontSize = 11.sp,
                                            color = MysticTextMuted,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
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
