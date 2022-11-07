package com.example.currencies.domain

import javax.inject.Inject

private const val regex = "^[0-9]+(.[0-9]{0,2})?$"

class GetCurrencyStringUseCase @Inject constructor(
) {
    operator fun invoke(oldValue: String, newValue: String): String {
        if (newValue.matches(Regex(regex)) || newValue.isEmpty()) {
            return newValue.replace(',', '.')
        }
        return oldValue
    }
}