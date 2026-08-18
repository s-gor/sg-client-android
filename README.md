<div align="center">

# SG Mobile

### Универсальный VPN-клиент для Android

> **Один APK. Несколько сетевых движков. Подписки, маршрутизация, мониторинг и защита от утечек — в одном интерфейсе.**

![Version](https://img.shields.io/badge/version-0.0.68-00BFA5)
![Android](https://img.shields.io/badge/Android-8.0%2B-3DDC84?logo=android&logoColor=white)
![ABI](https://img.shields.io/badge/ABI-arm64--v8a-5865F2)
![Xray](https://img.shields.io/badge/Xray-libXray-2563EB)
![sing-box](https://img.shields.io/badge/sing--box-supported-0EA5E9)
![Hysteria2](https://img.shields.io/badge/Hysteria2-supported-8B5CF6)
![Mieru](https://img.shields.io/badge/Mieru-supported-7C3AED)
![Mihomo](https://img.shields.io/badge/Mihomo-supported-6366F1)
![AmneziaWG](https://img.shields.io/badge/AmneziaWG-AWG2%20%2F%20AWG3-6D5BD0)
![Kotlin](https://img.shields.io/badge/Kotlin-2.x-7F52FF?logo=kotlin&logoColor=white)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

**SG Mobile** — Android-клиент экосистемы **Ser.Gor**, рассчитанный не на один протокол и не на один сервер.

Он объединяет **Xray, sing-box, Hysteria2, Mieru, Mihomo и AmneziaWG** и даёт единый сценарий работы: добавить профиль или подписку, проверить серверы, выбрать маршрут, подключиться и видеть, что реально происходит с трафиком.

**Без зоопарка отдельных VPN-приложений под каждый протокол.**

</div>

---

## Что SG Mobile умеет уже сейчас

| Область | Возможности |
|---|---|
| **Протоколы** | VLESS REALITY RAW/TCP, VLESS XHTTP REALITY, VLESS XHTTP TLS, VMess, Trojan, Hysteria2, Mieru, AnyTLS, TUIC v5, AmneziaWG AWG2/AWG3 |
| **Движки** | libXray, Hysteria2, sing-box, Mieru, Mihomo, AmneziaWG Android / wg-go |
| **Импорт** | отдельные ссылки, AmneziaWG-конфиги, URL подписки, обычные текстовые/Base64 feeds, SG Subscription v1, QR с камеры и QR из изображения |
| **Подписки** | массовый импорт, обновление, определение страны, проверка задержки, доступность, сортировка и фильтры |
| **Routing** | VPN / Direct / Block, RU White List, RU Blocked, пользовательские правила, GeoFiles, routing fail-safe |
| **Per-App VPN** | весь трафик, только выбранные приложения или исключение выбранных приложений |
| **Защита** | Leak Protection, IPv4 / IPv6 / DNS policy, Android Always-on VPN и Kill Switch integration |
| **Мониторинг** | реальные TUN-счётчики, скорость, история трафика, активные назначения и разложение трафика по VPN / Direct / Block |
| **Backup** | экспорт, проверка и восстановление профилей, подписок, настроек, routing, Per-App и статистики |
| **Интерфейс** | русский / English, светлая и тёмная темы, быстрый выбор сервера, журнал и диагностика |

---

## Протоколы и реальные runtime

SG Mobile не маскирует разные технологии под один условный «VPN-профиль». Для каждого семейства используется соответствующий runtime.

| Протокол / формат | Runtime | Что поддерживается |
|---|---|---|
| **VLESS REALITY** | libXray | RAW/TCP, REALITY, Vision-compatible профиль |
| **VLESS XHTTP REALITY** | libXray | XHTTP, REALITY, path/mode/extra |
| **VLESS XHTTP TLS** | libXray | XHTTP поверх TLS |
| **VMess** | libXray | URI / subscription import |
| **Trojan** | libXray | URI / subscription import |
| **Hysteria2** | Hysteria2 | `hysteria2://` / `hy2://`, включая Gecko/Salamander obfs |
| **Mieru** | Mieru / Mihomo | `mieru://` / `mierus://` |
| **AnyTLS** | sing-box | AnyTLS поверх TCP/TLS |
| **TUIC v5** | sing-box | TUIC URI, UUID/password, SNI и transport parameters |
| **AmneziaWG** | AmneziaWG Android / wg-go | raw `[Interface]` + `[Peer]`, распознавание **AWG2 / AWG3** |

Поддерживаемые записи валидируются до подключения: приложение проверяет обязательные параметры профиля и не должно выдавать ложное состояние «подключено» для заведомо неполной конфигурации.

---

## Подписки — не просто «вставить ссылку»

SG Mobile рассчитан и на небольшие личные списки, и на большие подписки с сотнями серверов.

Поддерживаются:

- обычные HTTPS-подписки;
- текстовые списки URI;
- Base64-обёрнутые feeds;
- собственный строгий контракт **SG Subscription v1**;
- VLESS, VMess, Trojan, Hysteria2, Mieru, AnyTLS, TUIC и AmneziaWG внутри одной подписки;
- безопасные HTTPS redirect для источника подписки;
- терпимый импорт сторонних feeds: неизвестная запись не обязана ломать всю подписку;
- строгая проверка SG Subscription v1: заявленный SG-CONFIG должен действительно импортироваться.

### Добавление подписки

Пользователь может:

- вставить URL вручную;
- вставить его из буфера;
- отсканировать QR камерой;
- импортировать QR из изображения.

### Работа с большим списком серверов

После импорта доступны:

- определение страны и флагов;
- проверка задержки;
- отметка успешного/неуспешного результата;
- сортировка;
- фильтрация по задержке, стране и протоколу;
- исключение страны;
- выбор конкретного сервера без автоматического подключения.

То есть подписка в SG Mobile — это **управляемый каталог серверов**, а не длинная простыня ссылок.

---

## Routing: VPN / Direct / Block

В SG Mobile маршрутизация является отдельной частью клиента, а не скрытой настройкой внутри профиля.

Основные действия:

- **VPN** — отправить трафик через активный туннель;
- **Direct** — вывести напрямую;
- **Block** — заблокировать.

На этой модели построены:

- системная routing matrix;
- пользовательские правила;
- отдельная политика для LAN;
- **RU White List**;
- **RU Blocked**;
- GeoFiles-based правила;
- routing fail-safe.

Маршрутизация не должна молча делать неожиданный fallback: явное пользовательское правило остаётся явным правилом.

---

## GeoFiles

Для доменных и IP-правил есть отдельное управление GeoFiles.

Из интерфейса можно:

- скачать или обновить пару routing-файлов;
- использовать собственный источник;
- импортировать локальные GeoFiles;
- проверить установленный набор;
- выполнить rollback;
- удалить локальные routing data и загрузить заново.

GeoFiles отделены от VPN-профилей и от runtime-ядер, поэтому обновление routing data не требует менять сам профиль подключения.

---

## Per-App VPN

Android-приложения можно включать в VPN policy отдельно.

Режимы:

- **All apps** — весь трафик устройства через VPN policy;
- **Only selected** — через VPN работают только выбранные приложения;
- **Exclude selected** — выбранные приложения идут вне VPN.

Per-App правила сохраняются в конфигурации SG Mobile и входят в backup.

---

## Leak Protection

SG Mobile содержит отдельный слой проверки и применения политики защиты от утечек.

Он контролирует состояние VPN policy для:

- **IPv4**;
- **IPv6**;
- **DNS**.

Также предусмотрена интеграция с системными возможностями Android:

- **Always-on VPN**;
- **Kill Switch / Block connections without VPN**;
- Per-App scope.

При этом намеренный `Direct` или сознательное исключение приложения из Per-App VPN считается пользовательской политикой, а не «скрытой утечкой».

Начиная с актуальной линии SG Mobile простое открытие приложения **не забирает Android VPN slot** у уже работающего VPN. Право на VPN запрашивается только при явном подключении/переподключении.

---

## Трафик и мониторинг

SG Mobile не ограничивается индикатором «Connected».

### Реальные TUN-счётчики

Приложение собирает реальные локальные показатели:

- download / upload;
- текущую скорость;
- объём за сессию;
- накопленную статистику;
- историю примерно за последние 15 минут;
- статистику по профилям.

### Мониторинг маршрутов

Для Xray runtime доступен локальный мониторинг destination metadata и route counters.

Можно видеть:

- назначение;
- протокол;
- route/outbound;
- число обращений;
- свежесть соединения;
- сколько трафика ушло через **VPN / Direct / Block**.

Мониторинг работает локально. Payload/content трафика не читается и не сохраняется.

---

## Backup & Restore

Backup в SG Mobile — это не экспорт одной ссылки.

В полный backup входят:

- профили;
- подписки;
- настройки;
- Per-App VPN;
- Routing rules;
- настройки отображения подписок;
- статистика трафика.

Из интерфейса доступны:

- создание restore point;
- экспорт полного backup;
- предварительная проверка backup;
- восстановление.

Логи, скачанные GeoFiles и runtime binaries намеренно не являются частью пользовательского backup.

---

## Профили и диагностика

SG Mobile хранит профили отдельно от подписок: можно работать как с единичными конфигурациями, так и с провайдерскими feeds.

Для проверки доступны:

- latency/probe;
- состояние доступности;
- журнал событий;
- диагностика активного профиля;
- отдельные сообщения об ошибках импорта и runtime.

Удаление подписки, которая сейчас используется VPN, не должно происходить молча: приложение предлагает отключить VPN и удалить её после завершения disconnect.

---

## Интерфейс

<div align="center">
<table>
<tr>
<td align="center"><b>Главная</b><br><sub>VPN state, профиль, задержка и быстрые действия</sub></td>
<td align="center"><b>Маршрутизация</b><br><sub>VPN / Direct / Block и пользовательская policy</sub></td>
<td align="center"><b>О приложении</b><br><sub>Версия, архитектура и runtime</sub></td>
</tr>
<tr>
<td><img src="docs/screenshots/01-home-connected.jpg" width="260" alt="SG Mobile — главная"></td>
<td><img src="docs/screenshots/02-routing.jpg" width="260" alt="SG Mobile — маршрутизация"></td>
<td><img src="docs/screenshots/03-about.jpg" width="260" alt="SG Mobile — о приложении"></td>
</tr>
</table>
</div>

Интерфейс построен на **Kotlin + Jetpack Compose + Material 3** и рассчитан на короткий ежедневный сценарий:

> **открыть → выбрать сервер → VPN On → видеть реальное состояние соединения**

Доступны русский и английский языки, светлая и тёмная темы.

---

## Текущая development-линия

> **SG Mobile 0.0.68 / versionCode 152** — текущий baseline UX/bugfix-линии.

В 068 собраны в один пакет несколько важных исправлений:

- исправлена навигация «Выбрать профиль»;
- языковой selector упрощён до **Русский / English**;
- доработана локализация подписок и фильтров;
- список серверов очищен от лишних quick chips и protocol badges;
- удаление активной подписки стало явной операцией с disconnect и progress;
- экран сетевых компонентов сделан информационным — без ложного ощущения, что отдельное ядро можно обновить из приложения;
- сохранена постоянная подпись SG Mobile;
- исправлен преждевременный захват Android VPN slot при простом открытии приложения.

SG Mobile активно развивается; отдельные элементы UI могут меняться между development-сборками.

---

## Версии сетевых компонентов

| Компонент | Версия |
|---|---:|
| **libXray** | 26.7.28 |
| **Hysteria2** | 2.12.1 |
| **sing-box** | 1.14.0-beta.14 |
| **Mieru** | 3.35.0 |
| **Mihomo** | 1.19.29 |
| **AmneziaWG Android** | 3.1.20260814 |

Экран в приложении показывает установленные версии компонентов **только как информацию**. Отдельное runtime-ядро не подменяется произвольно после установки APK.

Лицензии сторонних компонентов и их происхождение описаны в [THIRD-PARTY-NOTICES.md](THIRD-PARTY-NOTICES.md).

---

## Android baseline

- **Android:** 8.0+ / API 26+
- **Target SDK:** 36
- **ABI:** arm64-v8a
- **UI:** Jetpack Compose / Material 3
- **JDK для сборки:** 17
- **Android Build Tools:** 36.0.0
- **NDK:** 29
- **Gradle:** 9.4.1

SG Mobile использует настоящий Android `VpnService` / TUN и соответствующие native/runtime компоненты для разных протокольных семейств.

---

## Сборка и подпись

Release-процесс рассчитан на воспроизводимую локальную Windows-сборку.

Сборщик:

- проверяет локальный Android toolchain;
- проверяет runtime-компоненты перед упаковкой;
- собирает APK с R8/resource shrinking;
- использует постоянную подпись SG Mobile.

Начиная с линии 067 release APK **не зависит от пользовательского Android `debug.keystore`**.

Приватный signing key не хранится в GitHub-репозитории. Если постоянный ключ отсутствует, нормальный release workflow должен остановиться, а не молча создать новую идентичность приложения.

---

## Экосистема SG

SG Mobile рассчитан и на обычные сторонние подписки, и на совместную работу с серверными проектами экосистемы **Ser.Gor**.

В частности, собственный **SG Subscription v1** позволяет передавать в одной подписке как URI-профили, так и AmneziaWG AWG2/AWG3-конфигурации с контролируемым контрактом импорта.

---

## Безопасность и приватность

Ключевые принципы проекта:

- не показывать ложное «подключено», если runtime фактически не поднялся;
- не подменять release signing identity;
- не подменять произвольно сетевые runtime после установки APK;
- не читать payload в модуле мониторинга соединений;
- различать намеренный Direct/Per-App bypass и настоящую ошибку VPN policy;
- не забирать системный VPN slot только из-за запуска интерфейса.

---

## Лицензия

Основной код SG Mobile распространяется по лицензии [MIT](LICENSE).

Сторонние runtime и библиотеки сохраняют собственные лицензии — см. [THIRD-PARTY-NOTICES.md](THIRD-PARTY-NOTICES.md).

---

<div align="center">

### SG Mobile

**Один Android-клиент для современной многопротокольной VPN-конфигурации.**

**Ser.Gor**

</div>
