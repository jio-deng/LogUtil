# LogUtil
一个肥肠实用的log工具类

## v1.0 功能 (2018/06/11 更新)
### 1.可以控制log的输出以及输出等级（可配置）
### 2.可以控制log输出后是否保存于手机本地；默认保存五天以内的log，过期自动删除（可配置）
### 3.log写入文件过程使用队列保存，通过HandlerThread循环读取

## 使用方式
### 初始化
#### 1.在Application中调用init后可进行配置（默认无需配置）
#### 2.在需要输出log的地方直接调用LogUtil.xxx(Context context, String msg)
