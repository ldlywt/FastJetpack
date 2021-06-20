package com.aisier.architecture.base

import com.aisier.architecture.entity.ApiResponse
import com.aisier.architecture.entity.ResState
import com.aisier.architecture.entity.handlingApiExceptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> executeResp(
            resp: ApiResponse<T>,
            successBlock: (suspend CoroutineScope.() -> Unit)? = null,
            errorBlock: (suspend CoroutineScope.() -> Unit)? = null,
    ): ResState<T> {
        return coroutineScope {
            if (resp.errorCode == 0) {
                successBlock?.let { it() }
                ResState.Success(resp.data)
            } else {
                errorBlock?.let { it() } ?: handlingApiExceptions(resp.errorCode, resp.errorMsg)
                ResState.Error(IOException(resp.errorMsg))
            }
        }
    }

}