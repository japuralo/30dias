package com.example.a30dias

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a30dias.model.Actividad
import com.example.a30dias.model.ActividadRepository
import com.example.a30dias.ui.theme._30diasTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ActividadList(
    actividades: List<Actividad>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    // Fade in entry animation for the entire list
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = modifier
    ) {
        LazyColumn(contentPadding = contentPadding) {
            itemsIndexed(actividades) { index, actividad ->
                ActividadCard(
                    actividad = actividad,
                    modifier = Modifier.run {
                        padding(horizontal = 16.dp, vertical = 8.dp)
                            // Animate each list item to slide in vertically
                            .animateEnterExit(
                                enter = slideInVertically(
                                    animationSpec = spring(
                                        stiffness = StiffnessVeryLow,
                                        dampingRatio = DampingRatioLowBouncy
                                    ),
                                    initialOffsetY = { it * (index + 1) } // staggered entrance
                                )
                            )
                    }
                )
            }
        }
    }
}

@Composable
fun ActividadCard(
    actividad: Actividad,
    modifier: Modifier = Modifier
)
{
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier,
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)
        )
        {
            Text(
                text = stringResource(id = actividad.dayRes),
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = stringResource(id = actividad.descriptionRes),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.width(16.dp))
            Image(
                painter = painterResource(id = actividad.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = actividad.textRes),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Preview("Light Theme")
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ActividadPreview() {
    val actividad = Actividad(
        R.string.day1,
        R.string.description1,
        0,
        R.string.text1
    )
    _30diasTheme {
        ActividadCard(actividad = actividad)
    }
}

@Preview("Heroes List")
@Composable
fun ActividadesPreview() {
    _30diasTheme(darkTheme = false) {
        Surface (
            color = MaterialTheme.colorScheme.background
        ) {
            ActividadList(actividades = ActividadRepository.activities)
        }
    }
}