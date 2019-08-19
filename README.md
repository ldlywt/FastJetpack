# FastAAC
一个简单、快速的Android Architecture Components 的MVVM开发框架。

项目使用 Androidx 搭建，只使用官方部分的 MVVM 和 databind 的全部特性，个人觉得怎么简单怎么用。

## Lib 结构
   ![image](https://github.com/ldlywt/FastAAC/raw/master/img/structure.png)

## 优点

- 简单
 
    代码简单，没有过度的封装，上手非常容易，Lib 只有十几个类。
    
    除了 Android 原生的依赖以外，
    只集成了一个工具库 `utilcodex`，事件总线 `live-event-bus`，页面状态管理 `loadsir`，
    状态栏沉浸式工具 `immersionbar`，并且都是可以插拔的，不需要替换即可。
    
    图片加载库，网络请求库等可根据个人兴趣自己选择集成即可。
    

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
    
    数据为空：
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



