# SG Client for Android

![Android](https://img.shields.io/badge/Android-8.0%2B-3DDC84)
![Kotlin](https://img.shields.io/badge/Kotlin-2.3-7F52FF)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4)
![Stage](https://img.shields.io/badge/stage-ANDROID--001-orange)

Единый Android-клиент экосистемы **SG-Panel** и **SG-AWG-Panel**.

## Текущий этап: SG-CLIENT-ANDROID-001

Это первая проверочная основа приложения, а не публичный VPN-релиз.

Уже реализовано:

- Kotlin и Jetpack Compose;
- системная, светлая и тёмная темы;
- главный экран в стиле SG Client;
- локальное распознавание VLESS, Hysteria2 и AmneziaWG;
- список профилей, выбор и удаление;
- журнал действий;
- запрос системного разрешения Android VPN;
- корректно объявленная служба `VpnService`;
- unit-тесты распознавания профилей;
- автоматическая сборка тестового APK в GitHub Actions.

## Важное ограничение

Сборка 001 **не создаёт TUN-интерфейс и не направляет трафик**. Xray, sing-box и AmneziaWG ещё не встроены.

Это сделано намеренно: приложение не показывает ложное состояние «Подключено» до появления настоящего сетевого движка.

При импорте в 001 сохраняются только:

- имя;
- тип профиля;
- адрес сервера.

Пароли, приватные ключи и полный текст конфигурации не сохраняются.

## Поддерживаемые форматы импорта

- `vless://...`
- `hysteria2://...` и `hy2://...`
- полный текст AmneziaWG с секциями `[Interface]` и `[Peer]`

## Сборка

Требуются:

- Android Studio с JDK 17;
- Android SDK 36;
- Windows 10/11 для комплектного CMD.

Запустите:

```text
BUILD-SG-CLIENT-ANDROID-001.cmd
```

После успешной сборки в корне появится:

```text
SG-CLIENT-ANDROID-001-debug.apk
```

## GitHub Actions

Каждый push в `main` выполняет unit-тесты и создаёт временный artifact:

```text
SG-CLIENT-ANDROID-001-debug.apk
```

Это **не GitHub Release**. Artifact хранится 14 дней и предназначен только для проверки.

## Следующий этап

`SG-CLIENT-ANDROID-002`:

1. защищённое хранение полных профилей;
2. интеграция первого настоящего движка;
3. реальные включение и отключение;
4. проверка трафика, DNS и состояния TUN.

## Автор

**Ser.Gor**


## Сборка

Основной способ проверки сборки 001 — **GitHub Actions**. После каждого push в `main` workflow сам устанавливает Gradle 9.4.1, запускает unit-тесты и собирает debug APK.

Откройте вкладку **Actions**, выберите последний успешный запуск и скачайте artifact:

```text
SG-CLIENT-ANDROID-001
└── SG-CLIENT-ANDROID-001-debug.apk
```

Локальный `BUILD-SG-CLIENT-ANDROID-001.cmd` работает только если `gradle.exe`, JDK 17 и Android SDK уже установлены на компьютере.
