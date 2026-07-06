package net.sergor.sgclient

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import net.sergor.sgclient.data.ClientProfile
import net.sergor.sgclient.data.ProfileParser
import net.sergor.sgclient.data.ProfileRepository
import net.sergor.sgclient.data.ThemeMode
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class LogEntry(
    val time: String,
    val message: String,
)

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ProfileRepository(application)

    private val _profiles = MutableStateFlow(repository.loadProfiles())
    val profiles = _profiles.asStateFlow()

    private val _selectedProfileId = MutableStateFlow(
        repository.loadSelectedProfileId()
            ?.takeIf { id -> _profiles.value.any { it.id == id } }
            ?: _profiles.value.firstOrNull()?.id,
    )
    val selectedProfileId = _selectedProfileId.asStateFlow()

    private val _themeMode = MutableStateFlow(repository.loadThemeMode())
    val themeMode = _themeMode.asStateFlow()

    private val _vpnPermissionGranted = MutableStateFlow(false)
    val vpnPermissionGranted = _vpnPermissionGranted.asStateFlow()

    private val _logs = MutableStateFlow(
        listOf(
            LogEntry(now(), "SG Client Android 001 запущен."),
            LogEntry(now(), "Сетевые движки пока не подключены."),
        ),
    )
    val logs = _logs.asStateFlow()

    fun importProfile(text: String): String? {
        return runCatching {
            val profile = ProfileParser.parse(text)
            val updated = _profiles.value + profile
            _profiles.value = updated
            _selectedProfileId.value = profile.id
            repository.saveProfiles(updated)
            repository.saveSelectedProfileId(profile.id)
            addLog("Импортирован профиль ${profile.name} (${profile.type.title}).")
        }.exceptionOrNull()?.message ?: return null
    }

    fun selectProfile(profileId: String) {
        if (_profiles.value.none { it.id == profileId }) return
        _selectedProfileId.value = profileId
        repository.saveSelectedProfileId(profileId)
        addLog("Выбран другой профиль.")
    }

    fun deleteProfile(profileId: String) {
        val removed = _profiles.value.firstOrNull { it.id == profileId } ?: return
        val updated = _profiles.value.filterNot { it.id == profileId }
        _profiles.value = updated

        if (_selectedProfileId.value == profileId) {
            _selectedProfileId.value = updated.firstOrNull()?.id
        }

        repository.saveProfiles(updated)
        repository.saveSelectedProfileId(_selectedProfileId.value)
        addLog("Удалён профиль ${removed.name}.")
    }

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
        repository.saveThemeMode(mode)
        addLog("Выбрана тема: ${mode.displayName()}.")
    }

    fun setVpnPermissionGranted(granted: Boolean) {
        if (_vpnPermissionGranted.value == granted) return
        _vpnPermissionGranted.value = granted
        addLog(
            if (granted) {
                "Android разрешил приложению использовать VpnService."
            } else {
                "Разрешение VpnService не предоставлено."
            },
        )
    }

    fun addLog(message: String) {
        _logs.value = (_logs.value + LogEntry(now(), message)).takeLast(100)
    }

    private companion object {
        val TIME_FORMATTER: DateTimeFormatter =
            DateTimeFormatter.ofPattern("HH:mm:ss")

        fun now(): String = LocalTime.now().format(TIME_FORMATTER)
    }
}

private fun ThemeMode.displayName(): String = when (this) {
    ThemeMode.SYSTEM -> "Системная"
    ThemeMode.LIGHT -> "Светлая"
    ThemeMode.DARK -> "Тёмная"
}
