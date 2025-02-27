package ru.yandex.praktikumchatapp.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen

class ChatRepository(
    private val api: ChatApi = ChatApi()
) {

    fun getReplyMessage(): Flow<String> {
        var currentDelay = CURRENT_DELAY_VALUE
        return api.getReply().retryWhen { _, _ ->
            delay(currentDelay)
            currentDelay *= DELAY_FACTOR
            true
        }.onEach { currentDelay = CURRENT_DELAY_VALUE }
    }

    companion object {
        const val DELAY_FACTOR = 2
        const val CURRENT_DELAY_VALUE = 100L
    }
}