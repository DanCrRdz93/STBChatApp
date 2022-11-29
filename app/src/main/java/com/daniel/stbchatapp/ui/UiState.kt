package com.daniel.stbchatapp.ui

import com.daniel.stbchatapp.model.FastChat


sealed class UiState {
    data class Success (val chatList: List<FastChat>) : UiState()
    data class Error (val throwable: Throwable) : UiState()

    object Loading : UiState()
}