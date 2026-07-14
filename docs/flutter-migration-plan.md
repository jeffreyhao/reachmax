# ReachMax 纯 Flutter 化迁移方案

> 状态：方案待执行 · 编制日期：2026-07-14
> 目标：将现有「原生 Android（Java 为主）+ Flutter Add-to-App」工程改造为纯 Flutter 工程，支持 Android / iOS / macOS / Windows，未来可扩展 Linux。
> 用户已确认：**放弃全部灰产/马甲包能力**（defenceLib 保活、ActivityKiller、vest/monkey 开关、OAID 等）。

---

## 一、结论先行

**推荐方案：Greenfield（推倒重来）**——新建独立纯 Flutter 工程，按模块逐个重写，旧 Android 工程并行维护到切换日。

理由：

1. 用户已确认**放弃全部灰产/马甲包能力**，这正好砍掉了原生层最重、最难迁移、最阻碍 iOS/桌面适配的部分——推倒重来的最大顾虑消失。
2. 现存原生代码 ~14 万行 Java + 113 个 XML，但**大量是基础设施（base_net/base_util/commonlibrary）和小说阅读遗留（BookInfo 等 9 张表与广告投放业务无关）**，真正需要"翻译"的业务逻辑规模可控。
3. iOS 不存在、桌面要从零，Add-to-App 渐进式对这两个目标几乎无意义，反而受制于原生壳。
4. 现有 Flutter 仅 2 个文件、259 行，没有历史包袱值得保留。

> 不推荐"渐进式 Add-to-App 替换"：灰产逻辑删除后原生壳将空心化，维护双技术栈成本高于收益；且 iOS/桌面无法走这条路。

---

## 二、现状快照

| 维度 | 现状 |
|---|---|
| 架构 | 原生 Android（MVP，Java 为主）+ Flutter Add-to-App 模块 |
| 代码规模 | **~1196 个源文件 / ~14 万行**（app 233 + layer_base 963） |
| Flutter 现状 | 仅 2 个 Dart 文件、259 行（`mine_info_page`），通过 MethodChannel 混排 |
| iOS | **不存在**（仅有 flutter_module 自带的 `.ios` 壳） |
| 业务 | 广告投放管理（adv 一系 Fragment）+ 任务中心 + 登录 + 个人中心 |
| 原生依赖 | Retrofit、DBFlow、Glide、EventBus、神策埋点、JSBridge、Lottie、ImmersionBar、OAID… |
| 原生特化 | defenceLib 保活、vest/monkey 开关、马甲包逻辑、ActivityKiller |
| API 接口 | 67 个（APIService.java 等 3 个文件） |
| 资源 | 769 张 drawable/mipmap 图片 |
| 数据库 | DBFlow 9 张表（多数为小说阅读遗留） |
| 构建 | flavor `reachmax`（applicationId `com.xcyh.putting`）+ buildTypes `publish`/`develop`/`release` |

---

## 三、目标与范围

| 项 | 内容 |
|---|---|
| 平台 | Android ✅ / iOS ✅ / macOS ✅ / Windows ✅（Linux 暂不做，预留） |
| 保留业务 | 登录、广告投放管理（adv 全系）、任务中心、个人中心 |
| 丢弃 | defenceLib 保活、ActivityKiller、vest/monkey 马甲、OAID、神策 AOP 插桩埋点（改手动）、DBFlow 中小说遗留表、JSBridge 的马甲用途 |
| 环境 | 测试环境（develop，`launch.novelsbd.com/stg_song/`）/ 正式环境（publish，`launch.novelsbd.com/`） |
| 应用 ID | 沿用 `com.xcyh.putting`（Android）/ 新建 iOS Bundle ID |

---

## 四、技术选型

### 核心框架

