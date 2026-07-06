package net.sergor.sgclient

import android.app.Activity
import android.net.VpnService
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import net.sergor.sgclient.ui.SgClientApp
import net.sergor.sgclient.ui.theme.SgClientTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    private val vpnPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        viewModel.setVpnPermissionGranted(result.resultCode == Activity.RESULT_OK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themeMode by viewModel.themeMode.collectAsState()

            SgClientTheme(themeMode = themeMode) {
                SgClientApp(
                    viewModel = viewModel,
                    onPrepareVpn = ::prepareVpn,
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.setVpnPermissionGranted(VpnService.prepare(this) == null)
    }

    private fun prepareVpn() {
        val permissionIntent = VpnService.prepare(this)
        if (permissionIntent == null) {
            viewModel.setVpnPermissionGranted(true)
        } else {
            vpnPermissionLauncher.launch(permissionIntent)
        }
    }
}
