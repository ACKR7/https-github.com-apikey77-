package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.FortuneHistoryItem
import com.example.data.model.TarotCard
import com.example.data.model.TarotDeckRepository
import com.example.data.model.VipInsightItem
import com.example.data.model.ZodiacRepository
import com.example.data.model.ZodiacSign
import com.example.data.repository.FortuneRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

enum class AppTab(val title: String) {
    DAILY("รายวัน"),
    TAROT("ไพ่ยิปซี"),
    AI_ORACLE("AI อะราเคิล"),
    PREMIUM("พรีเมียม")
}

data class MainUiState(
    val currentTab: AppTab = AppTab.DAILY,
    val selectedZodiac: ZodiacSign = ZodiacRepository.allSigns.first(),
    val isDeepInsightUnlocked: Boolean = false,
    
    // Tarot
    val deck: List<TarotCard> = TarotDeckRepository.cards,
    val selectedTarotCards: List<TarotCard> = emptyList(),
    val isTarotRevealed: Boolean = false,
    
    // AI Oracle
    val userName: String = "",
    val birthDate: String = "",
    val question: String = "",
    val category: String = "การงาน",
    val isAiLoading: Boolean = false,
    val aiResponse: String? = null,
    val historyList: List<FortuneHistoryItem> = emptyList(),
    
    // VIP & Monetization
    val isVip: Boolean = false,
    val isBannerVisible: Boolean = true,
    val isVipDialogVisible: Boolean = false,
    val isRewardedAdVisible: Boolean = false,
    val rewardedCountdown: Int = 5,
    val activeVipDetail: VipInsightItem? = null,
    val toastMessage: String? = null
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FortuneRepository(application.applicationContext)

    private val _uiState = MutableStateFlow(
        MainUiState(
            isVip = repository.isVipActive(),
            isBannerVisible = !repository.isVipActive(),
            historyList = repository.getHistory(),
            isDeepInsightUnlocked = repository.isZodiacDeepInsightUnlocked(ZodiacRepository.allSigns.first().id)
        )
    )
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var countdownJob: Job? = null

    fun selectTab(tab: AppTab) {
        _uiState.value = _uiState.value.copy(currentTab = tab)
    }

    // Zodiac logic
    fun selectZodiac(sign: ZodiacSign) {
        val isUnlocked = repository.isZodiacDeepInsightUnlocked(sign.id)
        _uiState.value = _uiState.value.copy(
            selectedZodiac = sign,
            isDeepInsightUnlocked = isUnlocked
        )
    }

    // Tarot logic
    fun togglePickCard(card: TarotCard) {
        val currentSelected = _uiState.value.selectedTarotCards
        if (currentSelected.contains(card)) {
            // Already picked, do nothing
            return
        }
        if (currentSelected.size < 3) {
            val updated = currentSelected + card
            _uiState.value = _uiState.value.copy(selectedTarotCards = updated)
        }
    }

    fun revealTarot() {
        if (_uiState.value.selectedTarotCards.size == 3) {
            _uiState.value = _uiState.value.copy(isTarotRevealed = true)
        }
    }

    fun resetTarot() {
        _uiState.value = _uiState.value.copy(
            selectedTarotCards = emptyList(),
            isTarotRevealed = false,
            deck = TarotDeckRepository.cards.shuffled()
        )
    }

    // AI Oracle logic
    fun updateUserName(name: String) {
        _uiState.value = _uiState.value.copy(userName = name)
    }

    fun updateBirthDate(date: String) {
        _uiState.value = _uiState.value.copy(birthDate = date)
    }

    fun updateQuestion(q: String) {
        _uiState.value = _uiState.value.copy(question = q)
    }

    fun selectCategory(cat: String) {
        _uiState.value = _uiState.value.copy(category = cat)
    }

    fun askAiOracle() {
        val q = _uiState.value.question.trim()
        if (q.isEmpty()) {
            showToast("กรุณาระบุคำถามหรือเรื่องที่ต้องการทำนาย")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isAiLoading = true, aiResponse = null)
            val name = _uiState.value.userName.trim()
            val bDate = _uiState.value.birthDate.trim()
            val cat = _uiState.value.category

            val result = repository.consultAiOracle(name, bDate, q, cat)

            val historyItem = FortuneHistoryItem(
                id = UUID.randomUUID().toString(),
                name = if (name.isNotBlank()) name else "ผู้ศรัทธา",
                question = q,
                answer = result,
                category = cat,
                timestamp = System.currentTimeMillis()
            )
            repository.saveHistoryItem(historyItem)

            _uiState.value = _uiState.value.copy(
                isAiLoading = false,
                aiResponse = result,
                historyList = repository.getHistory()
            )
        }
    }

    fun selectHistoryItem(item: FortuneHistoryItem) {
        _uiState.value = _uiState.value.copy(
            userName = item.name,
            question = item.question,
            category = item.category,
            aiResponse = item.answer
        )
    }

    // Ad & Monetization
    fun startRewardedAd() {
        _uiState.value = _uiState.value.copy(
            isRewardedAdVisible = true,
            rewardedCountdown = 5
        )
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            for (i in 5 downTo 1) {
                _uiState.value = _uiState.value.copy(rewardedCountdown = i)
                delay(1000)
            }
            _uiState.value = _uiState.value.copy(rewardedCountdown = 0)
            delay(500)
            completeRewardedAd()
        }
    }

    private fun completeRewardedAd() {
        val currentSign = _uiState.value.selectedZodiac
        repository.unlockZodiacDeepInsight(currentSign.id)
        _uiState.value = _uiState.value.copy(
            isRewardedAdVisible = false,
            isDeepInsightUnlocked = true
        )
        showToast("✨ ปลดล็อกคำแนะนำเชิงลึกสำเร็จแล้วผ่าน Google AdMob Rewarded Ad!")
    }

    fun closeRewardedAd() {
        countdownJob?.cancel()
        _uiState.value = _uiState.value.copy(isRewardedAdVisible = false)
    }

    fun showVipDialog() {
        _uiState.value = _uiState.value.copy(isVipDialogVisible = true)
    }

    fun hideVipDialog() {
        _uiState.value = _uiState.value.copy(isVipDialogVisible = false)
    }

    fun confirmVipSubscription() {
        repository.setVipActive(true)
        _uiState.value = _uiState.value.copy(
            isVip = true,
            isBannerVisible = false,
            isVipDialogVisible = false,
            isDeepInsightUnlocked = true
        )
        showToast("👑 ยินดีต้อนรับสู่สมาชิก VIP รายเดือน! ปลดล็อกทุกฟีเจอร์และซ่อนโฆษณาเรียบร้อย")
    }

    fun closeBannerAd() {
        _uiState.value = _uiState.value.copy(isBannerVisible = false)
        showToast("ปิดแบนเนอร์โฆษณาชั่วคราว")
    }

    fun openVipDetail(item: VipInsightItem) {
        _uiState.value = _uiState.value.copy(activeVipDetail = item)
    }

    fun closeVipDetail() {
        _uiState.value = _uiState.value.copy(activeVipDetail = null)
    }

    fun showToast(msg: String) {
        _uiState.value = _uiState.value.copy(toastMessage = msg)
    }

    fun dismissToast() {
        _uiState.value = _uiState.value.copy(toastMessage = null)
    }
}
