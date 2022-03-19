package com.aisier.architecture.ktx

import android.app.Activity
import android.content.Intent
import android.os.Bundle

inline fun <reified T : Activity> Activity.startActivity(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}
