package net.sergor.sgclient.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import net.sergor.sgclient.data.ThemeMode

private val LightColors = lightColorScheme(
    primary = Color(0xFF0C6E5A),
    onPrimary = Color.White,
    secondary = Color(0xFF4A635D),
    background = Color(0xFFF7FAF8),
    surface = Color(0xFFFFFFFF),
    error = Color(0xFFBA1A1A),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF31D4A0),
    onPrimary = Color(0xFF00382B),
    secondary = Color(0xFFB1CCC3),
    background = Color(0xFF0B1210),
    surface = Color(0xFF111A17),
    error = Color(0xFFFFB4AB),
)

@Composable
fun SgClientTheme(
    themeMode: ThemeMode,
    content: @Composable () -> Unit,
) {
    val systemDark = isSystemInDarkTheme()
    val useDark = when (themeMode) {
        ThemeMode.SYSTEM -> systemDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val context = LocalContext.current
    val colorScheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (useDark) {
            dynamicDarkColorScheme(context)
        } else {
            dynamicLightColorScheme(context)
        }
    } else {
        if (useDark) DarkColors else LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
