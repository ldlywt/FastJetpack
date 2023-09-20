[简体中文](https://github.com/ldlywt/FastJetpack/blob/dev/README-zh-CN.md)

# Three ways to encapsulate Retrofit + Kotlin Coroutines for elegant and fast network requests

# Introduction
This project involves three ways to encapsulate Retrofit + Kotlin Coroutines, each encapsulation idea is different.

Even if you are not familiar with the concatenation and Kotlin flow, you can get started quickly.

The purpose of the wrappers is to reduce boilerplate code and facilitate rapid development.


# Branching Explained

- Encapsulation 1 (corresponding to branch [oneWay](https://github.com/ldlywt/FastJetpack/tree/oneWay)) passes ui references, allowing deep ui customization according to the project, which is fast and convenient, but highly coupled (Google's recent architecture documentation no longer recommends using LiveData in the repository). Google's recent architecture documentation no longer recommends the use of LiveData in the Repository, this approach mainly provides a kind of encapsulation of ideas)

- Encapsulation two (corresponding to branch [master](https://github.com/ldlywt/FastJetpack/tree/master)) is low coupling, few dependencies, but more template code.

- Encapsulation 3 (corresponding to branch [dev](https://github.com/ldlywt/FastJetpack/tree/dev)) aims to reduce boilerplate code, introduces the new flow stream programming, chaining calls, loading and network requests and result processing are all together, and in many cases don't even declare the LiveData/Flow objects.


> According to the [latest architecture](https://developer.android.com/jetpack/guide) of Google's official documentation, advancing the use of dev branches.

# Blog address

- [Two ways to encapsulate Retrofit + concatenation for elegant and fast network requests](https://juejin.cn/post/6993294489125126151) 
- [Optimizing your web request framework with Kotlin Flow to reduce boilerplate code](https://juejin.cn/post/7022823222928211975)

# Acknowledgments
Some of the ideas of the project come from other open source projects, thanks to other authors for their selfless contributions.

The project debugging Api comes from [wanandroid](https://wanandroid.com/) of Hongyang God, thanks for providing free and stable api.

I hope we can have a harmonious and friendly communication to build an open source community.

Translated with DeepL


