package com.aisier.architecture.net

import androidx.lifecycle.MutableLiveData
import com.aisier.architecture.entity.IBaseResponse


class StateLiveData<T> : MutableLiveData<IBaseResponse<T>>()