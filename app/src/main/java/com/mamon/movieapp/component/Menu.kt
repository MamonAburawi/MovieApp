package com.mamon.movieapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp



@Composable
fun Menu(
    modifier: Modifier = Modifier,
    items: List<String>,
    onItemClicked:(String) -> Unit
) {
    var expanded by remember{ mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(items.first()) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.width(80.dp)
        ) {

            Row(

            ) {
                Image(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    text = selectedItem,
                    color = Color.White
                )
            }

        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selectedItem = item
                        expanded = false
                        onItemClicked(item)
                    },
                    modifier = Modifier.height(48.dp)
                )
            }
        }
    }
}