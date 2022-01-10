package com.aisier.network

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aisier.network.entity.ApiResponse
import com.aisier.network.observer.ResultBuilder
import com.aisier.network.observer.parseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Flow<ApiResponse<T>>.launchAndCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    listenerBuilder: ResultBuilder<T>.() -> Unit,
) {
    if (owner is Fragment) {
        owner.viewLifecycleOwner.lifecycleScope.launch {
            owner.viewLifecycleOwner.repeatOnLifecycle(minActiveState) {
                collectState(listenerBuilder)
            }
        }
    } else {
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(minActiveState) {
                collectState(listenerBuilder)
            }
        }
    }
}

suspend inline fun <T> Flow<ApiResponse<T>>.collectState(
    noinline listenerBuilder: ResultBuilder<T>.() -> Unit,
) {
    collect { apiResponse: ApiResponse<T> ->
        apiResponse.parseData(listenerBuilder)
    }
}