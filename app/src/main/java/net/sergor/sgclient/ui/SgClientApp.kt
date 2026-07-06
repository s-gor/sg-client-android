package net.sergor.sgclient.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Article
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Smartphone
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import net.sergor.sgclient.LogEntry
import net.sergor.sgclient.MainViewModel
import net.sergor.sgclient.data.ClientProfile
import net.sergor.sgclient.data.ProfileType
import net.sergor.sgclient.data.ThemeMode

private enum class Screen(
    val title: String,
    val icon: ImageVector,
) {
    HOME("Подключение", Icons.Rounded.Home),
    PROFILES("Профили", Icons.Rounded.List),
    LOGS("Журнал", Icons.Rounded.Article),
    SETTINGS("Настройки", Icons.Rounded.Settings),
}

@Composable
fun SgClientApp(
    viewModel: MainViewModel,
    onPrepareVpn: () -> Unit,
) {
    val profiles by viewModel.profiles.collectAsState()
    val selectedProfileId by viewModel.selectedProfileId.collectAsState()
    val themeMode by viewModel.themeMode.collectAsState()
    val vpnPermissionGranted by viewModel.vpnPermissionGranted.collectAsState()
    val logs by viewModel.logs.collectAsState()

    var screen by rememberSaveable { mutableStateOf(Screen.HOME) }
    val selectedProfile = profiles.firstOrNull { it.id == selectedProfileId }

    Scaffold(
        bottomBar = {
            NavigationBar {
                Screen.entries.forEach { item ->
                    NavigationBarItem(
                        selected = screen == item,
                        onClick = { screen = item },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                            )
                        },
                        label = { Text(item.title) },
                    )
                }
            }
        },
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp),
            ) {
                Spacer(Modifier.height(18.dp))
                AppHeader(screen.title)
                Spacer(Modifier.height(14.dp))

                when (screen) {
                    Screen.HOME -> HomeScreen(
                        selectedProfile = selectedProfile,
                        vpnPermissionGranted = vpnPermissionGranted,
                        onPrepareVpn = onPrepareVpn,
                        onOpenProfiles = { screen = Screen.PROFILES },
                    )

                    Screen.PROFILES -> ProfilesScreen(
                        profiles = profiles,
                        selectedProfileId = selectedProfileId,
                        onImport = viewModel::importProfile,
                        onSelect = viewModel::selectProfile,
                        onDelete = viewModel::deleteProfile,
                    )

                    Screen.LOGS -> LogsScreen(logs)
                    Screen.SETTINGS -> SettingsScreen(
                        themeMode = themeMode,
                        onThemeModeChanged = viewModel::setThemeMode,
                    )
                }
            }
        }
    }
}

