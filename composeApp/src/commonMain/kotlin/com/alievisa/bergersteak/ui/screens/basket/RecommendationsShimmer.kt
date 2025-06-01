package com.alievisa.bergersteak.ui.screens.basket

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
fun RecommendationsShimmer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 8.dp)
    ) {

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