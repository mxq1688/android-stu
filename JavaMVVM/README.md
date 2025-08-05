# Java MVVM Android 项目

这是一个使用 **Java + 传统View系统 + MVVM + Jetpack组件** 的Android项目示例。

## 架构特点

- **编程语言**: Java
- **UI框架**: 传统View系统 (XML + ViewBinding + DataBinding)
- **架构模式**: MVVM (Model-View-ViewModel)
- **数据库**: Room
- **异步处理**: LiveData
- **依赖注入**: 手动依赖注入

## 项目结构

```
app/src/main/java/
├── data/
│   ├── model/          # 数据模型
│   ├── database/       # Room数据库
│   └── repository/     # 数据仓库
└── ui/
    ├── viewmodel/      # ViewModel层
    ├── adapter/        # RecyclerView适配器
    └── MainActivity    # 主界面
```

## 主要依赖

- AndroidX AppCompat
- Material Design Components
- Room Database
- ViewModel & LiveData
- ViewBinding & DataBinding
- Retrofit (网络请求)

## 运行说明

1. 用Android Studio打开此项目
2. 等待Gradle同步完成
3. 连接Android设备或启动模拟器
4. 点击运行按钮

## 功能演示

- 用户列表展示
- 添加/编辑/删除用户
- 本地数据库存储
- MVVM数据绑定
- 响应式UI更新 