| 层 | 选型 | 理由 |
|---|---|---|
| Flutter | 最新 stable（3.22+） | 桌面已 stable |
| Dart | 3.x，空安全 | —— |
| 状态管理 | **Riverpod 2.x** | 编译期安全、可测试，优于 Provider；团队规模适中 |
| 路由 | **go_router** | 官方推荐，声明式，支持深链/Web |
| 网络请求 | **dio 5.x** | 拦截器生态丰富，对齐现有 OkHttp 拦截器模型 |
| 序列化 | **freezed + json_serializable** | 不可变模型、联合类型，替代 Java bean |
| 本地存储 | **Hive CE / drift**（视复杂度） | 替代 DBFlow；ReachMax 本地表少，优先 Hive，若关系复杂用 drift |
| 依赖注入 | Riverpod 自带 + `flutter_riverpod` | 不引入额外 DI 框架 |
| 日志 | `logger` + 条件输出 | 对齐现有 LOG_DEBUG 开关 |

### UI / 工具

| 用途 | 选型 | 替代原 |
|---|---|---|
| 列表 | Flutter 内置 ListView/Sliver | RecyclerView |
| 下拉刷新 | `easy_refresh` | swiperefreshlayout |
| 图片加载 | `cached_network_image` | Glide |
| 动画 | Lottie（`lottie` 包，若用到） | Lottie |
| 沉浸式状态栏 | `flutter_statusbarcolor` / 系统层 | ImmersionBar |
| 时间/日期选择 | `syncfusion_flutter_datepicker` 或自研 | CalendarPicker/DateTimePicker |
| Flexbox 布局 | `flex` / `Wrap` | FlexboxLayout |
| 工具类 | 自研 + `collection`/`intl` | utilcodex |
| 权限 | `permission_handler` | easypermissions |

### 原生能力插件（必要的 MethodChannel/EventChannel）

- 推送：目前无需求，预留
- 埋点：用神策 Flutter SDK（`sensors_analytics_flutter_plugin`）或自研轻量埋点；**AOP 自动埋点放弃**，改为页面/事件手动埋点
- WebView：`webview_flutter`，若仍需 JS 互调用 `flutter_inappwebview`
- 应用版本/包名/签名：`package_info_plus` + `device_info_plus`
- 文件/路径：`path_provider`

### 桌面适配

- 窗口管理：`window_manager`（macOS/Windows 统一）
- 响应式布局：`LayoutBuilder` + 断点（手机 < 600 / 平板 < 1024 / 桌面）
- 输入方式：鼠标 hover、键盘快捷键、菜单栏（`platform_menus` 或 `macos_ui` / `fluent_ui`）

---

## 五、架构设计

### 分层（对齐原 layer_base 思想，但 Dart 化）

```
lib/
├── main.dart                    # 入口、环境初始化、ProviderScope
├── app/                         # 应用壳：路由、主题、全局配置
│   ├── router/                  # go_router 路由表
│   ├── theme/                   # 主题、颜色、字体
│   └── config/                  # 环境(flavor)配置：develop/publish
├── core/                        # 基础设施(对应 base_net/base_util)
│   ├── network/                 # dio 封装：ApiClient、拦截器、错误处理
│   ├── storage/                 # Hive/SharedPreferences 封装
│   ├── error/                   # AppException、ApiErrorCode
│   ├── util/                    # 工具类
│   └── tracking/                # 埋点封装(平台无关)
├── data/                        # 数据层
│   ├── model/                   # freezed 数据模型(对应 common_bean)
│   ├── repository/              # Repository(对应 model/manager + request)
│   ├── datasource/              # remote_api(对应 ApiService)/local
│   └── api/                     # 接口定义(对应 APIService.java 的 67 个方法)
├── features/                    # 业务功能(按页面分)
│   ├── login/                   # 登录
│   ├── adv/                     # 广告投放(最重要、页面最多)
│   │   ├── home/
│   │   ├── list/                # 含 account/group/plan/serial 多 tab
│   │   ├── detail/
│   │   ├── budget/
│   │   ├── timer/
│   │   ├── search/
│   │   └── date/
│   ├── task/                    # 任务中心
│   └── mine/                    # 个人中心
├── shared/                      # 跨功能复用(对应 commonlibrary)
│   ├── widgets/                 # 通用组件
│   └── providers/               # 全局 Provider
└── l10n/                        # 国际化(可选)
```

### 状态管理范式

