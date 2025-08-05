# Kotlin Compose MVVM Android 项目

这是一个使用 **Kotlin + Jetpack Compose + MVVM + Jetpack组件 + Hilt** 的现代化Android项目示例。

## 架构特点

- **编程语言**: Kotlin
- **UI框架**: Jetpack Compose (声明式UI)
- **架构模式**: MVVM (Model-View-ViewModel)
- **依赖注入**: Hilt
- **数据库**: Room + Coroutines Flow
- **异步处理**: Coroutines + StateFlow
- **状态管理**: Compose State + ViewModel

## 技术栈

### 核心组件
- **Jetpack Compose** - 现代化声明式UI
- **Hilt** - 依赖注入框架
- **Room** - 本地数据库
- **ViewModel** - 状态管理
- **StateFlow/Flow** - 响应式数据流
- **Coroutines** - 异步编程

### 架构模式
- **Clean Architecture** - 分层架构
- **Repository Pattern** - 数据仓库模式
- **MVVM** - 数据绑定架构

## 项目结构

```
app/src/main/java/
├── data/
│   ├── local/          # Room数据库
│   └── repository/     # Repository实现
├── domain/
│   ├── model/          # 数据模型
│   └── repository/     # Repository接口
├── presentation/
│   ├── components/     # Compose组件
│   ├── screen/         # 页面
│   ├── theme/          # 主题样式
│   ├── viewmodel/      # ViewModel
│   └── MainActivity    # 主界面
└── di/                 # Hilt依赖注入模块
```

## 主要特性

### UI层 (Compose)
- **声明式UI** - 更简洁的UI构建方式
- **状态驱动** - UI自动响应状态变化
- **Material 3 Design** - 现代化设计语言
- **组件化开发** - 可复用的UI组件

### 数据层 (Repository + Room)
- **响应式数据流** - Flow数据流
- **本地数据持久化** - Room数据库
- **搜索功能** - 实时搜索过滤
- **CRUD操作** - 完整的数据操作

### 业务层 (ViewModel)
- **状态管理** - StateFlow状态管理
- **防抖搜索** - 300ms搜索防抖
- **错误处理** - 统一错误处理机制
- **加载状态** - Loading/Error/Success状态

## 运行说明

1. 用Android Studio打开此项目
2. 等待Gradle同步完成
3. 确保Kotlin版本兼容
4. 连接Android设备或启动模拟器
5. 点击运行按钮

## 功能演示

- ✅ 用户列表展示 (LazyColumn)
- ✅ 实时搜索过滤 (搜索防抖)
- ✅ 添加用户 (对话框)
- ✅ 编辑用户 (内联编辑)
- ✅ 删除用户 (一键删除)
- ✅ 在线状态显示 (动态指示器)
- ✅ 响应式UI (状态驱动)
- ✅ Material 3 设计 (现代化界面)

## 技术亮点

### Compose优势
```kotlin
@Composable
fun UserItem(user: User, onEdit: (User) -> Unit) {
    Card { /* 声明式UI代码 */ }
}
```

### StateFlow数据流
```kotlin
private val _uiState = MutableStateFlow(UserUiState())
val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()
```

### Hilt依赖注入
```kotlin
@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel()
```

这个项目展示了现代Android开发的最佳实践！ 