@Composable
private fun AppHeader(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Rounded.Security,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(34.dp),
        )
        Spacer(Modifier.width(10.dp))
        Column {
            Text(
                text = "SG Client",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "$title · Android 001",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun HomeScreen(
    selectedProfile: ClientProfile?,
    vpnPermissionGranted: Boolean,
    onPrepareVpn: () -> Unit,
    onOpenProfiles: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            ),
            shape = RoundedCornerShape(24.dp),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = if (vpnPermissionGranted) {
                            Icons.Rounded.CheckCircle
                        } else {
                            Icons.Rounded.Smartphone
                        },
                        contentDescription = null,
                        tint = if (vpnPermissionGranted) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        modifier = Modifier.size(42.dp),
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Отключено",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = if (vpnPermissionGranted) {
                                "Разрешение Android VPN получено"
                            } else {
                                "Требуется системное разрешение VPN"
                            },
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                HorizontalDivider()

                Text(
                    text = selectedProfile?.name ?: "Профиль не выбран",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = selectedProfile?.let {
                        "${it.type.title} · ${it.type.engine} · ${it.endpoint}"
                    } ?: "Импортируйте профиль SG-Panel или SG-AWG-Panel.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                if (selectedProfile == null) {
                    OutlinedButton(
                        onClick = onOpenProfiles,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Icon(Icons.Rounded.Add, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Добавить профиль")
                    }
                } else {
                    Button(
                        onClick = onPrepareVpn,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Icon(Icons.Rounded.Security, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            if (vpnPermissionGranted) {
                                "Проверить разрешение VPN"
                            } else {
                                "Подготовить Android VPN"
                            },
                        )
                    }
                }
            }
        }

        StatusNote()

        CapabilityCard(
            title = "Импорт профилей",
            description = "VLESS, Hysteria2 и AmneziaWG распознаются локально.",
            icon = Icons.Rounded.Add,
        )
        CapabilityCard(
            title = "Три темы",
            description = "Системная, светлая и тёмная темы уже работают.",
            icon = Icons.Rounded.Tune,
        )
        CapabilityCard(
            title = "VpnService",
            description = "Служба объявлена корректно, но пока не создаёт TUN.",
            icon = Icons.Rounded.Security,
        )

        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun StatusNote() {
    OutlinedCard(
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outlineVariant,
        ),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Icon(
                imageVector = Icons.Rounded.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "Сборка 001 не выдаёт ложный статус подключения: " +
                    "Xray, sing-box и AmneziaWG будут интегрированы на следующем этапе.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun CapabilityCard(
    title: String,
    description: String,
    icon: ImageVector,
) {
    OutlinedCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(title, fontWeight = FontWeight.SemiBold)
                Text(
                    description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun ProfilesScreen(
    profiles: List<ClientProfile>,
    selectedProfileId: String?,
    onImport: (String) -> String?,
    onSelect: (String) -> Unit,
    onDelete: (String) -> Unit,
) {
    var showImportDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Button(
            onClick = { showImportDialog = true },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(Icons.Rounded.Add, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Импортировать профиль")
        }

        if (profiles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Rounded.List,
                        contentDescription = null,
                        modifier = Modifier.size(52.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Профилей пока нет",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        "Вставьте ссылку или конфигурацию.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        } else {
            androidx.compose.foundation.lazy.LazyColumn(
                contentPadding = PaddingValues(bottom = 18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(
                    count = profiles.size,
                    key = { profiles[it].id },
                ) { index ->
                    val profile = profiles[index]
                    ProfileCard(
                        profile = profile,
                        selected = profile.id == selectedProfileId,
                        onSelect = { onSelect(profile.id) },
                        onDelete = { onDelete(profile.id) },
                    )
                }
            }
        }
    }

    if (showImportDialog) {
        ImportProfileDialog(
            onDismiss = { showImportDialog = false },
            onImport = onImport,
        )
    }
}

@Composable
private fun ProfileCard(
    profile: ClientProfile,
    selected: Boolean,
    onSelect: () -> Unit,
    onDelete: () -> Unit,
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect),
        border = BorderStroke(
            width = if (selected) 2.dp else 1.dp,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outlineVariant
            },
        ),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = profile.name,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${profile.type.title} · ${profile.type.engine}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    text = profile.endpoint,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            if (selected) {
                Text(
                    text = "Выбран",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Удалить профиль",
                )
            }
        }
    }
}

@Composable
private fun ImportProfileDialog(
    onDismiss: () -> Unit,
    onImport: (String) -> String?,
) {
    var text by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Импорт профиля") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    "Вставьте VLESS/Hysteria2-ссылку или полный текст AmneziaWG.",
                    style = MaterialTheme.typography.bodyMedium,
                )
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        error = null
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    label = { Text("Ссылка или конфигурация") },
                    isError = error != null,
                    supportingText = error?.let { message ->
                        { Text(message) }
                    },
                )
                Text(
                    "В сборке 001 сохраняются только имя, тип и адрес. " +
                        "Секреты профиля не записываются.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    error = onImport(text)
                    if (error == null) onDismiss()
                },
            ) {
                Text("Импортировать")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        },
    )
}

@Composable
private fun LogsScreen(logs: List<LogEntry>) {
    androidx.compose.foundation.lazy.LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 18.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = logs.size,
            key = { index -> "$index-${logs[index].time}" },
        ) { index ->
            val entry = logs[index]
            OutlinedCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(
                        text = entry.time,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelMedium,
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = entry.message,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsScreen(
    themeMode: ThemeMode,
    onThemeModeChanged: (ThemeMode) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(
            text = "Тема оформления",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ThemeChoice(
                title = "Системная",
                selected = themeMode == ThemeMode.SYSTEM,
                icon = Icons.Rounded.Smartphone,
                onClick = { onThemeModeChanged(ThemeMode.SYSTEM) },
                modifier = Modifier.weight(1f),
            )
            ThemeChoice(
                title = "Светлая",
                selected = themeMode == ThemeMode.LIGHT,
                icon = Icons.Rounded.LightMode,
                onClick = { onThemeModeChanged(ThemeMode.LIGHT) },
                modifier = Modifier.weight(1f),
            )
            ThemeChoice(
                title = "Тёмная",
                selected = themeMode == ThemeMode.DARK,
                icon = Icons.Rounded.DarkMode,
                onClick = { onThemeModeChanged(ThemeMode.DARK) },
                modifier = Modifier.weight(1f),
            )
        }

        OutlinedCard {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    "SG Client Android 001",
                    fontWeight = FontWeight.Bold,
                )
                Text("Версия: 0.0.1-dev")
                Text("Автор: Ser.Gor")
                HorizontalDivider()
                Text(
                    "Поддерживаемая экосистема",
                    fontWeight = FontWeight.SemiBold,
                )
                Text("SG-Panel: VLESS и Hysteria2")
                Text("SG-AWG-Panel: AmneziaWG")
            }
        }

        OutlinedCard {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    "План следующего этапа",
                    fontWeight = FontWeight.SemiBold,
                )
                Text("1. Безопасное хранение полных профилей.")
                Text("2. Интеграция первого сетевого движка.")
                Text("3. Реальные подключение и отключение.")
                Text("4. Честная диагностика трафика и DNS.")
            }
        }

        Spacer(Modifier.height(18.dp))
    }
}

@Composable
private fun ThemeChoice(
    title: String,
    selected: Boolean,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(title) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
            )
        },
        modifier = modifier,
    )
}
