package com.daniel.stbchatapp.model


/***
 * Values must be initialized
 */
data class FastChat(
    val user: String = "",
    val message: String = "",
    val timeMillis: Long = 0L
)
