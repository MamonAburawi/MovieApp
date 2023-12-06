package com.mamon.movieapp.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.module.Movie

@Composable
fun ScrollableMovieItems(
    sectionName: String,
    items: List<Movie> = emptyList(),
    visible: Boolean = items.isNotEmpty(),
    landscape: Boolean = false,
    onItemClicked: (Movie) -> Unit
) {
    AnimatedVisibility(visible = visible) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(if (!landscape) 215.dp else 195.dp)
        ) {
            Column {
                Text(
                    text = sectionName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(items) { film ->
                        val imagePath = if (landscape) film.coverUrl else film.imgUrl
                        val title = film.title ?: film.name ?: ""

                        MovieItem(
                            landscape = landscape,
                            imageUrl = imagePath,
                            title = title,
                            modifier = Modifier
                                .width(if (landscape) 215.dp else 130.dp)
                                .height(if (landscape) 161.25.dp else 195.dp)
                        ) { onItemClicked(film) }



                    }
                }

            }

        }
    }

}


@Preview
@Composable
fun ScrollableMovieItemsPreview() {
    val testList  = ArrayList<Movie>()
    repeat(10){
        testList.add(
            Movie(
                id= it,
                coverUrl ="https://image.tmdb.org/t/p/w500/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg",
                imgUrl = "https://image.tmdb.org/t/p/w500/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg",
                releaseDate = "2005-11-16",
                title = "Harry Potter and the Chamber of Secrets",
                rate = 7.718,
                ratersCount = 20624,
                description = "Cars fly, trees fight back, and a mysterious house-elf comes to warn Harry Potter at the start of his second year at Hogwarts. Adventure and danger await when bloody writing on a wall announces: The Chamber Of Secrets Has Been Opened. To save Hogwarts will require all of Harry, Ron and Hermione’s magical abilities and courage.",
                genreIds = emptyList(),
                mediaType = "",
                name = ""
            )
        )
    }

    ScrollableMovieItems(
        sectionName = "Movies",
        items = testList,
        onItemClicked = {

        }
    )


}