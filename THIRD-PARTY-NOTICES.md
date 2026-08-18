# Third-party notices

SG Mobile uses the following open-source network engines and Android components.

## Network engines

| Component | Version | License |
|---|---:|---|
| XTLS/libXray | 26.7.28 | MIT |
| XTLS/Xray-core | upstream dependency | MPL-2.0 |
| enfein/mieru | 3.35.0 | GPL-3.0 |
| MetaCubeX/mihomo | 1.19.29 | GPL-3.0 |
| apernet/hysteria | 2.12.1 | MIT |
| SagerNet/sing-box | 1.14.0-beta.14 | GPL-3.0-or-later + upstream additional condition |
| amnezia-vpn/amneziawg-android | 3.1.20260814 | Apache-2.0 |
| amnezia-vpn/amneziawg-go/v3 | 3.1.20260814 | MIT |

## Android / build dependencies

- Android Open Source Project
- AndroidX
- Jetpack Compose / Material 3
- Kotlin
- kotlinx.coroutines
- CameraX
- ZXing Core
- Gradle / Android Gradle Plugin

Their respective licenses and notices remain applicable.

## Verified direct engine hashes

### libXray 26.7.28

```text
28b7dc9d6cc8455fcca5cbd56e387003a7bfb558128651a64899dc3a8ccff666
```

### Mieru 3.35.0 · Android ARM64

```text
53fd6a482122f964125434a7982583dc264a98e322b12882ec6a0c8fe632c3ad
```

### Mihomo 1.19.29 · Android ARM64

```text
ca44b51940fca5243f5099cf8c728bdfa86472af88a946265cf8f074fb2f0fe1
```

### Hysteria2 2.12.1 · Android ARM64

```text
92728ca71dee10508040939c0c99e69f8800519fcedb6ec35eed92b90f1b2a5f
```

### sing-box 1.14.0-beta.14 · Android ARM64 release archive

```text
411e4f0636c5201c77adcc6381444a549270d15ad84d3a82503d1b2c55ee80eb
```

### AmneziaWG Android 3.1.20260814 AAR

```text
a4554bb5dcca2a8ebb94d6c4f9ebbf1fcc0d304795eddd2a12e1bcee0bdda22f
```

The AmneziaWG Android AAR is built from the official `amnezia-vpn/amneziawg-android` tag `v3.1.20260814` with its official `amneziawg-go/v3` dependency.

## Runtime routing data

SG Mobile can use public routing-data projects at runtime. Those data files are not treated as executable network engines and are not bundled as replacement native code.

---

This document is provided for attribution and reproducibility. The license of SG Mobile itself is defined in [LICENSE](LICENSE).
