package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.TarotDeckRepository
import com.example.data.model.ZodiacRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("มหาดวง AI", appName)
  }

  @Test
  fun `verify zodiac signs collection`() {
    val signs = ZodiacRepository.allSigns
    assertEquals(12, signs.size)
    val aries = signs.find { it.id == "aries" }
    assertNotNull(aries)
    assertEquals("ราศีเมษ", aries?.thaiName)
    assertTrue(aries?.work?.isNotBlank() == true)
    assertTrue(aries?.finance?.isNotBlank() == true)
    assertTrue(aries?.love?.isNotBlank() == true)
  }

  @Test
  fun `verify tarot deck cards`() {
    val deck = TarotDeckRepository.cards
    assertEquals(12, deck.size)
    val theFool = deck.find { it.id == 0 }
    assertNotNull(theFool)
    assertEquals("The Fool", theFool?.nameEn)
    assertTrue(theFool?.pastMeaning?.isNotBlank() == true)
    assertTrue(theFool?.presentMeaning?.isNotBlank() == true)
    assertTrue(theFool?.futureMeaning?.isNotBlank() == true)
  }
}
