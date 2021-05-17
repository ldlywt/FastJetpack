package com.aisier.architecture.util

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
利用SingleLiveEvent 使 observe#LiveData时只相应一次onChanged操作

1 SingleLiveEvent 利用 AtomicBoolean （默认为false）进行赋值，当LiveData 进行 setValue时
改变 AtomicBoolean的值（set(true)）

2 使用 AtomicBoolean.compareAndSet(true,false)方法,先进行判断（此时的AtomicBoolean的值为true）
与 compareAndSet设置的except值（第一个参数）比较，因为相等所以将第二个参数设置为AtomicBoolean值设为false
函数并返回 true ，）

3 当再次进入该页面虽然 LiveData值并没有改变，仍然触发了 observer方法，由于 AtomicBoolean已经为 false ，但是 except值为 true ，
与if 进行判断所以 并不会继续触发 onChanged（T）方法

即只有在 setValue时相应一次onChanged(T)方法。
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w("SingleLiveEvent", "Multiple observers registered but only one will be notified of changes.")
        }
        super.observe(owner, { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    @MainThread
    fun call() {
        value = null
    }

}