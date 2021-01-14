package com.nicole.petnanny

import com.nicole.petnanny.util.toDisplayFormat
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MyUnitTest {
    @Before
    fun before() {
        println("before")
    }

    @Test
    fun testTransferTimeFormat() {
        //    實際值
        val time: Long = 1609459200000L
        //    預期時間(+8 台北時間)
        val dateTime = "2021-01-01 08:00:00"

        Assert.assertEquals(dateTime, time.toDisplayFormat())
//        assertEquals(null, expected, actual)
        println("testOK")
    }

    @After
    fun after() {
        println("after")
    }
}