package es.santyarbo.myfootball.ui.common

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

interface Scope : CoroutineScope {

    class Impl(override var uiDispatcher: CoroutineDispatcher) : Scope {
        override lateinit var job: Job
    }

    var job: Job
    var uiDispatcher: CoroutineDispatcher

    override val coroutineContext: CoroutineContext
        get() = uiDispatcher + job

    fun initScope() {
        job = SupervisorJob()
    }

    fun destroyScope() {
        job.cancel()
    }
}