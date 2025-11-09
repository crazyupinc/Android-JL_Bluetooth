# Android JL_Bluetooth SDK 온보딩 가이드

이 문서는 **Android JL_Bluetooth SDK** 프로젝트에 새로 참여하는 개발자를 위한 온보딩 가이드입니다.

## 📋 목차

1. [프로젝트 소개](#프로젝트-소개)
2. [시작하기 전에](#시작하기-전에)
3. [개발 환경 설정](#개발-환경-설정)
4. [프로젝트 구조](#프로젝트-구조)
5. [빌드 및 실행](#빌드-및-실행)
6. [개발 가이드](#개발-가이드)
7. [테스트](#테스트)
8. [문제 해결](#문제-해결)
9. [추가 리소스](#추가-리소스)

---

## 프로젝트 소개

**Android JL_Bluetooth SDK**는 **珠海市杰理科技股份有限公司**(JieLi Technology)에서 개발한 Android용 블루투스 SDK입니다. 이 SDK는 JieLi의 음향기기(스피커, 이어폰 등) 제품을 블루투스를 통해 제어하기 위한 개발 플랫폼을 제공합니다.

### 주요 기능

- 📱 **BLE 및 SPP 통신 지원**: 다양한 블루투스 통신 방식 지원
- 🎵 **음악 제어**: 재생, 일시정지, 다음/이전 곡 등
- 🔊 **음량 및 EQ 설정**: 세밀한 오디오 제어
- 📻 **FM 라디오 제어**: FM 기능이 있는 기기 지원
- 🔄 **OTA 펌웨어 업데이트**: 무선 펌웨어 업그레이드
- 🌐 **파일 브라우징**: 기기 내 파일 탐색 및 관리
- ⏰ **RTC 및 알람**: 실시간 시계 및 알람 기능
- 🎤 **음성 녹음**: 오디오 녹음 기능
- 🎨 **화면 제어**: 701N/707N 컬러 스크린 제어 지원

### 지원 칩셋

- AC707N
- JL701N
- AC697N
- AC696N
- AC695N
- AC693N

---

## 시작하기 전에

### 필수 요구사항

#### 1. 하드웨어
- Android 스마트폰 (Android 5.1 이상)
- BLE를 지원하는 Android 기기
- RCSP 프로토콜을 지원하는 JieLi 블루투스 기기 (테스트용)

#### 2. 소프트웨어
- **Java Development Kit (JDK)**: JDK 8 이상
- **Android Studio**: 최신 버전 권장 (Arctic Fox 이상)
- **Git**: 버전 관리를 위해 필요

#### 3. 개발 지식
- Java 프로그래밍 기본 지식
- Android 앱 개발 기초 (Activity, Fragment, Service 등)
- 블루투스 통신에 대한 기본 이해
- Gradle 빌드 시스템 이해

---

## 개발 환경 설정

### 1. 저장소 클론

```bash
git clone https://github.com/crazyupinc/Android-JL_Bluetooth.git
cd Android-JL_Bluetooth
```

### 2. Android Studio에서 프로젝트 열기

1. Android Studio를 실행합니다
2. `File` → `Open` 메뉴를 선택합니다
3. 클론한 디렉토리에서 `code/PiHome_V1.12.0_SDK_V4.1.0` 폴더를 선택합니다
4. `OK` 버튼을 클릭하여 프로젝트를 엽니다

### 3. Gradle 동기화

프로젝트를 처음 열면 Android Studio가 자동으로 Gradle 동기화를 시작합니다. 만약 자동으로 시작되지 않으면:

1. 상단 메뉴에서 `File` → `Sync Project with Gradle Files`를 클릭합니다
2. 동기화가 완료될 때까지 기다립니다 (처음에는 시간이 걸릴 수 있습니다)

### 4. SDK 및 의존성 설치

프로젝트는 다음과 같은 주요 의존성을 사용합니다:

- **AndroidX 라이브러리**: 최신 Android 지원 라이브러리
- **JieLi Core Libraries**: `libs/` 폴더의 AAR 파일들
  - `jl_bluetooth_rcsp_V4.1.0_40116-release.aar`: 블루투스 RCSP 통신 라이브러리
  - `jl_bt_ota_V1.10.0_10932-release.aar`: OTA 업데이트 라이브러리
  - `jldecryption_v0.4-release.aar`: 암호화 라이브러리
  - 기타 오디오 및 이미지 변환 라이브러리
- **OkHttp/Retrofit**: 네트워크 통신
- **Glide**: 이미지 로딩
- **Room**: 로컬 데이터베이스

필요한 SDK 버전:
- **compileSdk**: 36
- **minSdk**: 21
- **targetSdk**: 34

---

## 프로젝트 구조

```
Android-JL_Bluetooth/
├── README.md                          # 프로젝트 기본 설명 (중국어)
├── ONBOARDING.md                      # 이 온보딩 문서 (한국어)
├── apk/                               # 빌드된 APK 파일
│   ├── btsmart-V1.12.0-xxx.apk       # 테스트용 앱
│   └── UpdateContent.txt              # 업데이트 내용
├── code/                              # 소스 코드
│   └── PiHome_V1.12.0_SDK_V4.1.0/    # 메인 프로젝트
│       ├── btsmart/                   # 앱 모듈
│       │   ├── src/                   # 소스 코드
│       │   │   └── main/
│       │   │       ├── java/          # Java 소스 파일
│       │   │       │   └── com/jieli/btsmart/
│       │   │       ├── res/           # 리소스 (레이아웃, 이미지 등)
│       │   │       └── AndroidManifest.xml
│       │   ├── libs/                  # 로컬 라이브러리 (AAR)
│       │   ├── docs/                  # 개발 문서 (중국어)
│       │   └── build.gradle           # 모듈 빌드 설정
│       ├── build.gradle               # 프로젝트 레벨 빌드 설정
│       ├── settings.gradle            # Gradle 설정
│       └── gradle/                    # Gradle wrapper
├── doc/                               # 문서
│   ├── 杰理之家SDK(Android)开发文档.url  # 온라인 개발 문서 링크
│   ├── 杰理OTA外接库(Android)开발문档.url # OTA 개발 문서 링크
│   ├── 杰理开放平台接入说明문档.pdf       # 플랫폼 접속 가이드
│   └── 杰理之家APP用户手册V1.2.pdf      # 사용자 매뉴얼
└── libs/                              # 핵심 SDK 라이브러리
    ├── jl_bluetooth_rcsp_Vxxx.aar    # 블루투스 통신 라이브러리
    ├── jl_bt_ota_Vxxx.aar            # OTA 업데이트 라이브러리
    ├── jldecryption_vxxx.aar         # 암호화 라이브러리
    └── ReadMe.txt                     # 라이브러리 변경 로그
```

### 주요 패키지 구조

```
com.jieli.btsmart/
├── ui/                    # UI 관련 코드 (Activity, Fragment)
├── viewmodel/             # ViewModel (MVVM 패턴)
├── data/                  # 데이터 모델 및 저장소
├── util/                  # 유틸리티 클래스
├── tool/                  # 도구 클래스
└── MainApplication.java   # 애플리케이션 클래스
```

---

## 빌드 및 실행

### 1. 빌드 Variants 선택

프로젝트는 두 가지 제품 flavor를 지원합니다:

- **btsmart**: 기본 버전 (com.jieli.btsmart)
- **pilink**: 대체 버전 (com.jieli.pilink)

Android Studio 좌측 하단의 `Build Variants` 탭에서 원하는 variant를 선택합니다:
- `btsmartDebug`: 개발용 디버그 빌드
- `btsmartRelease`: 릴리스 빌드

### 2. 프로젝트 빌드

#### Gradle을 통한 빌드 (명령줄)

```bash
# Debug 빌드
cd code/PiHome_V1.12.0_SDK_V4.1.0
./gradlew assembleBtsmartDebug

# Release 빌드
./gradlew assembleBtsmartRelease

# 모든 variant 빌드
./gradlew build
```

#### Android Studio에서 빌드

1. 메뉴: `Build` → `Make Project` (또는 Ctrl+F9)
2. 빌드가 완료될 때까지 대기
3. 빌드 성공 시 하단에 "BUILD SUCCESSFUL" 메시지 표시

### 3. 앱 실행

#### USB를 통한 실행

1. Android 기기를 USB로 연결
2. 기기에서 개발자 옵션 및 USB 디버깅 활성화
3. Android Studio 상단의 실행 버튼(▶️) 클릭
4. 연결된 기기를 선택하고 OK 클릭

#### 에뮬레이터에서 실행

⚠️ **참고**: BLE 기능은 실제 기기에서만 완전히 동작합니다. 에뮬레이터에서는 제한적인 테스트만 가능합니다.

1. AVD Manager에서 에뮬레이터 생성
2. 에뮬레이터 시작
3. 실행 버튼으로 앱 배포

### 4. APK 파일 위치

빌드된 APK는 다음 위치에 생성됩니다:

```
code/PiHome_V1.12.0_SDK_V4.1.0/btsmart/build/outputs/apk/
├── btsmart/
│   ├── debug/
│   │   └── btsmart-V1.x.x-yyyyMMddHHmm-xxxxx-debug.apk
│   └── release/
│       └── btsmart-V1.x.x-yyyyMMddHHmm-xxxxx-release.apk
```

---

## 개발 가이드

### SDK 초기화

앱 시작 시 `MainApplication.java` 또는 초기 Activity에서 SDK를 초기화해야 합니다:

```java
// BluetoothOption 설정
BluetoothOption bluetoothOption = new BluetoothOption();
bluetoothOption.setPriority(BluetoothOption.PREFER_BLE);  // BLE 우선 사용
bluetoothOption.setReconnect(true);                       // 자동 재연결 활성화
bluetoothOption.setBleIntervalMs(500);                    // BLE 연결 간격 (ms)
bluetoothOption.setTimeoutMs(3000);                       // 명령 타임아웃 (ms)
bluetoothOption.setMtu(512);                              // MTU 크기

// 필터 데이터 설정 (펌웨어와 협의 필요)
byte[] scanData = "JLAISDK".getBytes();
bluetoothOption.setScanFilterData(new String(scanData));

// SDK 초기화
JL_BluetoothManager.getInstance(context).configure(bluetoothOption);
```

### 권한 처리

Android 6.0 이상에서는 런타임 권한 요청이 필요합니다:

```java
// 필요한 권한
String[] permissions = {
    Manifest.permission.BLUETOOTH,
    Manifest.permission.BLUETOOTH_ADMIN,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE
};

// Android 12 이상
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    permissions = new String[]{
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.ACCESS_FINE_LOCATION,
        // ... 기타 권한
    };
}

// 권한 요청
ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
```

### 블루투스 기기 스캔

```java
// 스캔 시작
JL_BluetoothManager.getInstance(context).startScan();

// 스캔 콜백 등록
JL_BluetoothManager.getInstance(context).registerBluetoothCallback(callback);
```

### 기기 연결

```java
// BluetoothDevice 객체를 사용하여 연결
JL_BluetoothManager.getInstance(context).connect(bluetoothDevice);
```

### 주요 API 사용 예제

자세한 API 문서는 다음을 참조하세요:
- `code/PiHome_V1.12.0_SDK_V4.1.0/btsmart/docs/杰理蓝牙控制库SDK开发说明.md`
- [온라인 개발 문서](https://doc.zh-jieli.com/Apps/Android/jielihome/zh-cn/master/index.html)

---

## 테스트

### 수동 테스트

1. **기기 연결 테스트**
   - 앱 실행 후 블루투스 활성화
   - 기기 스캔 및 연결 테스트
   - 연결 상태 확인

2. **기능 테스트**
   - 음악 재생/일시정지
   - 음량 조절
   - EQ 설정 변경
   - 파일 브라우징
   - OTA 업데이트 (펌웨어 준비 필요)

3. **호환성 테스트**
   - 다양한 Android 버전에서 테스트
   - 다른 제조사의 기기에서 테스트

### 단위 테스트

단위 테스트는 `src/test/` 디렉토리에 작성합니다:

```bash
# 테스트 실행
./gradlew test
```

### 계측 테스트 (Instrumented Tests)

실제 기기나 에뮬레이터에서 실행되는 테스트:

```bash
# 계측 테스트 실행
./gradlew connectedAndroidTest
```

---

## 문제 해결

### 일반적인 문제

#### 1. Gradle 동기화 실패

**문제**: "Failed to sync Gradle" 에러

**해결방법**:
- 인터넷 연결 확인
- Android Studio 재시작
- `File` → `Invalidate Caches / Restart` 실행
- `gradle/wrapper/gradle-wrapper.properties`의 Gradle 버전 확인

#### 2. 블루투스 연결 실패

**문제**: 기기를 찾을 수 없거나 연결되지 않음

**해결방법**:
- 블루투스 및 위치 권한이 허용되었는지 확인
- 기기의 블루투스가 활성화되어 있는지 확인
- 위치 서비스(GPS)가 켜져 있는지 확인 (BLE 스캔에 필요)
- 기기가 RCSP 프로토콜을 지원하는지 확인
- 펌웨어 버전이 SDK와 호환되는지 확인

#### 3. 빌드 에러: "Duplicate class found"

**문제**: 중복 클래스 에러

**해결방법**:
- `build.gradle`에서 의존성 충돌 확인
- `./gradlew dependencies`로 의존성 트리 확인
- 충돌하는 라이브러리의 버전 통일

#### 4. 권한 거부 문제

**문제**: 앱이 필요한 권한을 받지 못함

**해결방법**:
- `AndroidManifest.xml`에 모든 필요한 권한이 선언되어 있는지 확인
- 런타임 권한 요청 코드가 올바른지 확인
- Android 12 이상의 경우 새로운 블루투스 권한 추가 확인

#### 5. AAR 라이브러리를 찾을 수 없음

**문제**: "Cannot resolve symbol" 또는 라이브러리 import 실패

**해결방법**:
- `libs/` 폴더에 모든 AAR 파일이 있는지 확인
- `build.gradle`의 dependencies에 올바르게 선언되어 있는지 확인:
  ```gradle
  implementation fileTree(include: ['*.aar'], dir: 'libs')
  ```
- Gradle 동기화 재실행

### 로그 확인

문제 진단을 위해 Logcat을 사용하세요:

```bash
# Android Studio의 Logcat 창에서 필터링
# Tag: JL_Bluetooth, RCSP, OTA 등
```

---

## 추가 리소스

### 공식 문서

1. **온라인 개발 문서**
   - [杰理之家SDK 개발 문서](https://doc.zh-jieli.com/Apps/Android/jielihome/zh-cn/master/index.html)
   - SDK의 전체 API 레퍼런스 및 가이드 (중국어)

2. **로컬 문서**
   - `code/PiHome_V1.12.0_SDK_V4.1.0/btsmart/docs/杰理蓝牙控制库SDK开发说明.md`: SDK 개발 가이드
   - `doc/杰理之家APP用户手册V1.2.pdf`: 사용자 매뉴얼
   - `doc/杰理开放平台接入说明文档.pdf`: 플랫폼 접속 가이드

### 버전 정보

현재 SDK 버전: **4.1.0** (2025/07/18)

최신 변경사항:
- 701N 및 707N 컬러 스크린 지원 추가
- 화면 밝기 제어
- 화면 보호 프로그램 제어
- 날씨 동기화
- 메시지 동기화

전체 버전 히스토리는 `README.md` 파일의 "三、版本说明" 섹션을 참조하세요.

### 라이브러리 정보

핵심 라이브러리 (`libs/` 폴더):
- `jl_bluetooth_rcsp_V4.1.0_40116-release.aar`: 블루투스 RCSP 통신
- `jl_bt_ota_V1.10.0_10932-release.aar`: OTA 펌웨어 업데이트
- `jldecryption_v0.4-release.aar`: 암호화/복호화
- `BmpConvert_V1.6.0_10604-release.aar`: BMP 이미지 변환
- `GifConvert_V1.3.0_42-release.aar`: GIF 이미지 변환
- `jl_audio_V1.3.0_10301-release.aar`: 오디오 처리
- `jl_eq_V1.1.0_10101-release.aar`: 이퀄라이저 처리

⚠️ **참고**: 모든 라이브러리는 16KB 페이지를 지원합니다.

### 커뮤니티 및 지원

- **GitHub Issues**: 버그 리포트 및 기능 요청
- **JieLi 개발자 포럼**: 추가 지원 및 커뮤니티

### Android 개발 리소스

- [Android Developers 공식 문서](https://developer.android.com/docs)
- [Android Bluetooth 가이드](https://developer.android.com/guide/topics/connectivity/bluetooth)
- [Android BLE 가이드](https://developer.android.com/guide/topics/connectivity/bluetooth/ble-overview)

---

## 다음 단계

온보딩을 완료한 후에는:

1. ✅ 샘플 앱을 실행하고 기본 기능 탐색
2. ✅ 공식 개발 문서 읽기 (특히 API 레퍼런스)
3. ✅ 간단한 기능부터 수정하거나 추가하기 시작
4. ✅ 코드베이스의 다른 부분 탐색 및 이해
5. ✅ 팀원들과 협업하며 프로젝트 기여

---

## 연락처 및 기여

프로젝트 관련 질문이나 문제가 있으면:
- GitHub Issues를 통해 버그 리포트 제출
- Pull Request를 통한 코드 기여

---

**마지막 업데이트**: 2025년 11월

이 온보딩 가이드가 프로젝트 시작에 도움이 되기를 바랍니다! 🚀
