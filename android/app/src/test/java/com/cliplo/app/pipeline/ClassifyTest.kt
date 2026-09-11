package com.cliplo.app.pipeline

import com.cliplo.app.data.ClipType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pure-JVM tests for the capture pipeline. These touch no Android
 * framework classes, so they run anywhere — including GitHub Actions.
 */
class ClassifyTest {

    @Test
    fun `link keeps destination and tags service`() {
        val raw = "https://www.youtube.com/watch?v=abc123&utm_source=x&si=zzz"
        val normalized = Normalize.run(raw)
        assertEquals("https://www.youtube.com/watch?v=abc123", normalized.url)
        assertTrue(normalized.identity.startsWith("url:"))

        val service = identifyService(normalized.url)
        assertEquals("youtube", service?.key)

        val classification = Classify.run(normalized.displayText, normalized.url, service)
        assertEquals(ClipType.LINK, classification.type)
        assertFalse(classification.sensitive)
    }

    @Test
    fun `tracking params stripped, destination preserved`() {
        val cleaned = Normalize.stripTracking(
            "https://example.com/p?utm_medium=cpc&fbclid=1&q=keep&gclid=2",
        )
        assertTrue(cleaned.contains("q=keep"))
        assertFalse(cleaned.contains("utm_medium"))
        assertFalse(cleaned.contains("fbclid"))
        assertFalse(cleaned.contains("gclid"))
    }

    @Test
    fun `email and phone classify as contacts`() {
        assertEquals(ClipType.EMAIL, Classify.run("ada@example.com", null, null).type)
        assertEquals(ClipType.PHONE, Classify.run("+233 24 000 0000", null, null).type)
    }

    @Test
    fun `short numeric code is an otp and sensitive`() {
        val result = Classify.run("482916", null, null)
        assertEquals(ClipType.OTP, result.type)
        assertTrue(result.sensitive)
    }

    @Test
    fun `password material is sensitive`() {
        val result = Classify.run("db password: s3cret-hunter2", null, null)
        assertEquals(ClipType.SENSITIVE, result.type)
        assertTrue(result.sensitive)
    }

    @Test
    fun `long clips get automatic titles`() {
        val long = "How calm interfaces build trust across every surface\nsecond line here"
        assertEquals(
            "How calm interfaces build trust across every surface",
            Classify.autoTitle(long),
        )
        val veryLong = "a".repeat(200)
        assertTrue(Classify.autoTitle(veryLong).length <= 60)
    }

    @Test
    fun `duplicate text normalizes to the same identity`() {
        val a = Normalize.run("  Hello   World ")
        val b = Normalize.run("hello world")
        assertEquals(a.identity, b.identity)
    }
}
