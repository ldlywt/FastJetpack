package com.aisier.architecture

/**
 * <pre>
 *     @author : wutao
 *     e-mail : wutao@neuron.sg
 *     time   : 2020/05/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */

sealed class StateActionEvent

object LoadState : StateActionEvent()

class ToastState(val message: String?) : StateActionEvent()

object SuccessState : StateActionEvent()

object EmptyState : StateActionEvent()

class ErrorState(val message: String?) : StateActionEvent()
