package net.sergor.sgclient.data

data class ClientProfile(
    val id: String,
    val name: String,
    val type: ProfileType,
    val endpoint: String,
    val importedAtEpochSeconds: Long,
)

enum class ProfileType(
    val title: String,
    val engine: String,
) {
    VLESS("VLESS", "Xray"),
    HYSTERIA2("Hysteria2", "sing-box"),
    AMNEZIA_WG("AmneziaWG", "AmneziaWG"),
}

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK,
}
