package com.alievisa.bergersteak.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alievisa.bergersteak.domain.models.DishModel

@Composable
fun DishesList(
    dishes: List<DishModel>,
    isMainButtonVisible: Boolean,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp)
    ) {

        items(dishes.chunked(2)) { row ->
            Row(
                Modifier
                    .padding(horizontal = 12.dp)
            ) {
                row.forEach { dish ->
                    MenuItem(
                        dishModel = dish,
                        onDishClick = { },
                        onAddDishClick = { },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (row.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        if (isMainButtonVisible) {
            item {
                Spacer(modifier = Modifier.height(70.dp))
            }
        }
    }
}