- **页面状态**：`@riverpod` 注解生成 Notifier，持有 UiState（freezed）
- **数据流**：UI → Action → Repository → ApiClient → dio
- **全局单例**（登录态、用户信息、环境配置）：根 Provider

---

## 六、关键技术映射表

| 现有（Java/Android） | 迁移后（Dart/Flutter） | 迁移难度 |
|---|---|---|
| Retrofit + 67 接口方法 | dio + repository，逐个手写 | 🟡 中（量大但机械） |
| BaseRequest(334行) 请求队列/生命周期 | dio 拦截器 + CancelToken | 🟢 低 |
| DBFlow 9 表 | 评估：多数小说遗留可弃；ReachMax 实际本地表少，用 Hive | 🟢 低 |
| Glide 图片 | cached_network_image | 🟢 低 |
| EventBus | Riverpod 事件流 / `StreamProvider` | 🟢 低 |
| 神策 AOP 插桩 | 手动埋点封装 | 🟡 中 |
| ViewBinding/DataBinding | Widget + 数据绑定（天然） | 🟢 低 |
| Fragment + ViewPager | TabBar/PageView | 🟢 低 |
| 自定义 View/Dialog | CustomPainter / Dialog widget | 🟡 中（量大） |
| CalendarPicker/DateTimePicker | 第三方日期包 | 🟢 低 |
| WebView + JSBridge | webview_flutter / inappwebview | 🟡 中（H5 侧需对齐） |
| Activity 生命周期保活 | **删除** | — |
| OAID | **删除**（或 device_info_plus 替代） | — |
| 马甲包逻辑 | **删除** | — |

---

## 七、分阶段实施计划

### 阶段 0：准备（1 周）

- [ ] 新建仓库 `reachmax_flutter`，配置 Flutter 工程 + 多平台目录
- [ ] 配置 `dev` / `prod` flavor（`--flavor` + dart-define）
- [ ] 接入 CI（可沿用 AWS S3 上传 aab 逻辑的 Dart 版）
- [ ] **安全清理**：轮换泄露在 `gradle.properties` 的 AWS AccessKey/SecretKey
- [ ] 搭建 core 骨架：网络、存储、错误、日志、主题、路由

### 阶段 1：基础设施 + 登录（1–2 周）

- [ ] ApiClient（dio）+ 拦截器（token、日志、错误、缓存）
- [ ] 登录页（参考现有 `LoginScreen.kt` Compose 版，UI 直接复刻思路）
- [ ] Token 持久化 + 全局登录态 Provider
- [ ] 主框架：MainTab（首页 / 任务 / 我的）

### 阶段 2：广告投放核心业务（3–5 周，最重）

按价值排序，分批迁移：

- [ ] AdvList（account/group/plan/serial 4 个 tab）+ 列表/分页/下拉刷新
- [ ] AdvDetail 详情页
- [ ] Home 首页 + HomePagerAdapter
- [ ] Budget 预算
- [ ] Timer 定时任务创建
- [ ] Search 搜索
- [ ] Date 日期选择（集成第三方包）

每批：模型(freezed) → repository → API → UI，先跑通主流程再补交互细节。

### 阶段 3：任务中心 + 个人中心（1–2 周）

- [ ] TaskCenter / TaskList
- [ ] Mine / MineInfo（参考现有 `mine_info_page.dart`，已有雏形）

### 阶段 4：原生能力 + 埋点（1–2 周）

- [ ] 神策 Flutter SDK 接入，补齐关键页面/事件埋点
- [ ] WebView（若 H5 页面仍需要）
- [ ] 权限处理、应用更新、版本信息

### 阶段 5：iOS 适配（2–3 周）

- [ ] 配置 iOS 工程、签名、Info.plist、权限文案
- [ ] 真机调试、网络 ATS 配置、键位/交互适配
- [ ] TestFlight → 送审

### 阶段 6：桌面端（2–3 周）

- [ ] window_manager 接入，统一窗口尺寸/标题栏
- [ ] 响应式布局断点（窄屏走手机式，宽屏走分栏/侧栏）
- [ ] macOS：`macos_ui` 风格、菜单栏、沙盒配置、签名公证
- [ ] Windows：`fluent_ui` 风格、MSIX 打包
- [ ] 桌面特有交互：鼠标 hover、右键菜单、键盘快捷键

