package com.example.gilgoldzweig.moviedemo.models.threads

import io.reactivex.Scheduler

/**
 * I'm sorry that this class is in kotlin but I had it ready from another project of mine that is
 * written in kotlin
 *
 * This class makes it easier to separate rxjava tasks to their proper execution thread
 * while providing single access to an existing objects
 */
data class RxSchedulers(
		val database: Scheduler,
		val disk: Scheduler,
		val network: Scheduler,
		val main: Scheduler)
