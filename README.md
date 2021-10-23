# 两种方式封装Retrofit+协程，实现优雅快速的网络请求

## 目的

- 简单调用、少写重复代码
- 不依赖第三方库（只含Retrofit+Okhttp+协程）

- 完全不懂协程也能立马上手（模板代码）
- 用Kotlin的方式写Kotlin代码，什么意思呢？对比一下下面2个代码就知道了：

```kotlin
mViewModel.wxArticleLiveData.observe(this, object : IStateObserver<List<WxArticleBean>>() {

    override fun onSuccess(data: List<WxArticleBean>?) {
    }

    override fun onError() {
    }
})
mViewModel.wxArticleLiveData.observeState(this) {

    onSuccess { data: List<WxArticleBean>? ->
    }

    onError {
    }
}
```

既然是用Kotlin了，就不要用Java的方式写接口回掉了，DSL表达式不香么？



提供两种方式实现：

- 方式一代码量更少，网络请求自带Loading，不需要手动调用Loading
- 方式二解耦更彻底



两种方式设计思路在解耦这一块存在差异，看具体需求，没有谁好谁差，依照自己的项目，哪个更方便用哪个。

基于官方架构的封装：

![img](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/b0258b753a58459599208f83093114a2~tplv-k3u1fbpfcp-zoom-1.image)

## 一、封装一

### Activity中的代码示例

#### 点击请求网络

```kotlin
mViewModel.getArticleData()
```

#### 设置监听，只监听成功的结果，使用默认异常处理

```kotlin
mViewModel.wxArticleLiveData.observeState(this) {
    onSuccess { data ->
        Log.i("wutao","网络请求的结果是：$data")
    }
}
```

#### 如果需要单独处理每一个回调

这些回调都是可选的，不需要可不实现

```kotlin
mViewModel.wxArticleLiveData.observeState(this) {
    onSuccess { data ->
        Log.i("wutao","网络请求的结果是：$data")
    }

    onEmpty{
        Log.i("wutao", "返回的数据是空，展示空布局")
    }

    onFailed {
        Log.i("wutao", "后台返回的errorCode: $it")
    }

    onException { e ->
        Log.i("wutao","这是非后台返回的异常回调")
    }

    onShowLoading {
         Log.i("wutao","自定义单个请求的Loading")
    }

    onComplete {
        Log.i("wutao","网络请求结束")
    }
}
```

#### 请求自带Loading

很多网络请求都需要Loading，不想每次都写`onShowLoading{}`方法，也so easy。

```kotlin
mViewModel.wxArticleLoadingLiveData.observeState(this, this) {
    onSuccess { data ->
		Log.i("wutao","网络请求的结果是：$data")
    }
}
```

`observeState()`第二个方法传入`ui`的引用就可，这样单个网络请求之前会自动加载Loading，成功或者失败自动取消Loading。



上面代码都是Activity中，我们来看下ViewModel中。

### ViewModel中代码示例

```kotlin
class MainViewModel{

    private val repository by lazy { WxArticleRepository() }

    val wxArticleLiveData = StateLiveData<List<WxArticleBean>>()

    fun requestNet() {
        viewModelScope.launch {
            repository.fetchWxArticle(wxArticleLiveData)
        }
    }
}
```

很简单，引入对应的数据仓库Repo，然后使用协程执行网络请求方法。来看下Repo中的代码。

### Repository中代码示例

```kotlin
class WxArticleRepository : BaseRepository() {

    private val mService by lazy { RetrofitClient.service }

    suspend fun fetchWxArticle(stateLiveData: StateLiveData<List<WxArticleBean>>) {
        executeResp(stateLiveData, mService::getWxArticle)
    }
}
interface ApiService {

    @GET("wxarticle/chapters/json")
    suspend fun getWxArticle(): BaseResponse<List<WxArticleBean>>
}
```

获取一个Retrofit实例，然后调用`ApiService`接口方法。

## 封装一的优势

- 代码很简洁，不需要手写线程切换代码，没有很多的接口回调。
- 自带Loading状态，不需要手动启用Loading和关闭Loading。

- 数据驱动ui，以LiveData为载体，将页面状态和网络结果通过在LiveData返回给ui。

项目地址见：

https://github.com/ldlywt/FastJetpack/tree/withLoading   (分支名字是：withLoading)

## 封装一的不足

封装一的核心思想是：一个LiveData贯穿整个网络请求链。这是它的优势，也是它的劣势。



