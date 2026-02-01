package org.example.project

import kotlinx.coroutines.CoroutineDispatcher

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect val DispatcherIO: CoroutineDispatcher