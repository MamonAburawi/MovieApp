package com.mamon.movieapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mamon.movieapp.ui.theme.Purple

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    color: Color = Purple,
    size: Int = 35,
    ic: ImageVector,
    onClicked:()-> Unit
) {
    Box(
        modifier = modifier
            .background(color, RoundedCornerShape(12.dp))
            .size(size.dp)
            .clickable { onClicked() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            tint = Color.White,
            imageVector = ic,
            contentDescription = "button"
        )
    }
}

@Preview
@Composable
fun CircleButtonPreview() {
    CircleButton(ic = Icons.Default.KeyboardArrowRight) {

    }
}