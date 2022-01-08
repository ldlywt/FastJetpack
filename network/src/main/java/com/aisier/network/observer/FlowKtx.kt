package com.aisier.network.observer

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aisier.network.entity.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

inline fun <T> Flow<ApiResponse<T>>.launchAndCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline listenerBuilder: ResultBuilder<T>.() -> Unit,
) = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(minActiveState) {
        collectState(listenerBuilder)
    }
}

suspend inline fun <T> Flow<ApiResponse<T>>.collectState(
    listenerBuilder: ResultBuilder<T>.() -> Unit,
) {
    val listener = ResultBuilder<T>().also(listenerBuilder)
    collect { apiResponse: ApiResponse<T> ->
        apiResponse.parseData(listener)
    }
}