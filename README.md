<div align="center">

# SG Mobile

### Современный VPN-клиент для Android

**Один интерфейс для Xray, sing-box, Hysteria2, Mieru, Mihomo и AmneziaWG**

[![Android](https://img.shields.io/badge/Android-8.0%2B-3DDC84?logo=android&logoColor=white)](https://www.android.com/)
![ABI](https://img.shields.io/badge/ABI-arm64--v8a-5865F2)
![Kotlin](https://img.shields.io/badge/Kotlin-2.x-7F52FF?logo=kotlin&logoColor=white)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4)
![Version](https://img.shields.io/badge/dev-0.0.68-00BFA5)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

**SG Mobile** объединяет разные VPN-протоколы и движки в одном Android-приложении: импорт подписок, выбор серверов, маршрутизация, контроль утечек, статистика трафика и диагностика соединения — без необходимости держать несколько отдельных клиентов.

</div>

---

## Возможности

### Протоколы и движки

| Протокол | Движок |
|---|---|
| VLESS · Reality TCP · XHTTP Reality · XHTTP TLS | Xray / libXray |
| VMess | Xray / libXray |
| Trojan | Xray / libXray |
| Hysteria2 | Hysteria |
| Mieru | Mieru / Mihomo |
| AnyTLS | sing-box |
| TUIC v5 | sing-box |
| AmneziaWG | AmneziaWG Android / wg-go |

### Подписки и профили

- импорт подписки по URL;
- QR-код с камеры;
- импорт QR из изображения;
- локальное распознавание профилей;
- список серверов с определением страны;
- проверка задержки;
- сортировка и фильтрация серверов;
- безопасное удаление подписок и профилей;
- резервное копирование и восстановление конфигурации.

### VPN и маршрутизация

- настоящий Android `VpnService` / TUN;
- маршрутизация **VPN / Direct / Block**;
- правила маршрутизации по приложениям;
- отдельные пользовательские правила;
- RU White List / RU Blocked routing data;
- fail-safe для маршрутизации;
- интеграция с Android Always-on VPN;
- защита от утечек при разрыве соединения.

### Контроль соединения

- состояние VPN на главном экране;
- текущий профиль и сервер;
- реальная статистика TUN-трафика;
- история трафика;
- мониторинг активных соединений;
- журнал событий;
- диагностика профилей и соединения.

### Интерфейс

- Jetpack Compose + Material 3;
- светлая и тёмная темы;
- русский и английский языки;
- интерфейс оптимизирован под быстрый сценарий **выбрать сервер → подключиться**.

---

## Скриншоты

> Галерея интерфейса готовится. Скриншоты будут добавлены сюда в ближайшее обновление README.

<!--
Когда изображения будут добавлены в docs/screenshots/, этот блок заменяется галереей.
Рекомендуемые имена:
01-home.png
02-subscriptions.png
03-servers.png
04-routing.png
05-traffic.png
06-settings.png
-->

---

## Текущая версия

**Development baseline:** `0.0.68-ux-bugfix-batch`  
**versionCode:** `152`  
**Минимальная версия Android:** `8.0 (API 26)`  
**Target SDK:** `36`  
**Архитектура:** `arm64-v8a`

SG Mobile активно развивается. Интерфейс, диагностика и поведение отдельных функций могут изменяться между development-сборками.

---

## Версии сетевых компонентов

| Компонент | Версия |
|---|---:|
| libXray | 26.7.28 |
| Hysteria2 | 2.12.1 |
| sing-box | 1.14.0-beta.14 |
| Mieru | 3.35.0 |
| Mihomo | 1.19.29 |
| AmneziaWG Android | 3.1.20260814 |

SG Mobile использует проверенные версии сетевых компонентов. Сведения о сторонних проектах, лицензиях и контрольных суммах находятся в [THIRD-PARTY-NOTICES.md](THIRD-PARTY-NOTICES.md).

---

## Сборка

Проект собирается под Windows с локальным Android toolchain.

Базовые требования:

- JDK 17;
- Android SDK 36;
- Android Build Tools 36.0.0;
- NDK 29;
- Gradle 9.4.1.

Release-сборка использует R8 и resource shrinking. Сетевые runtime-компоненты проходят проверку перед упаковкой APK.

Начиная с ветки SG Mobile 067 используется **постоянная подпись SG Mobile** вместо автоматически создаваемого Android `debug.keystore`. Приватный signing key не хранится в репозитории.

---

## Безопасность проекта

SG Mobile не должен молча подменять сетевые движки или создавать новый ключ подписи при его потере. Для release-процесса используются фиксированные версии, контрольные суммы и отдельная постоянная подпись приложения.

Если обнаружена проблема в работе приложения, приложите к сообщению:

- версию SG Mobile;
- тип профиля/протокол;
- краткое описание воспроизведения;
- фрагмент журнала без лишних персональных данных.

---

## Экосистема SG

SG Mobile развивается как Android-клиент экосистемы проектов **Ser.Gor** и рассчитан как на обычные подписки, так и на использование с собственными SG-серверами.

---

## Лицензия

Основной код SG Mobile распространяется по лицензии [MIT](LICENSE).  
Сторонние движки и библиотеки распространяются на условиях своих лицензий — см. [THIRD-PARTY-NOTICES.md](THIRD-PARTY-NOTICES.md).

---

<div align="center">

**SG Mobile · Android VPN client by Ser.Gor**

</div>
