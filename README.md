# 简介
- 使用纯 Kotlin 和 Androidx 搭建。
- 上手特别容易，不会 MVVM 开发模式的都可以快速上手。
- 基于 Android Architecture Components(AAC)。

# 简单、浅封装、扩展性强
- 代码简单，没有过度的封装，上手非常容易，Lib 只有十几个类。
- 除了 Android 原生的依赖以外，只集成总线 `live-event-bus`，页面状态管理 `loadsir`，状态栏沉浸式工具 `immersionbar`，并且都是可以插拔的，不需要替换即可。
- 图片加载库，网络请求库等其他第三方 Lib 可根据个人兴趣自己选择集成即可。
  
# 快速开发

### 减少重复代码
不需要写
```
setContentView(R.layout.activity_main)
MainViewModel viewModel = ViewModelProviders.of(this).get(MainVm.class);
```
只需要在Activity、Fragment加上泛型既可
```
public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {}
```

###  页面状态管理非常容易
日常开发 Activity 的页面状态有：有网、无网络、空页面、loading加载页面等各种页面。

只需要在 ViewModel 中一行代码，Activity 会自动监测，然后展示不同的样式。

- Loading 状态
```
stateActionEvent.postValue(LoadState)
```
- 无网络状态
```
stateActionEvent.postValue(ErrorState("没有网络"))
```
-  数据为空
```
stateActionEvent.postValue(EmptyState)
```
- 还内置了占位样式、网络超时样式，并且自定义样式很容易。

### Activity 和 Fragment 共享 ViewModel
当 Activity 和 Fragment 通讯时，共用一个 ViewModel 是一种非常简单有效的方式。

只需要在 Fragment 类上面,加个注解既可搞定：

```
@FragmentConfiguration(shareViewModel = true)
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding(R.layout.fragment_main) 
```

### Activity、Fragment 不需要 ViewMoel 时

有时会有超级简单的页面，不需要使用MVVM设计模式时，内置了一个 `EmptyViewModel`：

```
class SecondActivity : BaseActivity<EmptyViewModel, ActivitySecondBinding>() 
```

### Application 级别的 ViewModel

内置应用级别的 ViewModel，不单独属于任何一个 Activity 或者 Fragment，可以页面之间的通讯和全局的配置观管理。

```
val shareViewModel = getAppViewModelProvider().get(ShareViewModel::class.java)
```

### 内置一些好用的 Kotlin 扩展函数

具体见 `UtilsKtx.kt`

# 具体使用

详细见 Demo.

# END
不断完善中...

有些idea来自网络学习，非常感谢大神们的开源奉献精神。

大家有什么更好的建议请提出，一起学习进步。

既然来了，麻烦动动手指，点个star，非常感谢。

# License

Copyright 2019 Wutao

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
