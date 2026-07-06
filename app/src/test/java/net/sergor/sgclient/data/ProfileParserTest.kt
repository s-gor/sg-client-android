package net.sergor.sgclient.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ProfileParserTest {
    @Test
    fun vlessLinkIsDetected() {
        val profile = ProfileParser.parse(
            "vless://00000000-0000-0000-0000-000000000001@" +
                "example.com:443?security=reality#France%20Primary",
        )

        assertEquals(ProfileType.VLESS, profile.type)
        assertEquals("France Primary", profile.name)
        assertEquals("example.com", profile.endpoint)
    }

    @Test
    fun hysteria2LinkIsDetected() {
        val profile = ProfileParser.parse(
            "hysteria2://secret@example.net:443/#Germany%20Backup",
        )

        assertEquals(ProfileType.HYSTERIA2, profile.type)
        assertTrue(profile.name.contains("Germany"))
    }

    @Test
    fun amneziaWgConfigurationIsDetected() {
        val profile = ProfileParser.parse(
            """
            [Interface]
            PrivateKey = hidden
            Address = 10.0.0.2/32

            [Peer]
            PublicKey = hidden
            Endpoint = vpn.example.org:51820
            AllowedIPs = 0.0.0.0/0
            """.trimIndent(),
        )

        assertEquals(ProfileType.AMNEZIA_WG, profile.type)
        assertEquals("vpn.example.org:51820", profile.endpoint)
    }
}