- 解耦不彻底，违背了"在应用的各个模块之间设定明确定义的职责界限"的思想
- LiveData监听时，如果需要Loading，`BaseActivity`都需要实现带有Loading方法接口。

- `obserState()`方法第二个参数中传入了UI引用。

- 不能达到"看方法如其意"，如果是刚接触，会有很多疑问：为什么需要一个livedata作为方法的参数。网络请求的返回值去哪了？

- 封装一还有一个最大的缺陷：对于是多数据源，封装一就展示了很不友好的一面。

Repository是做一个数据仓库，项目中获取数据的方式都在这里同意管理，网络获取数据只是其中一个方式而已。

如果想加一个从数据库或者缓存中获取数据，封装一想改都不好改，如果强制改就破坏了封装，侵入性很大。



针对封装一的不足，优化出了封装二。

# 二、封装二
### 思路

- 想要解决上面的不足，不能以LiveData为载体贯穿整个网络请求。
- `Observe()`方法中去掉ui引用，不要小看一个ui引用，这个引用代表着`具体的Activity`跟`Observe`耦合起来了，并且`Activity`还要实现`IUiView`接口。

- 网络请求跟Loading状态分开了，需要手动控制Loading。
- Repository中的方法都有返回值，会返回结果，也不需要用livedata作为方法参数。

- LiveData只存在于ViewModel中，LiveData不会贯穿整个请求链。Repository中也不需要LiveData的引用，Repository的代码就是单纯的获取数据。
- 针对多数据源，也非常好处理。

- 跟ui没任何关系，可以完全作为一个独立的Lib使用。

### Activity中代码

```kotlin
// 请求网络
mViewModel.login("username", "password")

// 注册监听
mViewModel.userLiveData.observeState(this) {
    onSuccess {data ->
        mBinding.tvContent.text = data.toString()
    }

    onComplete {
        dismissLoading()
    }
}
```

`observeState()`中不再需要一个ui引用了。

### ViewModel中

```kotlin
class MainViewModel {

    val userLiveData = StateLiveData<User?>()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            userLiveData.value = repository.login(username, password)
        }
    }
}
```

通过livedata的setValue或者postValue方法将数据发送出去。

### Repository中

```kotlin
suspend fun login(username: String, password: String): ApiResponse<User?> {
    return executeHttp {
        mService.login(username, password)
    }
}
```

Repository中的方法都返回请求结果，并且方法参数不需要livedata。Repository完全可以独立出来了。

### 针对多数据源

```kotlin
// WxArticleRepository
class WxArticleRepository : BaseRepository() {

    private val mService by lazy {
        RetrofitClient.service
    }

    suspend fun fetchWxArticleFromNet(): ApiResponse<List<WxArticleBean>> {
        return executeHttp {
            mService.getWxArticle()
        }
    }

    suspend fun fetchWxArticleFromDb(): ApiResponse<List<WxArticleBean>> {
        return getWxArticleFromDatabase()
    }
}
// MainViewModel.kt
private val dbLiveData = StateLiveData<List<WxArticleBean>>()
private val apiLiveData = StateLiveData<List<WxArticleBean>>()
val mediatorLiveDataLiveData = MediatorLiveData<ApiResponse<List<WxArticleBean>>>().apply {
    this.addSource(apiLiveData) {
        this.value = it
    }
    this.addSource(dbLiveData) {
        this.value = it
    }
}
```

可以看到，封装二更符合职责单一原则，`Repository`单纯的获取数据，`ViewModel`对数据进行处理和发送。

项目地址：

https://github.com/ldlywt/FastJetpack   (Master分支)

## 三、实现原理

