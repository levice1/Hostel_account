package com.example.hostelaccount

import com.example.hostelaccount.viewmodel.util.ProcessingDate
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
@Test
    fun test_calculating_different_days(){
        val daysDiff = ProcessingDate().calculateDaysDifference("01.05.23")
        assertEquals(17,daysDiff)
    }
}