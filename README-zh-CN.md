> [English Readme](https://github.com/ldlywt/FastJetpack/edit/dev/README.md)

# 三种方式封装Retrofit+协程，实现优雅快速的网络请求

# 介绍
项目涉及到三种方式对Retrofit+协程的封装，每种封装思想都不一样

就算对协程和Kotlin flow 不熟悉，也可以快速上手

封装的目的是为了减少模板代码，方便快速开发


# 分支解释

- 封装一 (对应分支[oneWay](https://github.com/ldlywt/FastJetpack/tree/oneWay)) 传递ui引用，可按照项目进行深度ui定制，方便快速，但是耦合高(Google 最近架构文档已经不推荐在Repository中使用LiveData了，这种方式主要提供一种封装思想)

- 封装二 (对应分支[master](https://github.com/ldlywt/FastJetpack/tree/master)） 耦合低，依赖的东西很少，但是写起来模板代码偏多

- 封装三 (对应分支[dev](https://github.com/ldlywt/FastJetpack/tree/dev)) 目的就是为了减少模板代码，引入了新的flow流式编程，链式调用，loading 和网络请求以及结果处理都在一起，很多时候甚至都不要声明 LiveData/Flow 对象。


> 根据谷歌官方文档的[最新架构](https://developer.android.com/jetpack/guide)，推进使用dev分支。

# 博客地址

- [两种方式封装Retrofit+协程，实现优雅快速的网络请求](https://juejin.cn/post/6993294489125126151) 
- [使用 Kotlin Flow 优化你的网络请求框架，减少模板代码](https://juejin.cn/post/7022823222928211975)

# 鸣谢
项目的部分思路来源于其他的开源项目，感谢其他作者的无私奉献。

项目调试Api来源于鸿阳大神的 [wanandroid](https://wanandroid.com/)，多谢提供免费稳定的api。

希望大家和谐友好交流，共建开源社区。

项目会长期不定时更新优化...


