package com.alievisa.bergersteak.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.sora_regular
import berger_steak_client.composeapp.generated.resources.sora_semibold
import org.jetbrains.compose.resources.Font

@Composable
fun SoraTypography(): Typography {
    val soraSemiBold = FontFamily(Font(Res.font.sora_semibold))
    val soraRegular = FontFamily(Font(Res.font.sora_regular))
    return Typography(
        body1 = TextStyle(
            fontFamily = soraSemiBold
        ),
        body2 = TextStyle(
            fontFamily = soraRegular
        )
    )
}