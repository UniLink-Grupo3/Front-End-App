package com.example.testinicial.data

import androidx.compose.runtime.mutableStateListOf
import com.example.testinicial.presentation.history.TripHistory

object SharedHistoryState {
    val tripHistoryList = mutableStateListOf<TripHistory>()
}
