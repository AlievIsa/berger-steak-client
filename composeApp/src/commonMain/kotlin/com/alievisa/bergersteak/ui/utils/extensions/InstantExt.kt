package com.alievisa.bergersteak.ui.utils.extensions

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime

fun Instant.formatAsReadableDate(): String {
    val local = this.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${local.hour}:${local.minute} - ${local.dayOfMonth.toString().padStart(2, '0')}." +
            "${local.month.number.toString().padStart(2, '0')}.${local.year}"
}