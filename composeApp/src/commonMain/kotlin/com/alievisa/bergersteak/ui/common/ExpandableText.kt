package com.alievisa.bergersteak.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.expand
import berger_steak_client.composeapp.generated.resources.shrink
import com.alievisa.bergersteak.ui.theme.AppDefaults
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExpandableText(
    text: String,
    collapsedMaxLines: Int = 3,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var textOverflow by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            maxLines = if (expanded) Int.MAX_VALUE else collapsedMaxLines,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            color = Color.Black,
            onTextLayout = { result ->
                textOverflow = !expanded && result.hasVisualOverflow
            },
            modifier = modifier
                .fillMaxWidth()
        )

        if (textOverflow || expanded) {
            Text(
                text = if (expanded) stringResource(Res.string.shrink) else stringResource(Res.string.expand),
                color = AppDefaults.Colors.brown,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.End)
                    .clickable { expanded = !expanded }
            )
        }
    }
}
