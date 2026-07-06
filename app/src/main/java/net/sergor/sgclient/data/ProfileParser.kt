package net.sergor.sgclient.data

import java.net.URI
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.UUID

object ProfileParser {
    fun parse(input: String): ClientProfile {
        val text = input.trim()
        require(text.isNotEmpty()) { "Вставьте ссылку или конфигурацию профиля." }

        return when {
            text.startsWith("vless://", ignoreCase = true) ->
                parseUriProfile(text, ProfileType.VLESS)

            text.startsWith("hysteria2://", ignoreCase = true) ||
                text.startsWith("hy2://", ignoreCase = true) ->
                parseUriProfile(text, ProfileType.HYSTERIA2)

            text.contains("[Interface]", ignoreCase = true) &&
                text.contains("[Peer]", ignoreCase = true) ->
                parseAmneziaWg(text)

            else -> throw IllegalArgumentException(
                "Поддерживаются VLESS, Hysteria2 и конфигурации AmneziaWG.",
            )
        }
    }

    private fun parseUriProfile(
        text: String,
        type: ProfileType,
    ): ClientProfile {
        val uri = runCatching { URI.create(text) }
            .getOrElse { throw IllegalArgumentException("Ссылка профиля повреждена.") }

        val endpoint = uri.host
            ?: uri.rawAuthority
                ?.substringAfterLast('@')
                ?.substringBefore('?')
                ?.takeIf { it.isNotBlank() }
            ?: "сервер не указан"

        val decodedFragment = uri.rawFragment
            ?.takeIf { it.isNotBlank() }
            ?.let {
                runCatching {
                    URLDecoder.decode(it, StandardCharsets.UTF_8.name())
                }.getOrNull()
            }
            ?.trim()
            ?.takeIf { it.isNotBlank() }

        val name = decodedFragment ?: "${type.title} · $endpoint"

        return ClientProfile(
            id = UUID.randomUUID().toString(),
            name = name,
            type = type,
            endpoint = endpoint,
            importedAtEpochSeconds = Instant.now().epochSecond,
        )
    }

    private fun parseAmneziaWg(text: String): ClientProfile {
        val endpoint = Regex(
            pattern = """(?im)^\s*Endpoint\s*=\s*([^\r\n]+)""",
        ).find(text)
            ?.groupValues
            ?.getOrNull(1)
            ?.trim()
            ?.takeIf { it.isNotBlank() }
            ?: "сервер из конфигурации"

        return ClientProfile(
            id = UUID.randomUUID().toString(),
            name = "AmneziaWG · $endpoint",
            type = ProfileType.AMNEZIA_WG,
            endpoint = endpoint,
            importedAtEpochSeconds = Instant.now().epochSecond,
        )
    }
}
