package org.example.project

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


actual val DispatcherIO: CoroutineDispatcher = Dispatchers.Default