package com.example.myapplication


/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 * Используется в качестве оболочки для данных, которые предоставляются через LiveData, представляющий событие.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     * Возвращает содержимое и предотвращает его повторное использование.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     * Возвращает содержимое, даже если оно уже было обработано.
     */
    fun peekContent(): T = content
}
