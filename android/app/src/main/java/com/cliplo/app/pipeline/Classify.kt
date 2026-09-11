package com.cliplo.app.pipeline

import com.cliplo.app.data.ClipType
import java.net.URI

/** Brand accent per service, from the ui-proto service rules. */
data class ServiceInfo(val key: String, val label: String, val accentHex: String)

private val SERVICES = listOf(
    ServiceInfo("youtube", "YouTube", "#FF4A42"),
    ServiceInfo("github", "GitHub", "#E6E8EB"),
    ServiceInfo("x", "X", "#EEEEEE"),
    ServiceInfo("reddit", "Reddit", "#FF5B4D"),
    ServiceInfo("medium", "Medium", "#6FB4D0"),
    ServiceInfo("telegram", "Telegram", "#2AA3E0"),
    ServiceInfo("instagram", "Instagram", "#D159A8"),
    ServiceInfo("amazon", "Amazon", "#FF9900"),
    ServiceInfo("whatsapp", "WhatsApp", "#5FD068"),
    ServiceInfo("arxiv", "arXiv", "#B31B1B"),
)

fun identifyService(url: String?): ServiceInfo? {
    if (url == null) return null
    val host = runCatching { URI(url).host }.getOrNull()?.lowercase() ?: return null
    return SERVICES.firstOrNull { info ->
        host.contains(info.key) ||
            (info.key == "x" && (host == "x.com" || host == "twitter.com")) ||
            (info.key == "arxiv" && host.contains("arxiv"))
    }
}

private val PHONE = Regex("""^\+?[0-9][0-9\s\-().]{6,19}$""")
private val EMAIL = Regex("""^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$""")
private val OTP = Regex("""\b\d{4,8}\b""")
private val CODE_HINT = Regex("""[{};]\s*$|^\s*(fun|val|var|class|def|import|from|package|public|private|const|let)\b""", RegexOption.MULTILINE)
private val SENSITIVE_HINT = Regex("""(?i)(password|passwd|secret|api[_-]?key|bearer\s+[A-Za-z0-9\-._~+/]+|-----BEGIN [A-Z ]*PRIVATE KEY-----|eyJ[A-Za-z0-9\-_]{10,}\.)""")

data class Classification(
    val type: ClipType,
    val title: String,
    val tags: List<String>,
    val sensitive: Boolean,
)

object Classify {

    fun run(text: String, url: String?, service: ServiceInfo?): Classification {
        val trimmed = text.trim()
        val sensitive = SENSITIVE_HINT.containsMatchIn(trimmed)
        if (sensitive) {
            return Classification(ClipType.SENSITIVE, autoTitle(trimmed), listOf("sensitive"), true)
        }
        if (url != null) {
            val tags = mutableListOf("link")
            service?.let { tags += it.key }
            return Classification(ClipType.LINK, autoTitle(trimmed, maxLen = 80), tags, false)
        }
        if (EMAIL.matches(trimmed)) return Classification(ClipType.EMAIL, trimmed, listOf("contact"), false)
        if (PHONE.matches(trimmed)) return Classification(ClipType.PHONE, trimmed, listOf("contact"), false)
        if (trimmed.length <= 8 && OTP.matches(trimmed)) {
            return Classification(ClipType.OTP, "Authentication code", listOf("otp", "sensitive"), true)
        }
        if (CODE_HINT.containsMatchIn(trimmed) && trimmed.contains('\n')) {
            return Classification(ClipType.CODE, autoTitle(trimmed), listOf("code"), false)
        }
        if (trimmed.length > 140 && trimmed.contains('\n')) {
            return Classification(ClipType.NOTE, autoTitle(trimmed), listOf("note"), false)
        }
        return Classification(ClipType.NOTE, autoTitle(trimmed), listOf("note"), false)
    }

    /** Automatic titles for long clips (MUST_HAVE smart-organization). */
    fun autoTitle(text: String, maxLen: Int = 60): String {
        val firstLine = text.lineSequence().firstOrNull()?.trim().orEmpty()
        if (firstLine.length <= maxLen) return firstLine.ifEmpty { "Untitled clip" }
        return firstLine.take(maxLen - 1).trimEnd() + "…"
    }
}
