package net.sergor.sgclient.vpn

import android.content.Intent
import android.net.VpnService

/**
 * Основа Android VpnService.
 *
 * В сборке 001 служба намеренно не создаёт TUN-интерфейс и не перехватывает
 * трафик. Это исключает ложное состояние "подключено" до интеграции Xray,
 * sing-box и AmneziaWG.
 */
class SgVpnService : VpnService() {
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int = START_NOT_STICKY

    override fun onRevoke() {
        stopSelf()
        super.onRevoke()
    }
}
