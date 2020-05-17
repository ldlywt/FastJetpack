# 分支
- Master 分支 使用Kotlin、ViewBinding、Jetpack框架
- ViewBinding 分支 使用 Java、ViewBinding、Jetpack框架
- DataBinding 分支 使用 Java、DataBinding、Jetpack框架

# FastAAC

- 上手特别容易，不会 MVVM 开发模式的都可以快速上手。
- 一个浅封装、快速开发的 Android MVVM 开发框架。
- 基于 Android Architecture Components(AAC)。
- 项目使用 Androidx 搭建。
- 只使用官方部分的 MVVM 和 databind 的部分特性。

## Lib 结构
   ![image](https://github.com/ldlywt/FastAAC/raw/master/img/structure.png)

## 优点

- 简单、浅封装、扩展性强
 
    代码简单，没有过度的封装，上手非常容易，Lib 只有十几个类。
    
    除了 Android 原生的依赖以外，
    只集成了一个工具库 `utilcodex`，事件总线 `live-event-bus`，页面状态管理 `loadsir`，
    状态栏沉浸式工具 `immersionbar`，并且都是可以插拔的，不需要替换即可。
    
    图片加载库，网络请求库等其他第三方 Lib 可根据个人兴趣自己选择集成即可。
    

- 快速开发

    通过泛型可以减少很多不必要的重复代码，以前每个 Activity 都需要写如下重复代码，例如:
    ```
    ActivityMainBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    
    MainVm mainVm = ViewModelProviders.of(this).get(MainVm.class);
    ```
    现在只需要在类名上面写上泛型即可，自动解析：
    ```
    public class MainActivity extends AbsMvvmActivity<MainVm, ActivityMainBinding> {}
    ```
    
- 自动页面状态管理

    日常开发 Activity 的页面状态有：有网、无网络、空页面、loading加载页面等各种页面。
    
    使用 LiveData 的监听集成在 BaseActivity 中，根据 BaseResult 返回的 code 来自动管理页面显示状态。
    ```
     switch (httpResult.getErrorCode()) {
                case BaseResult.SUCCESS_CODE:
                    if (httpResult.getData() != null) {
                        resultCode = SuccessCallback.class;
                    } else {
                        resultCode = EmptyCallback.class;
                    }
                    break;
                default:
                    resultCode = ErrorCallback.class;
            }
    ```
    
    如果想手动改变页面状态，只需在 ViewModel 中非常简单的设置，例如：
    
    无网络时：
    ```
    postPageState(new BaseResult(-1));
    ```
    
    数据为空时：
    ```
    public void clickNoData() {
        BaseResult<Object> baseResult = new BaseResult<>();
        baseResult.setErrorCode(0);
        baseResult.setData(null);
        postPageState(baseResult);
    }
    ```
 
## 具体使用
见 demo 中的 MainActivity。   

## END
大家有什么更好的建议请提出，一起学习进步。

既然来了，麻烦动动手指，点个star，非常感谢。
 
## License

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



