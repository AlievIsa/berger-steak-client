package com.alievisa.bergersteak.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        var trimmed = if (text.text.length >= 11) text.text.substring(0..10) else text.text

        var out = when {
            trimmed.isEmpty() -> ""
            trimmed.first() == '7' -> "+"
            trimmed.first() == '8' -> {
                trimmed = if (trimmed.length == 1) "7" else "7${trimmed.substring(1)}"
                "+"
            }
            else -> return TransformedText(AnnotatedString(trimmed), OffsetMapping.Identity)
        }

        for (i in trimmed.indices) {
            if (i == 1) out += " ("
            if (i == 4) out += ") "
            if (i == 7) out += "-"
            if (i == 9) out += "-"
            out += trimmed[i]
        }
        return TransformedText(AnnotatedString(out), phoneNumberOffsetTranslator)
    }


    private val phoneNumberOffsetTranslator = object : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int =
            when (offset) {
                0 -> offset
                1 -> offset + 1
                in 2..4 -> offset + 3
                in 5..7 -> offset + 5
                in 8..9 -> offset + 6
                else -> offset + 7
            }

        override fun transformedToOriginal(offset: Int): Int =
            when (offset) {
                0 -> offset
                in 1..2 -> offset - 1
                in 2..4 -> offset - 3
                in 5..7 -> offset - 5
                in 8..9 -> offset - 6
                else -> offset - 7
            }
    }
}