package com.example.testinicial.data

import androidx.compose.runtime.mutableStateListOf
import com.example.testinicial.presentation.profile.PaymentMethod

object SharedPaymentState {
    val paymentMethods = mutableStateListOf<PaymentMethod>()
}
