package com.bethena.base_wall

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * App全局ViewModel可直接替代EventBus
 *
 */
class AppViewModel : ViewModel() {

    val wallChangeEvent = MutableLiveData<String>()

}