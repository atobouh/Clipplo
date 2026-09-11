package com.cliplo.app.pipeline

import java.net.URI

/** Tracking params stripped at capture. Destination is always preserved. */
private val TRACKING_PARAMS = setOf(
    "utm_source", "utm_medium", "utm_campaign", "utm_term", "utm_content",
    "utm_id", "fbclid", "gclid", "gclsrc", "igshid", "igsh", "si",
    "mc_cid", "mc_eid", "vero_conv", "vero_id", "yclid", "msclkid",
)

data class Normalized(
    val displayText: String,
    val url: String?,
    /** Dedup identity: canonical URL for links, normalized text otherwise. */
    val identity: String,
)

/**
 * Pure JVM — no Android framework dependency — so the whole pipeline is
 * unit-testable anywhere (CI, or locally with only a JDK).
 */
object Normalize {

    private val URL_LIKE = Regex("""https?://\S+|www\.\S+\.\S+""", RegexOption.IGNORE_CASE)

    fun run(raw: String): Normalized {
        val trimmed = raw.trim()
        val match = URL_LIKE.find(trimmed)
        if (match == null || trimmed.contains('\n')) {
            val flat = trimmed.replace(Regex("\\s+"), " ")
            return Normalized(displayText = trimmed, url = null, identity = "text:" + flat.lowercase().hashCode())
        }
        var candidate = match.value
        if (!candidate.startsWith("http", ignoreCase = true)) candidate = "https://$candidate"
        val cleaned = runCatching { stripTracking(candidate) }.getOrDefault(candidate)
        return Normalized(displayText = cleaned, url = cleaned, identity = "url:" + canonical(cleaned))
    }

    fun stripTracking(url: String): String {
        val qStart = url.indexOf('?')
        if (qStart < 0) return url
        val fragStart = url.indexOf('#', qStart)
        val base = url.substring(0, qStart)
        val rawQuery = if (fragStart < 0) url.substring(qStart + 1) else url.substring(qStart + 1, fragStart)
        val frag = if (fragStart < 0) "" else url.substring(fragStart)
        val host = runCatching { URI(url).host }.getOrNull()
        if (host.isNullOrEmpty()) return url
        val parts = rawQuery.split("&")
        val kept = parts.filter { part ->
            part.isNotEmpty() &&
                TRACKING_PARAMS.none { it.equals(part.substringBefore("="), ignoreCase = true) }
        }
        if (kept.size == parts.size) return url
        return buildString {
            append(base)
            if (kept.isNotEmpty()) append('?').append(kept.joinToString("&"))
            append(frag)
        }
    }

    fun canonical(url: String): String {
        val uri = runCatching { URI(url) }.getOrNull() ?: return url.lowercase()
        val host = (uri.host ?: "").lowercase().removePrefix("www.")
        var path = (uri.path ?: "").trimEnd('/')
        if (path.isEmpty()) path = "/"
        val query = (uri.rawQuery ?: "").split("&")
            .filter { it.isNotEmpty() }.sorted().joinToString("&")
        return buildString {
            append(host); append(path)
            if (query.isNotEmpty()) append('?').append(query)
        }
    }
}
