# GTNH Modify （万宁GTNH / WanNing Fork）

[English](./README.md) | 中文

面向 **GT: New Horizons** 1.7.10 整合包的配方修改 Mod。

本仓库 fork 自 [wohaopa/GTNHModify](https://github.com/wohaopa/GTNHModify)。
上游仓库停留在提交
[`a0b4ac1`](https://github.com/wohaopa/GTNHModify/commit/a0b4ac14ad8c9f84b370ea4295545d096b78cf85)
之后不再更新——原作者因个人原因停止了开发。本 fork 从该提交继续维护：
修复构建与依赖、让 Mixin 适配新版 GT5-Unofficial，并新增功能。

## 这个 fork 做了什么

- 构建迁移到 RetroFuturaGradle / Gradle 9，所有 GTNH 依赖锁定为 GTNH Maven 仓库中仍存在的版本
  （GT5-Unofficial `5.09.50.60`）。
- Mixin 目标适配当前 GT5-Unofficial 的 `MTE*` 类名。
- **新增**：配方输出概率控制（保底概率 + 倍率）。
- **新增**：按机器电压等级提升输出概率。
- 配置系统重做：支持多参数、`double` 数值，配置键带前缀，避免与其它 Mod 撞名。

## 功能开关

所有功能都是 `config/GTNHModify.cfg` 中的独立开关。

### GregTech 配方

| 开关 | 效果 |
| --- | --- |
| `GT_Recipe OneTick` | 所有 GT 配方耗时 1 tick |
| `GT_Recipe Tenths` | 配方耗时除以 `_setting` 值（默认 10） |
| `GT_Recipe EnergyLess` | 配方不消耗能量 |
| `GT_Recipe Input_1` | 输入数量改为 1 |
| `GT_Recipe Input_64` | 输出数量改为 64 |
| `GT_Recipe DynamicDuration` | 按现实时间计算耗时倍率 |
| `Fusion’s power on energy` | 聚变配方只消耗 1 EU |

### 输出概率（本 fork 新增）

- `GT_Recipe OutputChance` —— 静态概率修改总开关。
  - `OutputChance.Chance` —— 保底输出概率，万分比（`10000` = 100%）。
  - `OutputChance.ChanceRate` —— 概率倍率。
- `GT_Recipe VoltageChance` —— 机器电压等级每高于配方所需一级，物品输出概率 ×`1.05`
  （最高 100%）。单方块（`MTEBasicMachine`）与多方块（`ParallelHelper`）均生效。

### 其它

| 开关 | 效果 |
| --- | --- |
| `Other GGNaquadahReactor` | 燃烧时间 ×10 |
| `Other EyeOfHarmonyRecipeTweaker` | 调和之眼配方增强 |

### 机器加速 Handler

| 开关 | 作用对象 |
| --- | --- |
| `Handle Minecraft` | 原版熔炉 |
| `Handle GregTech` | 扫描器、矿机、工业熔炉、钻井 |
| `Handle Thaumcraft` | 炼金炉、灵气节点 |
| `Handle Botania` | 花格扩散器 |

## 配置

配置位于 `config/GTNHModify.cfg`（客户端和服务端各有一份）。

- 每个功能是一个布尔开关，键名带游戏内分类前缀，例如 `B:"GT_Recipe OneTick"=false`。
- 单参数功能会额外生成 `<开关名>_setting`，例如 `I:"GT_Recipe Tenths_setting"=10`。
- 多参数功能使用 `<枚举名>.<参数名>`，例如 `S:OutputChance.Chance=0.0`。
- 配置在启动时读取，**改完必须重启游戏/服务端**；重载世界无效，客户端 NEI 也可能缓存配方显示。

## 构建

```bash
./gradlew build
```

产物在 `build/libs/`。分发时使用**不带** `-dev`、`-sources` 后缀的 jar；
`-dev` 包只能在 RFG 开发环境中加载。

开发时启动客户端请用：

```bash
./gradlew runClient
```

## 安装

1. 把发布版 jar 放进 GTNH 实例的 `mods/` 目录。
2. 客户端和服务端**都要安装**——配方修改与 Mixin 注入发生在服务端逻辑，且两端 FML
   模组列表需一致。
3. GTNH 整合包自带 GTNHMixins 与 GTNHLib，无需额外安装。
4. 本 Mod 针对 GT5-Unofficial `5.09.50.60` 编译，Mixin 目标与该版本绑定，
   使用其它 GT5U 版本可能导致注入失败。

## 致谢与许可

- 原项目：[wohaopa/GTNHModify](https://github.com/wohaopa/GTNHModify)，作者 初夏同学（wohaopa）。
- 本 fork 由 [LiuHongXuMaritime](https://github.com/LiuHongXuMaritime) 维护。
- 采用 MIT 许可证，详见 [LICENSE](./LICENSE)；保留原作者版权信息（Copyright (c) 2024 初夏同学）。
