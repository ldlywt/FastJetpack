package com.aisier.architecture.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import com.blankj.utilcode.util.NetworkUtils
import com.aisier.architecture.R


class NetworkStateReceive : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            if (!NetworkUtils.isConnected()) {
                Toast.makeText(context, context.getString(R.string.network_not_good), Toast.LENGTH_SHORT).show()
                NetworkStateManager.networkStateCallback.postValue(NetState().apply { this.isSuccess = false })
            } else {
                NetworkStateManager.networkStateCallback.postValue(NetState().apply { this.isSuccess = true })
            }
        }
    }
}