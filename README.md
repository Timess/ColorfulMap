# ColorfulMap

### [开源地址](https://ssl.lunadeer.cn:14446/zhangyuheng/ColorfulMap) | [文档地址](https://ssl.lunadeer.cn:14448/doc/26/)
### [下载页面](https://ssl.lunadeer.cn:14446/zhangyuheng/ColorfulMap/releases)
### [统计页面](https://bstats.org/plugin/bukkit/ColorfulMap/21443) | [Hangar](https://hangar.papermc.io/zhangyuheng/ColorfulMap)

## 简介

![](https://ssl.lunadeer.cn:14437/i/2024/02/21/65d5e0d10b7d5.png)

用于将图片转换为地图画，悬挂在展示框上增添装饰。

## 说明

本插件大约相当于 [ImageFrame](https://github.com/LOOHP/ImageFrame) 的简版以及 [ImageMaps](https://github.com/SydMontague/ImageMaps) 的高版本重制版。前者功能丰富，不过可能由于项目体量较大，对于新版本的兼容较慢，后者在 1.18 开始就停止了更新，切不支持 Folia 核心。

## 功能介绍

- 将图片转换为地图画；
- 图片缩放；
- 自动放置；

## 支持版本

- 1.20.1+ (Paper、Folia)

## 安装方法

1. 将插件放入服务器的 `plugins` 目录下
2. 重启服务器
3. 在 `plugins/ColorfulMap/config.yml` 中配置
4. 重启服务器

## 玩家使用方法

1.  首先需要将你想要转换的图片上传到 [图床](https://ssl.lunadeer.cn:14437/)  ，便于本插件从网络读取图片内容。上传完成后会得到一个图片的网络地址，复制此地址。

2. 在游戏中输入指令：`/tomap <图片地址>` 即可获得一张地图。地图的左上角会显示这张地图画所需的展示框阵列的尺寸，如下图所示 5x4 代表你需要在墙上准备一组长5格，宽4格的展示框阵列才能摆的下这张地图画。

![](https://ssl.lunadeer.cn:14437/i/2024/02/21/65d5ef7e8d676.png)

3. 对着展示框阵列的**左下角展示框**摆放此地图，则会自动在墙上的剩余展示框内放置对应的地图。

4. 如果图片太大或太小可以尝试在指令后加入缩放倍率，例如 `/tomap <图片地址> 0.5`表示将以原图的50%大小渲染。如果你希望将图片填满所有地图边缘处没有留白，那么你需要保证你的图片的长宽分辨率均为**128的倍数**，因为在MC中一张地图的分辨率为128x128。

## 管理员指南

## 指令

>  以下指令中尖括号 `<>` 表示必填，方括号 `[]` 表示选填。

### 玩家指令


| 功能    | 指令                     |
|-------|------------------------|
| 生成地图画 | `/tomap <图片地址> [缩放倍率]` |



### 管理员指令

## 配置文件参考

```yaml
MaxFrameX: 32

MaxFrameY: 18

CheckUpdate: true

Debug: false
```

## TODO

## 建议与反馈

Mail: [zhangyuheng@lunadeer.cn](mailto:zhangyuheng@lunadeer.cn)

QQ: 2751268851
