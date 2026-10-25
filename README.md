# GTNHModify (WanNing Fork)

English | [中文](./README_CN.md)

A recipe-focused tweak mod for the **GT: New Horizons** 1.7.10 modpack.

This repository is a fork of [wohaopa/GTNHModify](https://github.com/wohaopa/GTNHModify).
The upstream project has been inactive since commit
[`a0b4ac1`](https://github.com/wohaopa/GTNHModify/commit/a0b4ac14ad8c9f84b370ea4295545d096b78cf85) —
its author stopped development for personal reasons. This fork continues from that commit,
keeps the mod working with current GTNH versions and adds new tweaks.

## What this fork changes

- Build migrated to RetroFuturaGradle / Gradle 9, with every GTNH dependency pinned to a
  version that still exists in the GTNH Maven (GT5-Unofficial `5.09.50.60`).
- Mixins ported to the current `MTE*` class names of GT5-Unofficial.
- **New:** output chance control (minimum chance + multiplier).
- **New:** output chance bonus based on the machine's voltage tier.
- Config reworked: multi-parameter tweakers, `double` values, and prefixed keys so settings
  do not collide with other mods.

## Tweaks

Every tweak is an independent switch in `config/GTNHModify.cfg`.

### GregTech recipes

| Switch | Effect |
| --- | --- |
| `GT_Recipe OneTick` | every GT recipe takes 1 tick |
| `GT_Recipe Tenths` | recipe duration divided by the `_setting` value (default 10) |
| `GT_Recipe EnergyLess` | recipes require no energy |
| `GT_Recipe Input_1` | inputs reduced to 1 |
| `GT_Recipe Input_64` | outputs raised to 64 |
| `GT_Recipe DynamicDuration` | duration multiplier calculated from real time |
| `Fusion’s power on energy` | fusion recipes consume 1 EU |

### Output chance (added by this fork)

- `GT_Recipe OutputChance` — master switch for static chance tweaks.
  - `OutputChance.Chance` — minimum output chance in 1/10000 (`10000` = 100%).
  - `OutputChance.ChanceRate` — multiplier applied to the chance.
- `GT_Recipe VoltageChance` — for each voltage tier the machine is above the recipe's tier,
  the item output chance is multiplied by `1.05` (capped at 100%). Works for both single-block
  machines (`MTEBasicMachine`) and multiblocks (`ParallelHelper`).

### Other tweaks

| Switch | Effect |
| --- | --- |
| `Other GGNaquadahReactor` | burn time ×10 |
| `Other EyeOfHarmonyRecipeTweaker` | Eye of Harmony recipe enhancement |

### Machine speed handlers

| Switch | Target |
| --- | --- |
| `Handle Minecraft` | vanilla furnace |
| `Handle GregTech` | scanner, miner, multi furnace, driller |
| `Handle Thaumcraft` | alchemy furnace, node |
| `Handle Botania` | flower spreader |

## Configuration

Settings live in `config/GTNHModify.cfg` (client and server each keep their own copy).

- Each tweak is a boolean named after its in-game category, e.g. `B:"GT_Recipe OneTick"=false`.
- Tweakers that take a single value add `<switch>_setting`, e.g. `I:"GT_Recipe Tenths_setting"=10`.
- Multi-parameter tweakers use `<EnumName>.<param>`, e.g. `S:OutputChance.Chance=0.0`.
- The config is read at startup, so **restart the game/server after editing**. Reloading the
  world is not enough, and NEI may cache recipe displays on the client.

## Building

```bash
./gradlew build
```

The distributable jar is written to `build/libs/`. Use the jar **without** the `-dev` or
`-sources` suffix — the `-dev` jar only works inside the RFG development environment.

For development, run the client with:

```bash
./gradlew runClient
```

## Installing

1. Drop the release jar into the `mods/` folder of your GTNH instance.
2. Install it on **both client and server** — recipe changes and mixin injections happen on
   the server side, and the FML mod list must match on both ends.
3. GTNHMixins and GTNHLib come with the GTNH pack; no extra dependency is needed.
4. The mod is compiled against GT5-Unofficial `5.09.50.60`. Mixin targets are tied to that
   version, so a different GT5-Unofficial build may break injection.

## Credits & License

- Original project: [wohaopa/GTNHModify](https://github.com/wohaopa/GTNHModify) by 初夏同学 (wohaopa).
- This fork: maintained by [LiuHongXuMaritime](https://github.com/LiuHongXuMaritime).
- Licensed under the MIT License — see [LICENSE](./LICENSE). Original copyright
  (c) 2024 初夏同学 is preserved.
