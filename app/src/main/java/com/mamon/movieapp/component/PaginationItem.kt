package com.mamon.movieapp.component


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PaginationItem(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    currentPage: Int,
    totalPages: Int,
    onPreviousClicked:() -> Unit,
    onNextClicked: () -> Unit,
) {
    AnimatedVisibility(visible = isVisible) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (currentPage > 1) {
                    CircleButton(ic = Icons.Default.KeyboardArrowLeft) {
                        onPreviousClicked()
                    }
                }

                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = "Page $currentPage of $totalPages",
                    color = Color.White
                )

                if (currentPage < totalPages) {

                    CircleButton(ic = Icons.Default.KeyboardArrowRight) {
                        onNextClicked()
                    }

                }
            }
        }
    }

}


@Preview
@Composable
fun PaginationPreview() {
    PaginationItem(
        currentPage = 1,
        totalPages = 50,
        onPreviousClicked = {  },
        onNextClicked = {

        }
    )
}