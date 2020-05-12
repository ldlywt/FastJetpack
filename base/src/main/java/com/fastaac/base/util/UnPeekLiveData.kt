/*
 * Copyright 2018-2020 KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fastaac.base.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * 仅分发 owner observe 后 才新拿到的数据
 * 可避免共享作用域 VM 下 liveData 被 observe 时旧数据倒灌的情况
 *
 *
 * Create by KunMinX at 19/9/23
 */
class UnPeekLiveData<T> : MutableLiveData<T>() {
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        hook(observer)
    }

    private fun hook(observer: Observer<in T>) {
        val liveDataClass = LiveData::class.java
        try {
            //获取field private SafeIterableMap<Observer<T>, ObserverWrapper> mObservers
            val mObservers = liveDataClass.getDeclaredField("mObservers")
            mObservers.isAccessible = true
            //获取SafeIterableMap集合mObservers
            val observers = mObservers[this]
            val observersClass: Class<*> = observers.javaClass
            //获取SafeIterableMap的get(Object obj)方法
            val methodGet = observersClass.getDeclaredMethod("get", Any::class.java)
            methodGet.isAccessible = true
            //获取到observer在集合中对应的ObserverWrapper对象
            val objectWrapperEntry = methodGet.invoke(observers, observer)
            var objectWrapper: Any? = null
            if (objectWrapperEntry is Map.Entry<*, *>) {
                objectWrapper = objectWrapperEntry.value
            }
            if (objectWrapper == null) {
                throw NullPointerException("ObserverWrapper can not be null")
            }
            //获取ObserverWrapper的Class对象  LifecycleBoundObserver extends ObserverWrapper
            val wrapperClass: Class<*> = objectWrapper.javaClass.superclass
            //获取ObserverWrapper的field mLastVersion
            val mLastVersion = wrapperClass.getDeclaredField("mLastVersion")
            mLastVersion.isAccessible = true
            //获取liveData的field mVersion
            val mVersion = liveDataClass.getDeclaredField("mVersion")
            mVersion.isAccessible = true
            val mV = mVersion[this]
            //把当前ListData的mVersion赋值给 ObserverWrapper的field mLastVersion
            mLastVersion[objectWrapper] = mV
            mObservers.isAccessible = false
            methodGet.isAccessible = false
            mLastVersion.isAccessible = false
            mVersion.isAccessible = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}