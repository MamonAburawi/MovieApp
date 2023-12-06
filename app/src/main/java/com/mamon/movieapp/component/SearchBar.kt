@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.mamon.movieapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mamon.movieapp.ui.theme.Purple


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    text : String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    query:(String) -> Unit
) {
    var v by remember{ mutableStateOf("") }

        TextField(
            modifier = modifier
                .clip(RoundedCornerShape(25.dp))
                .height(55.dp)
                .focusable(false),
            value = text,
            placeholder = {
                Text(
                    text = hint,
                    color =  Color.White.copy(alpha = 0.8f)
                )
            },
            onValueChange = {
                query(it)
            },
            trailingIcon = {
                Row(
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    if (v.isNotEmpty()){
                        Image(
                            modifier = Modifier
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { v = "" }
                                .size(22.dp),
                            imageVector = Icons.Default.Close,
                            contentDescription = "clear icon",
                            colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.8f))
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                    }
                    Image(
                        modifier = Modifier
                            .size(22.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = "search icon",
                        colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.8f))
                    )
                }

            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.White,
                focusedSupportingTextColor = Color.LightGray,
                focusedIndicatorColor = Purple,
                disabledIndicatorColor = Purple,
                unfocusedIndicatorColor = Purple,
                containerColor = Purple,
                textColor = Color.White
            )
        )

}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(
        hint = "Search",
        query = {}
    )
}