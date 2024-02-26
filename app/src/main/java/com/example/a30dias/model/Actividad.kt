package com.example.a30dias.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Actividad(
    @StringRes val dayRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int,
    @StringRes val textRes: Int
)
