package com.example.gilgoldzweig.moviedemo.modules.logging.utils

/**
 * Created by gilgoldzweig on 05/11/2017.
 * * part of a custom implementation of Jake wharton Timber, I always use it
 * And I didn't want to spend to much time converting it fully to java
 */
interface BundlifyType<E: Any> {
    var key: String
    var value: E
}