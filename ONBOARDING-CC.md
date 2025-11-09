# JieLi Bluetooth SDK 온보딩 가이드

> 안드로이드 블루투스 스피커/이어폰 제어를 위한 종합 개발 가이드

## 목차

1. [프로젝트 소개](#1-프로젝트-소개)
2. [빠른 시작](#2-빠른-시작)
3. [개발 환경 설정](#3-개발-환경-설정)
4. [프로젝트 구조 이해](#4-프로젝트-구조-이해)
5. [핵심 기능](#5-핵심-기능)
6. [아키텍처 개요](#6-아키텍처-개요)
7. [SDK 통합 가이드](#7-sdk-통합-가이드)
8. [주요 사용 예제](#8-주요-사용-예제)
9. [문제 해결](#9-문제-해결)
10. [추가 리소스](#10-추가-리소스)

---

## 1. 프로젝트 소개

### 1.1 개요

**JieLi Bluetooth SDK**는 주하이 제리 과학기술 주식회사(珠海市杰理科技股份有限公司)에서 개발한 안드로이드용 블루투스 오디오 장치 제어 SDK입니다.

### 1.2 주요 특징

- **지원 장치**: 블루투스 스피커, 이어폰, TWS 이어폰, 보청기
- **지원 칩셋**: AC707N, JL701N, AC697N, AC696N, AC695N, AC693N
- **최신 버전**: SDK V4.1.0 / APP V1.12.0 (2025/07/18)
- **핵심 프로토콜**: RCSP (Remote Control Service Protocol)

### 1.3 시스템 요구사항

| 항목 | 요구사항 | 비고 |
|------|----------|------|
| 최소 Android 버전 | Android 5.1 (API 21) | BLE 기능 필수 |
| 타겟 Android 버전 | Android 34 (API 34) | - |
| 컴파일 SDK | Android 36 (API 36) | - |
| 개발 도구 | Android Studio | 최신 버전 권장 |
| 빌드 시스템 | Gradle 8.10.0+ | - |
| Java 버전 | Java 8+ | - |

---

## 2. 빠른 시작

### 2.1 프로젝트 클론

```bash
git clone https://github.com/crazyupinc/Android-JL_Bluetooth.git
cd Android-JL_Bluetooth
```

### 2.2 프로젝트 구조 확인

```
Android-JL_Bluetooth/
├── apk/                    # 테스트용 APK 파일
├── code/                   # 데모 앱 소스코드
│   └── PiHome_V1.12.0_SDK_V4.1.0/
│       ├── btsmart/       # 메인 앱 모듈
│       └── build.gradle   # 빌드 설정
├── doc/                    # 개발 문서
├── libs/                   # SDK 라이브러리 (AAR)
└── README.md              # 프로젝트 개요
```

### 2.3 데모 앱 실행

1. Android Studio에서 `code/PiHome_V1.12.0_SDK_V4.1.0` 폴더 열기
2. Gradle 동기화 대기
3. 실행 구성에서 `btsmart` 모듈 선택
4. 디바이스 연결 또는 에뮬레이터 실행
5. Run 버튼 클릭

### 2.4 APK 직접 설치

```bash
# APK 디렉토리로 이동
cd apk/

# ADB를 통한 설치
adb install [apk파일명].apk
```

---

## 3. 개발 환경 설정

### 3.1 필수 구성 요소

#### Android Studio 설정

1. **Android Studio 설치**
   - [공식 사이트](https://developer.android.com/studio)에서 최신 버전 다운로드
   - Android SDK 34 설치
   - Android SDK Build-Tools 설치

2. **JDK 설정**
   - JDK 8 이상 설치
   - Android Studio에서 JDK 경로 설정

3. **SDK Manager 설정**
   - Android SDK Platform 21, 34 설치
   - Android SDK Build-Tools 최신 버전
   - Android SDK Platform-Tools
   - Android SDK Tools

### 3.2 Gradle 설정

프로젝트의 `gradle.properties`에 다음 설정 추가:

```properties
org.gradle.jvmargs=-Xmx2048m
android.useAndroidX=true
android.enableJetifier=true
```

### 3.3 권한 설정

앱에서 필요한 주요 권한:

```xml
<!-- 블루투스 관련 권한 -->
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" />
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
<uses-permission android:name="android.permission.BLUETOOTH_ADVERTISE" />

<!-- 위치 권한 (BLE 스캔에 필요) -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

<!-- 저장소 권한 -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

---

## 4. 프로젝트 구조 이해

### 4.1 핵심 SDK 라이브러리 (`libs/`)

| 라이브러리 | 버전 | 용도 |
|------------|------|------|
| **jl_bluetooth_rcsp** | V4.1.0 | 핵심 블루투스 RCSP 프로토콜 |
| **jl_bt_ota** | V1.10.0 | 펌웨어 OTA 업데이트 |
| **jl_eq** | V1.1.0 | 오디오 이퀄라이저 제어 |
| **jl_audio** | V1.3.0 | 오디오 재생 관리 |
| **jl_dialog** | V1.3.0 | 커스텀 다이얼로그 컴포넌트 |
| **jl_component_lib** | V1.4.0 | 재사용 가능한 컴포넌트 |
| **jl_http** | V1.1.0 | HTTP 클라이언트 |
| **BmpConvert** | V1.6.0 | BMP 이미지 변환 |
| **GifConvert** | V1.3.0 | GIF 이미지 변환 |

### 4.2 데모 앱 구조 (`code/btsmart/`)

```
btsmart/src/main/java/com/jieli/btsmart/
├── constant/              # 상수 정의
├── data/                  # 데이터 레이어
│   ├── model/            # 데이터 모델
│   │   ├── alarm/        # 알람 관련 모델
│   │   ├── bluetooth/    # 블루투스 기기 정보
│   │   ├── device/       # 기기 관리 모델
│   │   ├── eq/           # 이퀄라이저 설정
│   │   ├── music/        # 음악 재생 모델
│   │   └── ota/          # OTA 업데이트 모델
│   ├── adapter/          # RecyclerView 어댑터
│   └── listeners/        # 데이터 리스너 인터페이스
├── tool/                  # 비즈니스 로직 및 유틸리티
│   ├── bluetooth/        # 블루투스 작업
│   │   ├── rcsp/        # RCSP 프로토콜 구현
│   │   ├── BluetoothHelper.java
│   │   └── BTEventCallbackManager.java
│   ├── room/             # SQLite 데이터베이스 (Room)
│   │   ├── AppDatabase.java
│   │   ├── dao/         # Data Access Objects
│   │   └── entity/      # 데이터베이스 엔티티
│   └── ...
├── ui/                    # UI 레이어
│   ├── home/             # 홈 화면
│   ├── device/           # 기기 목록/상세
│   ├── music/            # 음악 플레이어
│   ├── eq/               # 이퀄라이저 UI
│   ├── alarm/            # 알람 설정
│   ├── settings/         # 설정
│   └── ota/              # OTA 업데이트 UI
├── viewmodel/            # MVVM ViewModel
└── MainApplication.java  # 앱 진입점
```

---

## 5. 핵심 기능

### 5.1 기기 관리

#### 기기 검색 및 연결
- **BLE 기기 스캔**: 주변 JieLi 블루투스 기기 검색
- **페어링**: 자동/수동 페어링 지원
- **다중 기기 지원**: 최대 5개 기기 동시 연결
- **재연결**: 자동 재연결 기능

#### 기기 정보 관리
- 배터리 상태 모니터링 (본체 + 충전 케이스)
- 기기 이름 설정
- 펌웨어 버전 확인
- 신호 강도 모니터링

### 5.2 오디오 제어

#### 음악 재생
- **로컬 음악**: 기기 내 음악 라이브러리 재생
- **네트워크 라디오**: 온라인 라디오 스트리밍
- **기기 음악**: 블루투스 기기에 저장된 음악 재생
- **재생 제어**: 재생/일시정지/다음곡/이전곡

#### 이퀄라이저
- 사전 설정 프리셋 (Rock, Pop, Jazz 등)
- 커스텀 EQ 설정
- Bass/Treble 조정
- 실시간 적용

### 5.3 펌웨어 업데이트 (OTA)

- **무선 업데이트**: BLE를 통한 펌웨어 업데이트
- **진행 상황 추적**: 실시간 업데이트 진행률
- **롤백 지원**: 업데이트 실패 시 복구
- **자동 검사**: 새 펌웨어 자동 확인

### 5.4 알람 관리

- 다중 알람 생성/편집
- 스누즈 기능
- 알람 알림 처리
- 기기 동기화

### 5.5 LED/조명 제어

- 밝기 조절
- 색상 설정
- 조명 모드 선택
- 커스텀 애니메이션

### 5.6 특수 기능

#### TWS (True Wireless Stereo)
- TWS 이어폰 페어링
- 일대이(一拖二) 기능 - 하나의 이어폰으로 두 기기 연결

#### 컬러 스크린 지원 (701N/707N)
- 화면 밝기 제어
- 화면 보호기 관리
- 날씨 동기화
- 메시지 동기화

#### 보청기 기능
- 청력 검사 및 보정
- 맞춤형 음향 설정

---

## 6. 아키텍처 개요

### 6.1 MVVM 아키텍처 패턴

```
┌─────────────────────────────────────────┐
│         UI Layer (View)                 │
│  Activities, Fragments                  │
│  - HomeActivity                         │
│  - MusicFragment                        │
│  - SettingsFragment                     │
└────────────┬────────────────────────────┘
             │ Observes LiveData
┌────────────▼────────────────────────────┐
│      ViewModel Layer                    │
│  - HomeVM                               │
│  - NetRadioViewModel                    │
│  - MultiMediaViewModel                  │
└────────────┬────────────────────────────┘
             │ Uses
┌────────────▼────────────────────────────┐
│         Data Layer (Model)              │
│  - RCSPController (Bluetooth)           │
│  - BTEventCallbackManager               │
│  - Room Database                        │
│  - Data Models                          │
└────────────┬────────────────────────────┘
             │ Uses
┌────────────▼────────────────────────────┐
│         SDK Layer                       │
│  - jl_bluetooth_rcsp                    │
│  - jl_bt_ota                            │
│  - jl_eq, jl_audio                      │
└─────────────────────────────────────────┘
```

### 6.2 주요 컴포넌트

#### MainApplication
```java
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // SDK 초기화
        // 데이터베이스 설정
        // 블루투스 매니저 초기화
        // 로깅 및 크래시 리포팅 설정
    }
}
```

#### 블루투스 통신 레이어
- **BluetoothHelper**: 블루투스 작업용 Facade (구버전, 호환성 유지)
- **RCSPController**: 최신 RCSP 프로토콜 구현
- **BTEventCallbackManager**: 블루투스 이벤트 중앙 관리
- **BTEventCallback**: 블루투스 이벤트 리스너 인터페이스

#### 데이터베이스 레이어 (Room ORM)
```java
@Database(
    entities = {
        FMCollectInfo.class,
        NetRadioInfo.class,
        UserInfo.class,
        FittingRecord.class
    },
    version = 4
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FMCollectInfoDao fmCollectInfoDao();
    public abstract NetRadioInfoDao netRadioInfoDao();
    public abstract UserDao userDao();
    public abstract FittingRecordDao fittingRecordDao();
}
```

---

## 7. SDK 통합 가이드

### 7.1 새 프로젝트에 SDK 통합하기

#### Step 1: AAR 라이브러리 추가

1. `libs/` 디렉토리의 모든 AAR 파일을 프로젝트의 `app/libs/` 폴더에 복사

2. `app/build.gradle`에 의존성 추가:

```gradle
dependencies {
    // JieLi SDK 라이브러리
    implementation fileTree(dir: 'libs', include: ['*.jar', '*.aar'])

    // 또는 개별 지정
    implementation files('libs/jl_bluetooth_rcsp-V4.1.0-release.aar')
    implementation files('libs/jl_bt_ota-V1.10.0-release.aar')
    implementation files('libs/jl_eq-V1.1.0-release.aar')
    // ... 기타 라이브러리

    // 필수 AndroidX 라이브러리
    implementation 'androidx.appcompat:appcompat:1.7.1'
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.9.1'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.1'
    implementation 'androidx.room:room-runtime:2.7.2'
    annotationProcessor 'androidx.room:room-compiler:2.7.2'

    // 네트워킹
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.okhttp3:okhttp:4.10.0'
    implementation 'com.google.code.gson:gson:2.11.0'
}
```

#### Step 2: AndroidManifest.xml 설정

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- 권한 선언 -->
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
    <uses-permission android:name="android.permission.BLUETOOTH_SCAN" />
    <uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

    <!-- BLE 기능 필수 -->
    <uses-feature android:name="android.hardware.bluetooth_le"
                  android:required="true" />

    <application
        android:name=".MainApplication"
        ...>
        <!-- Activities 등록 -->
    </application>
</manifest>
```

#### Step 3: Application 클래스 초기화

```java
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // SDK 초기화
        initBluetoothSDK();

        // 데이터베이스 초기화
        initDatabase();
    }

    private void initBluetoothSDK() {
        // RCSPController 설정
        RCSPController.getInstance().init(this);

        // 이벤트 콜백 등록
        BTEventCallbackManager.getInstance()
            .addCallback(btEventCallback);
    }

    private void initDatabase() {
        AppDatabase.getInstance(this);
    }

    private final BTEventCallback btEventCallback = new BTEventCallback() {
        @Override
        public void onConnection(BluetoothDevice device, int status) {
            // 연결 상태 변경 처리
        }

        @Override
        public void onDeviceInfoUpdate(DeviceInfo info) {
            // 기기 정보 업데이트 처리
        }

        // 기타 이벤트 콜백 구현
    };
}
```

### 7.2 ProGuard 규칙

`proguard-rules.pro`에 다음 규칙 추가:

```proguard
# JieLi SDK
-keep class com.jieli.** { *; }
-dontwarn com.jieli.**

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# OkHttp
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

# Gson
-keepattributes Signature
-keep class com.google.gson.** { *; }
```

---

## 8. 주요 사용 예제

### 8.1 기기 스캔 및 연결

```java
public class DeviceScanActivity extends AppCompatActivity {

    private RCSPController rcspController;
    private List<ScanResult> scanResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        rcspController = RCSPController.getInstance();

        // 권한 확인
        checkPermissions();
    }

    private void startScan() {
        // 스캔 시작
        rcspController.startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                // 스캔 결과 처리
                if (!scanResults.contains(result)) {
                    scanResults.add(result);
                    updateDeviceList();
                }
            }

            @Override
            public void onScanFailed(int errorCode) {
                Toast.makeText(DeviceScanActivity.this,
                    "스캔 실패: " + errorCode,
                    Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void connectDevice(BluetoothDevice device) {
        // 기기 연결
        rcspController.connect(device, new ConnectionCallback() {
            @Override
            public void onConnected(BluetoothDevice device) {
                Toast.makeText(DeviceScanActivity.this,
                    "연결 성공",
                    Toast.LENGTH_SHORT).show();

                // 메인 화면으로 이동
                startActivity(new Intent(
                    DeviceScanActivity.this,
                    MainActivity.class
                ));
            }

            @Override
            public void onDisconnected(BluetoothDevice device) {
                Toast.makeText(DeviceScanActivity.this,
                    "연결 해제됨",
                    Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int errorCode, String message) {
                Toast.makeText(DeviceScanActivity.this,
                    "연결 실패: " + message,
                    Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkPermissions() {
        // 블루투스 및 위치 권한 확인
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12 이상
            requestPermissions(new String[]{
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_CODE_PERMISSIONS);
        } else {
            // Android 11 이하
            requestPermissions(new String[]{
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_CODE_PERMISSIONS);
        }
    }
}
```

### 8.2 음악 재생 제어

```java
public class MusicControlViewModel extends ViewModel {

    private RCSPController rcspController;
    private MutableLiveData<MusicStatusInfo> musicStatus =
        new MutableLiveData<>();

    public MusicControlViewModel() {
        rcspController = RCSPController.getInstance();

        // 음악 상태 업데이트 리스너 등록
        BTEventCallbackManager.getInstance()
            .addCallback(new BTEventCallback() {
                @Override
                public void onMusicStatusUpdate(MusicStatusInfo info) {
                    musicStatus.postValue(info);
                }
            });
    }

    public void playOrPause() {
        rcspController.sendCommand(
            RCSPCommand.MUSIC_PLAY_PAUSE,
            null,
            new CommandCallback() {
                @Override
                public void onSuccess() {
                    Log.d("Music", "재생/일시정지 성공");
                }

                @Override
                public void onError(int code, String msg) {
                    Log.e("Music", "명령 실패: " + msg);
                }
            }
        );
    }

    public void nextTrack() {
        rcspController.sendCommand(
            RCSPCommand.MUSIC_NEXT,
            null,
            null
        );
    }

    public void previousTrack() {
        rcspController.sendCommand(
            RCSPCommand.MUSIC_PREVIOUS,
            null,
            null
        );
    }

    public void setVolume(int volume) {
        // 볼륨 범위: 0-100
        VolumeInfo volumeInfo = new VolumeInfo();
        volumeInfo.setVolume(volume);

        rcspController.sendCommand(
            RCSPCommand.SET_VOLUME,
            volumeInfo,
            null
        );
    }

    public LiveData<MusicStatusInfo> getMusicStatus() {
        return musicStatus;
    }
}
```

### 8.3 이퀄라이저 설정

```java
public class EqualizerFragment extends Fragment {

    private RCSPController rcspController;
    private EqInfo currentEqInfo;

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcspController = RCSPController.getInstance();

        // 현재 EQ 설정 가져오기
        getCurrentEqSettings();

        // EQ 프리셋 버튼 설정
        setupEqPresets();
    }

    private void getCurrentEqSettings() {
        rcspController.sendCommand(
            RCSPCommand.GET_EQ_INFO,
            null,
            new CommandCallback<EqInfo>() {
                @Override
                public void onSuccess(EqInfo eqInfo) {
                    currentEqInfo = eqInfo;
                    updateEqUI(eqInfo);
                }

                @Override
                public void onError(int code, String msg) {
                    Toast.makeText(getContext(),
                        "EQ 정보 로드 실패",
                        Toast.LENGTH_SHORT).show();
                }
            }
        );
    }

    private void applyEqPreset(EqPreset preset) {
        EqInfo eqInfo = new EqInfo();
        eqInfo.setPreset(preset);

        rcspController.sendCommand(
            RCSPCommand.SET_EQ_PRESET,
            eqInfo,
            new CommandCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getContext(),
                        "EQ 프리셋 적용됨: " + preset.getName(),
                        Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(int code, String msg) {
                    Toast.makeText(getContext(),
                        "EQ 적용 실패",
                        Toast.LENGTH_SHORT).show();
                }
            }
        );
    }

    private void setupEqPresets() {
        // Rock 프리셋
        btnRock.setOnClickListener(v ->
            applyEqPreset(EqPreset.ROCK)
        );

        // Pop 프리셋
        btnPop.setOnClickListener(v ->
            applyEqPreset(EqPreset.POP)
        );

        // Jazz 프리셋
        btnJazz.setOnClickListener(v ->
            applyEqPreset(EqPreset.JAZZ)
        );

        // Classical 프리셋
        btnClassical.setOnClickListener(v ->
            applyEqPreset(EqPreset.CLASSICAL)
        );
    }
}
```

### 8.4 OTA 펌웨어 업데이트

```java
public class OtaUpdateActivity extends AppCompatActivity {

    private OTAManager otaManager;
    private ProgressBar progressBar;
    private TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ota);

        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tvProgress);

        otaManager = OTAManager.getInstance();

        // OTA 업데이트 확인
        checkForUpdate();
    }

    private void checkForUpdate() {
        otaManager.checkUpdate(new UpdateCheckCallback() {
            @Override
            public void onUpdateAvailable(FirmwareInfo firmwareInfo) {
                // 업데이트 가능
                showUpdateDialog(firmwareInfo);
            }

            @Override
            public void onNoUpdateAvailable() {
                Toast.makeText(OtaUpdateActivity.this,
                    "최신 버전입니다",
                    Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code, String msg) {
                Toast.makeText(OtaUpdateActivity.this,
                    "업데이트 확인 실패: " + msg,
                    Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startOtaUpdate(FirmwareInfo firmwareInfo) {
        otaManager.startUpdate(
            firmwareInfo,
            new OTAUpdateCallback() {
                @Override
                public void onProgress(int progress) {
                    // 진행률 업데이트 (0-100)
                    runOnUiThread(() -> {
                        progressBar.setProgress(progress);
                        tvProgress.setText(progress + "%");
                    });
                }

                @Override
                public void onSuccess() {
                    runOnUiThread(() -> {
                        Toast.makeText(OtaUpdateActivity.this,
                            "업데이트 성공! 기기가 재시작됩니다.",
                            Toast.LENGTH_LONG).show();
                        finish();
                    });
                }

                @Override
                public void onError(int code, String msg) {
                    runOnUiThread(() -> {
                        Toast.makeText(OtaUpdateActivity.this,
                            "업데이트 실패: " + msg,
                            Toast.LENGTH_LONG).show();
                        progressBar.setProgress(0);
                    });
                }

                @Override
                public void onCancelled() {
                    runOnUiThread(() -> {
                        Toast.makeText(OtaUpdateActivity.this,
                            "업데이트가 취소되었습니다",
                            Toast.LENGTH_SHORT).show();
                    });
                }
            }
        );
    }

    private void showUpdateDialog(FirmwareInfo firmwareInfo) {
        new AlertDialog.Builder(this)
            .setTitle("펌웨어 업데이트")
            .setMessage("새로운 펌웨어 버전이 있습니다.\n\n" +
                "현재 버전: " + firmwareInfo.getCurrentVersion() + "\n" +
                "최신 버전: " + firmwareInfo.getLatestVersion() + "\n\n" +
                "업데이트하시겠습니까?")
            .setPositiveButton("업데이트", (dialog, which) ->
                startOtaUpdate(firmwareInfo))
            .setNegativeButton("취소", null)
            .show();
    }
}
```

### 8.5 배터리 상태 모니터링

```java
public class BatteryMonitorViewModel extends ViewModel {

    private MutableLiveData<BatteryInfo> batteryInfo =
        new MutableLiveData<>();
    private RCSPController rcspController;

    public BatteryMonitorViewModel() {
        rcspController = RCSPController.getInstance();

        // 배터리 상태 업데이트 리스너
        BTEventCallbackManager.getInstance()
            .addCallback(new BTEventCallback() {
                @Override
                public void onBatteryUpdate(BatteryInfo info) {
                    batteryInfo.postValue(info);
                }
            });

        // 주기적으로 배터리 정보 요청
        startBatteryMonitoring();
    }

    private void startBatteryMonitoring() {
        // 30초마다 배터리 정보 갱신
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable batteryCheckRunnable = new Runnable() {
            @Override
            public void run() {
                requestBatteryInfo();
                handler.postDelayed(this, 30000); // 30초
            }
        };
        handler.post(batteryCheckRunnable);
    }

    private void requestBatteryInfo() {
        rcspController.sendCommand(
            RCSPCommand.GET_BATTERY_INFO,
            null,
            new CommandCallback<BatteryInfo>() {
                @Override
                public void onSuccess(BatteryInfo info) {
                    batteryInfo.postValue(info);
                }

                @Override
                public void onError(int code, String msg) {
                    Log.e("Battery", "배터리 정보 요청 실패: " + msg);
                }
            }
        );
    }

    public LiveData<BatteryInfo> getBatteryInfo() {
        return batteryInfo;
    }

    public String getBatteryStatusText(BatteryInfo info) {
        if (info == null) return "알 수 없음";

        StringBuilder sb = new StringBuilder();

        // 본체 배터리
        sb.append("본체: ").append(info.getMainBattery()).append("%");

        // 충전 케이스 배터리 (있는 경우)
        if (info.hasCaseBattery()) {
            sb.append("\n케이스: ")
              .append(info.getCaseBattery())
              .append("%");
        }

        // 충전 상태
        if (info.isCharging()) {
            sb.append(" (충전 중)");
        }

        return sb.toString();
    }
}
```

---

## 9. 문제 해결

### 9.1 일반적인 문제

#### Q1: 기기 스캔이 안 됩니다

**A:** 다음 사항을 확인하세요:

1. **권한 확인**
   ```java
   // Android 12 이상
   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
       if (ContextCompat.checkSelfPermission(this,
           Manifest.permission.BLUETOOTH_SCAN)
           != PackageManager.PERMISSION_GRANTED) {
           // 권한 요청 필요
       }
   }
   ```

2. **위치 서비스 활성화**
   - BLE 스캔은 위치 서비스가 켜져 있어야 합니다
   - 설정 > 위치 > 켜기

3. **블루투스 활성화**
   ```java
   BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
   if (adapter == null || !adapter.isEnabled()) {
       // 블루투스 활성화 요청
       Intent enableBtIntent =
           new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
       startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
   }
   ```

#### Q2: 기기 연결이 자주 끊깁니다

**A:**

1. **신호 강도 확인**: 기기와 스마트폰 거리가 너무 멀지 않은지 확인
2. **배터리 최적화 해제**:
   - 설정 > 배터리 > 앱별 배터리 최적화 > 앱 선택 > 최적화 안 함
3. **백그라운드 제한 해제**:
   - 설정 > 앱 > 앱 선택 > 배터리 > 백그라운드 제한 없음

#### Q3: OTA 업데이트가 실패합니다

**A:**

1. **배터리 충분한지 확인**: 최소 30% 이상
2. **네트워크 연결 확인**: 펌웨어 다운로드 필요
3. **저장 공간 확인**: 최소 100MB 이상 여유 공간
4. **기기 재시작 후 재시도**

#### Q4: 음악 재생이 안 됩니다

**A:**

1. **블루투스 오디오 프로필 확인**: A2DP 프로필 연결 확인
2. **볼륨 확인**: 기기 및 스마트폰 볼륨 모두 확인
3. **다른 앱과 충돌**: 다른 음악 앱 종료 후 재시도

### 9.2 디버깅 팁

#### 로그 활성화

```java
// MainApplication.java
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 디버그 모드 활성화
        if (BuildConfig.DEBUG) {
            RCSPController.getInstance().setDebugMode(true);
            RCSPController.getInstance().setLogLevel(Log.VERBOSE);
        }
    }
}
```

#### 이벤트 로깅

```java
BTEventCallbackManager.getInstance().addCallback(
    new BTEventCallback() {
        @Override
        public void onConnection(BluetoothDevice device, int status) {
            Log.d("BT_EVENT",
                "Connection: " + device.getName() +
                ", Status: " + status);
        }

        @Override
        public void onDeviceInfoUpdate(DeviceInfo info) {
            Log.d("BT_EVENT",
                "Device Info: " + info.toString());
        }

        // 모든 이벤트 로깅
    }
);
```

#### ADB 로그 필터링

```bash
# JieLi SDK 로그만 보기
adb logcat -s JL_BT JL_RCSP JL_OTA

# 특정 태그 필터링
adb logcat | grep -E "BluetoothHelper|RCSPController"
```

### 9.3 성능 최적화

#### 1. 배터리 효율성

```java
// 스캔 주기 조절
ScanSettings scanSettings = new ScanSettings.Builder()
    .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER) // 저전력 모드
    .setReportDelay(1000) // 배치 스캔
    .build();
```

#### 2. 메모리 최적화

```java
// 사용하지 않는 리스너 제거
@Override
protected void onDestroy() {
    super.onDestroy();
    BTEventCallbackManager.getInstance()
        .removeCallback(btEventCallback);
}
```

#### 3. 연결 안정성

```java
// 재연결 로직
private void reconnectWithRetry(BluetoothDevice device, int maxRetries) {
    int retryCount = 0;
    while (retryCount < maxRetries) {
        try {
            rcspController.connect(device, connectionCallback);
            break;
        } catch (Exception e) {
            retryCount++;
            if (retryCount >= maxRetries) {
                Log.e("BT", "재연결 실패: " + e.getMessage());
            } else {
                // 재시도 전 대기
                Thread.sleep(1000 * retryCount);
            }
        }
    }
}
```

---

## 10. 추가 리소스

### 10.1 공식 문서

- **JieLi Home SDK 개발 문서**: [https://doc.zh-jieli.com/Apps/Android/jielihome/zh-cn/master/index.html](https://doc.zh-jieli.com/Apps/Android/jielihome/zh-cn/master/index.html)
- **JieLi OTA 외부 라이브러리 문서**: `doc/` 디렉토리 참조
- **JieLi 개방 플랫폼 접속 설명**: `doc/杰理开放平台接入说明文档.pdf`

### 10.2 참조 자료

#### Android 블루투스 개발
- [Android Bluetooth 가이드](https://developer.android.com/guide/topics/connectivity/bluetooth)
- [Android BLE 가이드](https://developer.android.com/guide/topics/connectivity/bluetooth/ble-overview)

#### MVVM 아키텍처
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- [ViewModel 가이드](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [LiveData 가이드](https://developer.android.com/topic/libraries/architecture/livedata)

#### Room Database
- [Room Persistence Library](https://developer.android.com/training/data-storage/room)

### 10.3 버전 히스토리

| 버전 | 날짜 | 주요 변경사항 |
|------|------|---------------|
| **4.1.0** | 2025/07/18 | - 701N/707N 컬러 스크린 지원<br>- 화면 밝기 제어<br>- 화면 보호기 제어<br>- 날씨/메시지 동기화 |
| **4.0.0** | 2025/04/15 | - 블루투스 및 RCSP 기능 분리<br>- SDK 로그 출력 최적화<br>- 파일 탐색 기능 개선<br>- Android 14 호환성 |
| **3.2.0** | 2023/11/23 | - TWS 이어폰 일대이 기능<br>- 버그 수정 |
| **3.0.8** | 2022/08/12 | - 보청기 검증 기능 추가 |
| **3.0.7** | 2022/07/20 | - 목걸이형 이어폰 UI 지원 |

### 10.4 지원 및 문의

#### GitHub Issues
- 이슈 제보: [GitHub Issues](https://github.com/crazyupinc/Android-JL_Bluetooth/issues)
- Pull Request: 기여를 환영합니다!

#### 프로젝트 구조
```
프로젝트 루트/
├── apk/           # 테스트 APK
├── code/          # 소스 코드
├── doc/           # 문서
├── libs/          # SDK 라이브러리
└── README.md      # 프로젝트 개요
```

### 10.5 체크리스트

개발을 시작하기 전에 다음 사항을 확인하세요:

- [ ] Android Studio 최신 버전 설치
- [ ] JDK 8 이상 설치
- [ ] Android SDK 21, 34 설치
- [ ] 프로젝트 클론 및 Gradle 동기화
- [ ] 데모 앱 빌드 및 실행 성공
- [ ] 블루투스 권한 이해
- [ ] RCSP 프로토콜 개념 이해
- [ ] MVVM 아키텍처 패턴 이해
- [ ] 데모 앱 코드 탐색 완료

---

## 마치며

이 온보딩 가이드를 통해 JieLi Bluetooth SDK를 성공적으로 시작할 수 있기를 바랍니다.

**Happy Coding!** 🎉

---

**문서 버전**: 1.0.0
**최종 업데이트**: 2025년 11월 9일
**작성자**: Claude (AI Assistant)
**SDK 버전**: V4.1.0
**앱 버전**: V1.12.0
