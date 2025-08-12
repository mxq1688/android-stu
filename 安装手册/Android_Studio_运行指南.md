# Android Studio 运行指南

## 🚀 用 Android Studio 打开并运行项目

### 1. 打开项目

#### 方法一：直接打开
1. 启动 **Android Studio**
2. 选择 **"Open an Existing Project"** 或 **"Open"**
3. 导航到项目目录：`pods-sdk/android/`
4. 选择 `android` 文件夹并点击 **"Open"**

#### 方法二：从文件夹打开
1. 在 Finder/文件管理器中找到 `pods-sdk/android/` 目录
2. 右键点击 `android` 文件夹
3. 选择 **"Open with Android Studio"**

### 2. 项目同步

打开项目后，Android Studio 会自动：
- 🔄 **同步 Gradle** - 下载依赖并配置项目
- 📝 **索引文件** - 分析代码结构
- ⚙️ **配置构建环境**

**注意事项**：
- 首次打开可能需要几分钟时间
- 如果网络较慢，依赖下载可能较久
- 确保网络畅通

### 3. 解决可能的问题

#### ⚠️ 签名配置错误
如果看到关于 `D:\keystore\neviewtech.keystore` 的错误：

1. 打开 `app/build.gradle` 文件
2. 找到 `signingConfigs` 部分
3. 注释掉签名配置：
   ```gradle
   android {
       // signingConfigs {
       //     neview {
       //         storeFile file('D:\\keystore\\neviewtech.keystore')
       //         storePassword '147258'
       //         keyAlias 'androidentry'
       //         keyPassword '147258'
       //     }
       // }
   ```
4. 同时注释掉 Release 版本的签名引用：
   ```gradle
   buildTypes {
       release {
           minifyEnabled true
           proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
           // signingConfig signingConfigs.neview
       }
   }
   ```

#### 🔄 强制重新同步
如果同步失败：
1. 点击菜单 **File → Sync Project with Gradle Files**
2. 或点击工具栏的 🔄 同步按钮

### 4. 配置运行设备

#### 真机调试（推荐）
1. 用 USB 连接 Android 设备
2. 在设备上开启 **"开发者选项"** 和 **"USB 调试"**
3. 在 Android Studio 中检查设备：
   - 点击工具栏的设备选择器
   - 确认看到你的设备

#### 使用模拟器
1. 点击 **Tools → AVD Manager**
2. 创建新的虚拟设备（推荐 API 26+ 的设备）
3. 启动模拟器

### 5. 运行项目

#### 🎯 方法一：使用运行按钮
1. 确保项目已同步完成
2. 在设备选择器中选择目标设备
3. 点击绿色的 **▶️ Run** 按钮
4. 或使用快捷键 **⇧⌘R** (Mac) / **Shift+F10** (Windows)

#### 🎯 方法二：构建并安装
1. 点击 **Build → Build Bundle(s) / APK(s) → Build APK(s)**
2. 构建完成后，在 `app/build/outputs/apk/debug/` 找到 APK
3. 拖拽到设备或使用 ADB 安装

### 6. 调试功能

#### 📊 查看日志
1. 打开底部的 **"Logcat"** 窗口
2. 过滤标签：在搜索框输入 `NvEasy` 或 `Bluetooth`
3. 选择日志级别：Debug、Info、Warn、Error

#### 🐛 断点调试
1. 在代码行号左侧点击设置断点
2. 点击 **🐛 Debug** 按钮运行调试模式
3. 应用会在断点处暂停

#### 📱 布局检查器
1. 运行应用后，点击 **Tools → Layout Inspector**
2. 选择运行中的应用进程
3. 可以实时查看界面层次结构

### 7. 项目结构说明

```
android/
├── app/                          # 主应用模块
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/nveasy/bledemo/  # Java 源码
│   │   │   │   ├── MainActivity.java     # 主活动
│   │   │   │   └── ...
│   │   │   ├── res/                      # 资源文件
│   │   │   │   ├── layout/              # 布局文件
│   │   │   │   ├── values/              # 字符串、颜色等
│   │   │   │   └── ...
│   │   │   └── AndroidManifest.xml      # 应用清单
│   │   └── test/                         # 测试代码
│   ├── libs/                            # 本地库文件
│   │   ├── NvEasyBle-v1.1.2.aar        # 蓝牙 SDK
│   │   └── NvEasyAudio-v1.0.2.aar      # 音频 SDK
│   └── build.gradle                     # 模块构建脚本
├── build.gradle                         # 项目构建脚本
├── settings.gradle                      # 项目设置
└── gradle.properties                    # Gradle 属性
```

### 8. 快捷键参考

| 功能 | Mac | Windows/Linux |
|------|-----|---------------|
| 运行应用 | ⇧⌘R | Shift+F10 |
| 调试应用 | ⌃D | Shift+F9 |
| 构建项目 | ⌘F9 | Ctrl+F9 |
| 同步项目 | ⌘⇧O | Ctrl+Shift+O |
| 查找文件 | ⇧⌘O | Ctrl+Shift+N |
| 全局搜索 | ⇧⌘F | Ctrl+Shift+F |

### 9. 常见问题解决

#### 🔧 Gradle 同步失败
```bash
# 解决方案：清理并重建
1. Build → Clean Project
2. Build → Rebuild Project
```

#### 🔧 设备无法识别
```bash
# 检查 ADB 连接
1. 打开 Terminal（Android Studio 底部）
2. 运行：adb devices
3. 如果没有设备，检查 USB 调试设置
```

#### 🔧 应用安装失败
```bash
# 卸载旧版本再重试
1. 在设备上卸载旧版本应用
2. 或在 Terminal 中运行：adb uninstall com.nveasy.bledemo
3. 重新运行项目
```

### 10. 性能优化建议

#### 🚀 加速构建
1. **File → Settings → Build → Compiler**
   - 勾选 "Compile independent modules in parallel"
   - 设置合适的 heap size

2. **启用 Gradle 缓存**
   - 在 `gradle.properties` 中添加：
   ```properties
   org.gradle.caching=true
   org.gradle.parallel=true
   ```

#### 💾 节省存储空间
1. 定期清理构建缓存：**Build → Clean Project**
2. 删除不必要的 Gradle 缓存：**File → Invalidate Caches and Restart**

---

## 🎯 快速开始总结

1. **打开** Android Studio，选择 `pods-sdk/android/` 目录
2. **等待** Gradle 同步完成
3. **修复** 签名配置问题（如果有）
4. **连接** Android 设备并开启 USB 调试
5. **点击** 绿色运行按钮 ▶️
6. **查看** Logcat 日志进行调试

**应用功能**：这是一个蓝牙耳机演示应用，可以测试设备搜索、连接、录音、音频处理等功能。

---

💡 **提示**：如果遇到问题，可以查看 Android Studio 底部的 "Build" 窗口获取详细错误信息。