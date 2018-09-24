package com.bonnetrouge.shopifyproducts

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.newFixedThreadPoolContext

inline fun AppCompatActivity.fragmentTransaction(addToBackStack: Boolean = false,
                                                 tag: String? = null,
                                                 swapInfo: FragmentTransaction.() -> Unit) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.swapInfo()
    if (addToBackStack) {
        transaction.addToBackStack(tag)
    }
    transaction.commit()
}

/**
 * Faster lazy delegation for Android.
 * Warning: Only use for objects accessed on main thread
 */
fun <T> lazyAndroid(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

/**
 * CommonPool determines its thread pool size by using:
 *   Runtime.getRuntime().availableProcessors() - 1
 * It will then default to at least 1. We default to at least 2
 *   since using only 1 can be problematic
 *
 * The following can be used as such: `launch(bgPool) { /* Some very cool fancy async code */ }`
 */
val bgPool: CoroutineDispatcher by lazy {
    val numProcessors = Runtime.getRuntime().availableProcessors()
    when {
        numProcessors <= 2 -> newFixedThreadPoolContext(2, "background")
        else -> CommonPool
    }
}

/** Syntax helper to convert
 * data.observe(this, Observer<Int> { ... })
 * to
 * data.observe(this) { ... }
 */
fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) = observe(owner, Observer(observer))
