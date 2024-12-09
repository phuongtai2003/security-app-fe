package com.security.securityapplication.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (T) -> Unit) {
    liveData.observe(this, Observer {
        it?.let { t -> action(t) }
    })
}

fun <T> LifecycleOwner.observeSingleEvent(liveData: LiveData<SingleEvent<T>>, action: (SingleEvent<T>) -> Unit) {
    liveData.observe(this, Observer{
        it?.let { t -> action(t) }
    })
}
