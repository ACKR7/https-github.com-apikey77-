package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AdMobBannerSimulator
import com.example.ui.components.MahaTopAppBar
import com.example.ui.components.RewardedAdDialog
import com.example.ui.components.VipDetailDialog
import com.example.ui.components.VipUpgradeDialog
import com.example.ui.screens.AiOracleScreen
import com.example.ui.screens.DailyHoroscopeScreen
import com.example.ui.screens.PremiumScreen
import com.example.ui.screens.TarotReadingScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.MysticBorder
import com.example.ui.theme.MysticBorderGold
import com.example.ui.theme.MysticDarkBg
import com.example.ui.theme.MysticGold
import com.example.ui.theme.MysticPink
import com.example.ui.theme.MysticPrimary
import com.example.ui.theme.MysticSurface
import com.example.ui.theme.MysticSurfaceCard
import com.example.ui.theme.MysticSurfaceVariant
import com.example.ui.theme.MysticTextMuted
import com.example.ui.theme.MysticTextPrimary
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val uiState by viewModel.uiState.collectAsState()
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(uiState.toastMessage) {
                    uiState.toastMessage?.let { msg ->
                        snackbarHostState.showSnackbar(msg)
                        viewModel.dismissToast()
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MysticDarkBg,
                    topBar = {
                        MahaTopAppBar(
                            isVip = uiState.isVip,
                            onVipClick = { viewModel.showVipDialog() },
                            modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
                        )
                    },
                    bottomBar = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .windowInsetsPadding(WindowInsets.navigationBars)
                        ) {
                            if (uiState.isBannerVisible && !uiState.isVip) {
                                AdMobBannerSimulator(
                                    onCloseClick = { viewModel.closeBannerAd() },
                                    onVipClick = { viewModel.showVipDialog() }
                                )
                            }
                        }
                    },
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        // 4 Tab Bar
                        AppTabBar(
                            currentTab = uiState.currentTab,
                            onSelectTab = { viewModel.selectTab(it) }
                        )

                        // Main Content
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            when (uiState.currentTab) {
                                AppTab.DAILY -> {
                                    DailyHoroscopeScreen(
                                        selectedZodiac = uiState.selectedZodiac,
                                        isDeepInsightUnlocked = uiState.isDeepInsightUnlocked,
                                        onSelectZodiac = { viewModel.selectZodiac(it) },
                                        onWatchRewardedAd = { viewModel.startRewardedAd() }
                                    )
                                }
                                AppTab.TAROT -> {
                                    TarotReadingScreen(
                                        deck = uiState.deck,
                                        selectedCards = uiState.selectedTarotCards,
                                        isRevealed = uiState.isTarotRevealed,
                                        onPickCard = { viewModel.togglePickCard(it) },
                                        onReveal = { viewModel.revealTarot() },
                                        onReset = { viewModel.resetTarot() }
                                    )
                                }
                                AppTab.AI_ORACLE -> {
                                    AiOracleScreen(
                                        userName = uiState.userName,
                                        birthDate = uiState.birthDate,
                                        question = uiState.question,
                                        selectedCategory = uiState.category,
                                        isLoading = uiState.isAiLoading,
                                        aiResponse = uiState.aiResponse,
                                        historyList = uiState.historyList,
                                        onNameChange = { viewModel.updateUserName(it) },
                                        onBirthDateChange = { viewModel.updateBirthDate(it) },
                                        onQuestionChange = { viewModel.updateQuestion(it) },
                                        onCategoryChange = { viewModel.selectCategory(it) },
                                        onAskOracle = { viewModel.askAiOracle() },
                                        onSelectHistory = { viewModel.selectHistoryItem(it) }
                                    )
                                }
                                AppTab.PREMIUM -> {
                                    PremiumScreen(
                                        isVip = uiState.isVip,
                                        onOpenVipModal = { viewModel.showVipDialog() },
                                        onOpenItem = { viewModel.openVipDetail(it) }
                                    )
                                }
                            }
                        }
                    }
                }

                // Modals & Dialogs
                if (uiState.isRewardedAdVisible) {
                    RewardedAdDialog(
                        countdown = uiState.rewardedCountdown,
                        onDismiss = { viewModel.closeRewardedAd() }
                    )
                }

                if (uiState.isVipDialogVisible) {
                    VipUpgradeDialog(
                        onConfirm = { viewModel.confirmVipSubscription() },
                        onDismiss = { viewModel.hideVipDialog() }
                    )
                }

                uiState.activeVipDetail?.let { detail ->
                    VipDetailDialog(
                        item = detail,
                        onDismiss = { viewModel.closeVipDetail() }
                    )
                }
            }
        }
    }
}

@Composable
fun AppTabBar(
    currentTab: AppTab,
    onSelectTab: (AppTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MysticSurfaceCard.copy(alpha = 0.85f)),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MysticBorder, MysticBorderGold))),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("app_tab_bar")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TabButton(
                tab = AppTab.DAILY,
                icon = Icons.Default.WbSunny,
                isSelected = currentTab == AppTab.DAILY,
                onClick = { onSelectTab(AppTab.DAILY) },
                modifier = Modifier.weight(1f)
            )
            TabButton(
                tab = AppTab.TAROT,
                icon = Icons.Default.Style,
                isSelected = currentTab == AppTab.TAROT,
                onClick = { onSelectTab(AppTab.TAROT) },
                modifier = Modifier.weight(1f)
            )
            TabButton(
                tab = AppTab.AI_ORACLE,
                icon = Icons.Default.AutoAwesome,
                isSelected = currentTab == AppTab.AI_ORACLE,
                onClick = { onSelectTab(AppTab.AI_ORACLE) },
                modifier = Modifier.weight(1f)
            )
            TabButton(
                tab = AppTab.PREMIUM,
                icon = Icons.Default.WorkspacePremium,
                isSelected = currentTab == AppTab.PREMIUM,
                onClick = { onSelectTab(AppTab.PREMIUM) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun TabButton(
    tab: AppTab,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) MysticPrimary else Color.Transparent
    val contentColor = if (isSelected) Color.White else if (tab == AppTab.PREMIUM) MysticGold else MysticTextMuted

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .testTag("tab_button_${tab.name.lowercase()}")
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = tab.title,
                tint = contentColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = tab.title,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = contentColor
            )
        }
    }
}
