package com.alievisa.bergersteak.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.alievisa.bergersteak.ui.utils.shimmerBackground

@Composable
fun MenuShimmer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp, horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            repeat(4) {
                Spacer(
                    modifier = Modifier.height(36.dp).width(80.dp).clip(RoundedCornerShape(6.dp)).shimmerBackground())
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(24.dp).width(140.dp).clip(RoundedCornerShape(6.dp)).shimmerBackground())

        Spacer(modifier = Modifier.height(12.dp))

        repeat(2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.height(280.dp).weight(1f).clip(RoundedCornerShape(12.dp)).shimmerBackground())
                Spacer(modifier = Modifier.width(16.dp))
                Spacer(modifier = Modifier.height(280.dp).weight(1f).clip(RoundedCornerShape(12.dp)).shimmerBackground())
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}