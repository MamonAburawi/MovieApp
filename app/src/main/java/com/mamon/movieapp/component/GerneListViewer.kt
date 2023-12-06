package com.mamon.movieapp.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dto.movie_detail.Genre
import com.mamon.movieapp.ui.theme.LightGray
import com.mamon.movieapp.ui.theme.Purple

@Composable
fun GenresListViewer(
    genres: List<Genre>,
    itemColor: Color = Purple,
    size: Int = 15
) {
    AnimatedVisibility(visible = genres.isNotEmpty()) {
        LazyRow( modifier = Modifier
            .fillMaxWidth(),
            contentPadding = PaddingValues( vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = genres) {
                Text(
                    modifier = Modifier
                        .background(itemColor, RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    text = it.name,
                    color = LightGray,
                    fontSize = size.sp
                )
            }
        }
    }
}
