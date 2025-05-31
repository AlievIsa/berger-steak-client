package com.alievisa.bergersteak.ui.utils.extensions

import androidx.compose.runtime.Composable
import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.cal
import berger_steak_client.composeapp.generated.resources.gram
import berger_steak_client.composeapp.generated.resources.rub
import berger_steak_client.composeapp.generated.resources.sec
import org.jetbrains.compose.resources.stringResource

@Composable
fun Int.rub() = "$this ${stringResource(Res.string.rub)}"

@Composable
fun Int.gram() = "$this ${stringResource(Res.string.gram)}"

@Composable
fun Int.cal() = "$this ${stringResource(Res.string.cal)}"

@Composable
fun Int.sec() = "$this ${stringResource(Res.string.sec)}"

fun Int.roundUpDiv(other: Int): Int {
    return if (other == 0) 0 else this / other + this % 2
}
