package com.example.gilgoldzweig.moviedemo.modules.logging.analytics

/**
 * part of a custom implementation of Jake wharton Timber, I always use it
 * And I didn't want to spend to much time converting it fully to java
 */
interface Eventable {
    val displayName: String

    override fun toString(): String
}

interface EventableParam : Eventable

interface EventableType : Eventable