数据来源于鸿洋大神的[玩Android 开放API](https://wanandroid.com/blog/show/2)

```kotlin
回数据结构定义:
{
    "data": ...,
    "errorCode": 0,
    "errorMsg": ""
}
```

封装一和封装二的代码差距很小，主要看封装二。

### 定义数据返回类

```kotlin
open class ApiResponse<T>(
        open val data: T? = null,
        open val errorCode: Int? = null,
        open val errorMsg: String? = null,
        open val error: Throwable? = null,
) : Serializable {
    val isSuccess: Boolean
        get() = errorCode == 0
}

data class ApiSuccessResponse<T>(val response: T) : ApiResponse<T>(data = response)

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiFailedResponse<T>(override val errorCode: Int?, override val errorMsg: String?) : ApiResponse<T>(errorCode = errorCode, errorMsg = errorMsg)

data class ApiErrorResponse<T>(val throwable: Throwable) : ApiResponse<T>(error = throwable)
```

基于后台返回的基类，根据不同的结果，定义不同的状态数据类。

### 网络请求统一处理：BaseRepository

```kotlin
open class BaseRepository {

    suspend fun <T> executeHttp(block: suspend () -> ApiResponse<T>): ApiResponse<T> {
        runCatching {
            block.invoke()
        }.onSuccess { data: ApiResponse<T> ->
            return handleHttpOk(data)
        }.onFailure { e ->
            return handleHttpError(e)
        }
        return ApiEmptyResponse()
    }

    /**
     * 非后台返回错误，捕获到的异常
     */
    private fun <T> handleHttpError(e: Throwable): ApiErrorResponse<T> {
        if (BuildConfig.DEBUG) e.printStackTrace()
        handlingExceptions(e)
        return ApiErrorResponse(e)
    }

    /**
     * 返回200，但是还要判断isSuccess
     */
    private fun <T> handleHttpOk(data: ApiResponse<T>): ApiResponse<T> {
        return if (data.isSuccess) {
            getHttpSuccessResponse(data)
        } else {
            handlingApiExceptions(data.errorCode, data.errorMsg)
            ApiFailedResponse(data.errorCode, data.errorMsg)
        }
    }

    /**
     * 成功和数据为空的处理
     */
    private fun <T> getHttpSuccessResponse(response: ApiResponse<T>): ApiResponse<T> {
        return if (response.data == null || response.data is List<*> && (response.data as List<*>).isEmpty()) {
            ApiEmptyResponse()
        } else {
            ApiSuccessResponse(response.data!!)
        }
    }

}
```

Retrofit协程的错误码处理是通过异常抛出来的，所以通过try...catch来捕捉非200的错误码。包装成不同的数据类对象返回。

### 扩展LiveData和Observer

在LiveData的`Observer()`来判断是哪种数据类，进行相应的回调处理：

```kotlin
abstract class IStateObserver<T> : Observer<ApiResponse<T>> {

    override fun onChanged(apiResponse: ApiResponse<T>) {
        when (apiResponse) {
            is ApiSuccessResponse -> onSuccess(apiResponse.response)
            is ApiEmptyResponse -> onDataEmpty()
            is ApiFailedResponse -> onFailed(apiResponse.errorCode, apiResponse.errorMsg)
            is ApiErrorResponse -> onError(apiResponse.throwable)
        }
        onComplete()
    }
```

再扩展LiveData，通过kotlin的DSL表达式替换java的callback回调，简写代码。

```kotlin
class StateLiveData<T> : MutableLiveData<ApiResponse<T>>() {

    fun observeState(owner: LifecycleOwner, listenerBuilder: ListenerBuilder.() -> Unit) {
        val listener = ListenerBuilder().also(listenerBuilder)
        val value = object : IStateObserver<T>() {

            override fun onSuccess(data: T) {
                listener.mSuccessListenerAction?.invoke(data)
            }

            override fun onError(e: Throwable) {
                listener.mErrorListenerAction?.invoke(e) ?: toast("Http Error")
            }

            override fun onDataEmpty() {
                listener.mEmptyListenerAction?.invoke()
            }

            override fun onComplete() {
                listener.mCompleteListenerAction?.invoke()
            }

            override fun onFailed(errorCode: Int?, errorMsg: String?) {
                listener.mFailedListenerAction?.invoke(errorCode, errorMsg)
            }

        }
        super.observe(owner, value)
    }
}
```

## 四、总结

封装一：代码量更少，可以根据项目需要封装一些具体的ui相关，开发起来更快速，用起来更爽。

封装二：解耦更彻底，可以独立于ui模块运行。

个人认为，框架设计主要还是服务于自己的项目需求（开源项目除外），符合设计模式和设计原则更好，但是不满足也没关系，适合自己项目需求，能节省自己的时间，就是好的。

我们自己项目中使用，怎么轻便，怎么快速，怎么写的爽就怎么来。

## 五、鸣谢

非常感谢鸿洋大神提供稳定好用的[玩Android](https://wanandroid.com/blog/show/2)，业余时间用[玩Android 开放API](https://wanandroid.com/blog/show/2)折腾学习了好多东西。

感觉网上各路大神的无私分享，给我提供了很多好的思路。

这套网络框架前前后后改了很多次，终于优化到了自己还算满意的地步，如有不足，请指出交流，一起学习进步。

## 六、项目地址

https://github.com/ldlywt/FastJetpack

如果能动动手指，点个Star，不甚感激。