### 阶段 7：回归测试 + 上线（2–3 周）

- [ ] 全功能回归（Android/iOS/macOS/Windows）
- [ ] 性能：启动速度、列表滚动、内存
- [ ] 旧工程灰度切流 → 下线

**合计：约 13–21 周（3–5 个月），1 名熟手 Flutter 开发。** 若 2 人并行可压到 2–3 个月。

---

## 八、工作量估算（细化）

| 工作项 | 量 | 单位工时 | 小计 |
|---|---|---|---|
| API 接口翻译 | 67 个 | 0.5 天/批(5个) | 7 天 |
| 数据模型(freezed) | ~按接口 1:1.5 | 0.3 天/批 | 4 天 |
| 业务页面 UI | ~35 个页面类 | 1–1.5 天/页 | 40–50 天 |
| 网络层封装 | base_net 移植 | 3 天 | 3 天 |
| 通用组件/工具 | commonlibrary 等价 | 5 天 | 5 天 |
| 埋点 | 手动补齐 | 3 天 | 3 天 |
| iOS 适配 | 从零 | 12 天 | 12 天 |
| 桌面适配 | macOS+Win | 15 天 | 15 天 |
| 测试与修 bug | —— | 12 天 | 12 天 |
| **合计** | | | **约 100–115 人天** |

---

## 九、风险与应对

| 风险 | 等级 | 应对 |
|---|---|---|
| **后端接口契约不清晰**（Java 反推耗时） | 🟡 中 | 阶段 1 先用现有 develop 包抓包确认，必要时索要接口文档 |
| **神策埋点口径不一致**（AOP→手动，可能漏埋） | 🟡 中 | 迁移前列「埋点清单」核对表，阶段 4 集中补齐 + 数据校验 |
| **WebView 内 H5 依赖 JSBridge 协议** | 🟡 中 | 早期确认哪些 H5 页面要用，用 inappwebview 对齐 `window.webkit` 协议 |
| **桌面端交互范式差异大**（手机 App 改桌面） | 🟡 中 | 阶段 6 单独评审布局方案，必要时产品介入设计 |
| **iOS 送审被拒**（原生壳残留习惯） | 🟢 低 | 全新工程，无历史问题；注意隐私清单(Privacy Manifest) |
| **双技术栈并行维护成本** | 🟡 中 | 旧工程进入「仅修 P0 bug」冻结期，新工程按里程碑替代 |
| **AWS Key 已泄露**（`gradle.properties`） | 🔴 高 | 阶段 0 立即轮换密钥，从 git 历史清除（`git filter-repo`）或接受历史已泄露、仅轮换 |

---

## 十、验收标准

- [ ] Android/iOS/macOS/Windows 四端可独立打包安装运行
- [ ] 登录、广告管理(增删改查)、任务、个人中心主流程全通
- [ ] 网络请求在 dev/prod 双环境正确切换
- [ ] 关键埋点数据上报校验通过（与旧版对比抽样一致）
- [ ] 启动速度不低于旧版 80%，列表滚动无明显卡顿
- [ ] iOS 通过 App Store 审核
- [ ] 旧 Android 工程可下线（灰度全量后）

---

## 十一、待确认事项（执行前需拍板）

1. **仓库策略**：新建独立仓库 `reachmax_flutter`，还是在本仓库内新建目录？（影响 CI/历史）
2. **iOS Bundle ID** 与签名/开发者账号信息。
3. **桌面端是「可运行即可」还是要做完整桌面级交互**？（决定阶段 6 投入）
4. **埋点方案**：续用神策，还是借机换方案？
5. **是否需要 Web 端**？（Flutter Web 同源，成本低，建议明确以免返工）

> 以上 5 项不影响本方案立项，可在阶段 0 期间陆续确认。

---

## 附：执行入口

如同意本方案，从「**阶段 0：工程骨架 + 网络/存储/路由/主题**」开始落地。
