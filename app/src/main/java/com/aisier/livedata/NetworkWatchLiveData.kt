package com.aisier.livedata

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import com.aisier.architecture.base.BaseApp

class NetworkWatchLiveData : LiveData<NetworkInfo?>() {
    private val mContext = BaseApp.instance
    private val mNetworkReceiver: NetworkReceiver = NetworkReceiver()
    private val mIntentFilter: IntentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

    override fun onActive() {
        mContext.registerReceiver(mNetworkReceiver, mIntentFilter)
    }

    override fun onInactive() = mContext.unregisterReceiver(mNetworkReceiver)

    private class NetworkReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val manager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = manager.activeNetworkInfo
            get().postValue(activeNetwork)
        }
    }

    companion object {

        private lateinit var sInstance: NetworkWatchLiveData

        @MainThread
        fun get(): NetworkWatchLiveData {
            sInstance = if (::sInstance.isInitialized) sInstance else NetworkWatchLiveData()
            return sInstance
        }
    }
}