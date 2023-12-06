package com.mamon.movieapp.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import com.mamon.movieapp.ui.theme.SeeMore

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 2,
) {
    var cutText by remember(text) { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }

    // getting raw values for smart cast
    val textLayoutResult = textLayoutResultState.value
    val seeMoreSize = seeMoreSizeState.value
    val seeMoreOffset = seeMoreOffsetState.value

    LaunchedEffect(text, expanded, textLayoutResult, seeMoreSize) {
        val lastLineIndex = minimizedMaxLines - 1
        if (!expanded && textLayoutResult != null && seeMoreSize != null
            && lastLineIndex + 1 == textLayoutResult.lineCount
            && textLayoutResult.isLineEllipsized(lastLineIndex)
        ) {
            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult.getCursorRect(lastCharIndex)
            } while (
                charRect.left > textLayoutResult.size.width - seeMoreSize.width
            )
            seeMoreOffsetState.value = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
            cutText = text.substring(startIndex = 0, endIndex = lastCharIndex)
        }
    }

    Box(modifier) {
        Text(
            text = cutText ?: text,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResultState.value = it },
            color = SeeMore
        )
        if (!expanded) {
            val density = LocalDensity.current
            Text(
                text = "... See more",
                color = Color(0x2DFF978C).copy(alpha = 0.78F),
                onTextLayout = { seeMoreSizeState.value = it.size },
                modifier = Modifier
                    .then(
                        if (seeMoreOffset != null)
                            Modifier.offset(
                                x = with(density) { seeMoreOffset.x.toDp() },
                                y = with(density) { seeMoreOffset.y.toDp() },
                            )
                        else
                            Modifier
                    )
                    .clickable {
                        expanded = true
                        cutText = null
                    }
                    .alpha(if (seeMoreOffset != null) 1f else 0f)
            )
        }
    }
}
//
//@Composable
//fun ExpandableText(
//    text: String,
//    modifier: Modifier = Modifier,
//    minimizedMaxLines: Int = 2,
//) {
//    var cutText by remember(text) { mutableStateOf<String?>(null) }
//    var expanded by remember { mutableStateOf(false) }
//    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
//    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
//    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }
//
//    // getting raw values for smart cast
//    val textLayoutResult = textLayoutResultState.value
//    val seeMoreSize = seeMoreSizeState.value
//    val seeMoreOffset = seeMoreOffsetState.value
//
//    LaunchedEffect(text, expanded, textLayoutResult, seeMoreSize) {
//        val lastLineIndex = minimizedMaxLines - 1
//        if (!expanded && textLayoutResult != null && seeMoreSize != null &&
//            lastLineIndex + 1 == textLayoutResult.lineCount &&
//            textLayoutResult.isLineEllipsized(lastLineIndex)
//        ) {
//            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
//            var charRect: Rect
//            do {
//                lastCharIndex -= 1
//                charRect = textLayoutResult.getCursorRect(lastCharIndex)
//            } while (
//                charRect.left > textLayoutResult.size.width - seeMoreSize.width
//            )
//            seeMoreOffsetState.value = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
//            cutText = text.substring(startIndex = 0, endIndex = lastCharIndex)
//        }
//    }
//
//    Box(modifier) {
//        Text(
//            color = DarkBlue,
//            text = cutText ?: text,
//            modifier = Modifier
//                .clickable(
//                    interactionSource = MutableInteractionSource(),
//                    indication = null
//                ) {
//                    if (expanded) {
//                        expanded = false
//                    }
//                },
//            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
//            overflow = TextOverflow.Ellipsis,
//            onTextLayout = { textLayoutResultState.value = it },
//        )
//
//        if (!expanded) {
//            val density = LocalDensity.current
//            Text(
//                color = Color(0x2DFF978C).copy(alpha = 0.78F),
//                text = "... See more",
//                fontWeight = FontWeight.Bold,
//                fontSize = 14.sp,
//                onTextLayout = { seeMoreSizeState.value = it.size },
//                modifier = Modifier
//                    .then(
//                        if (seeMoreOffset != null)
//                            Modifier.offset(
//                                x = with(density) { seeMoreOffset.x.toDp() },
//                                y = with(density) { seeMoreOffset.y.toDp() },
//                            )
//                        else Modifier
//                    )
//                    .clickable(
//                        interactionSource = MutableInteractionSource(),
//                        indication = null
//                    ) {
//                        expanded = true
//                        cutText = null
//                    }
//                    .alpha(if (seeMoreOffset != null) 1f else 0f)
//                    .verticalScroll(
//                        enabled = true,
//                        state = rememberScrollState()
//                    )
//            )
//        }
//    }
//}

@Preview
@Composable
fun ExpandableTextPreview() {
    ExpandableText(
        text = "sldkfjlsdjlkjdsf  sdlkfjslkdf lksdjf slkdj skldfj slk dfjsld fdlskfj ldjf lk dfslkjd"
    )


}