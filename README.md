# 三种方式封装Retrofit+协程，实现优雅快速的网络请求

# 介绍
项目涉及到三种方式对Retrofit+协程的封装，每种封装思想都不一样

就算对协程和Kotlin flow 不熟悉，也可以快速上手

封装的目的是为了减少模板代码，方便快速开发


# 分支解释

- 封装一 (对应分支[oneWay](https://github.com/ldlywt/FastJetpack/tree/oneWay)) 传递ui引用，可按照项目进行深度ui定制，方便快速，但是耦合高

- 封装二 (对应分支[master](https://github.com/ldlywt/FastJetpack/tree/master)） 耦合低，依赖的东西很少，但是写起来模板代码偏多

- 封装三 (对应分支[dev](https://github.com/ldlywt/FastJetpack/tree/dev)) 引入了新的flow流式编程（虽然出来很久，但是大部分人应该还没用到），链式调用，loading 和网络请求以及结果处理都在一起，很多时候甚至都不要声明 LiveData 对象。

第二种封装我在公司的商业项目App中用了很长时间了，涉及几十个接口，暂时没遇到什么问题。

第三种是我最近才折腾出来的，在公司的新项目中（还没上线）使用，也暂时没遇到什么问题。

# 博客地址

- [两种方式封装Retrofit+协程，实现优雅快速的网络请求](https://juejin.cn/post/6993294489125126151) 
- [使用 Kotlin Flow 优化你的网络请求框架，减少模板代码](https://juejin.cn/post/7022823222928211975)

# 鸣谢
项目的部分思路来源于其他的开源项目，感谢其他作者的无私奉献。

项目调试Api来源于鸿阳大神的 [wanandroid](https://wanandroid.com/)，多谢提供免费稳定的api。

希望大家和谐友好交流，共建开源社区。

项目会长期不定时更新优化...

如果该项目对你有帮助，麻烦动动手指star一个，谢谢

# License
MIT License

Copyright (c) 2021 wutao

