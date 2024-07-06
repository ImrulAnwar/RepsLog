package com.imrul.replog.feature_exercises.presentation.screen_exercises

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.imrul.replog.R
import com.imrul.replog.core.Routes
import com.imrul.replog.core.presentation.CustomButton
import com.imrul.replog.feature_exercises.presentation.components.CustomFlowRow
import com.imrul.replog.feature_exercises.presentation.components.ExerciseListItem
import com.imrul.replog.feature_workout.presentation.components.CustomTextField
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.ui.theme.Maroon10
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.WhiteCustom
import kotlinx.coroutines.delay

@Composable
fun ExerciseListScreen(
    navController: NavHostController,
    viewModel: ExerciseListViewModel,
    context: Context = LocalContext.current,
    workoutViewModel: WorkoutViewModel
) {
    val exerciseListState by viewModel.exercisesList.collectAsState()
    val weightTypeFilterList by viewModel.weightTypeFilterList.collectAsState()
    val targetMuscleFilterList by viewModel.targetMuscleFilterList.collectAsState()
    var isSearchExpanded by remember { mutableStateOf(false) }
    val searchText = viewModel.searchText
    val isLoading = viewModel.isLoading

    LaunchedEffect(Unit) {
        viewModel.getAllExercises()
    }
    LaunchedEffect(isSearchExpanded) {
        if (!isSearchExpanded) viewModel.onSearchTextChanged("")
    }

    LaunchedEffect(searchText, weightTypeFilterList, targetMuscleFilterList) {
        viewModel.updateExerciseList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteCustom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            if (!isSearchExpanded)
                CustomButton(
                    onClick = {
                        navController.navigate(
                            Routes.ScreenAddEditExercises(
                                exerciseId = -1
                            )
                        )
                    },
                    text = "Add Exercise",
                    modifier = Modifier.padding(10.dp)
                )
            else CustomTextField(
                text = searchText,
                onValueChange = { viewModel.onSearchTextChanged(it) },
                label = "Search",
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 2.dp, horizontal = 5.dp)
            )
            Row(
                Modifier.padding(10.dp)
            ) {
                Icon(
                    imageVector = if (isSearchExpanded) Icons.Filled.Clear else Icons.Filled.Search,
                    contentDescription = "More Details",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            isSearchExpanded = !isSearchExpanded
                        }
                )
                Spacer(modifier = Modifier.width(20.dp))
                Icon(
                    painter = painterResource(R.drawable.icon_filter),
                    contentDescription = "More Details",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            // go to filter page
                            // then toggle filters
                            navController.navigate(Routes.ScreenFilterExercise)
                        }
                )
            }
        }
        CustomFlowRow(
            filterList = weightTypeFilterList + targetMuscleFilterList,
            item = { text ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Maroon10)
                        .padding(start = 5.dp)
                        .padding(5.dp),
                ) {
                    Text(
                        text = text,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = "close"
                    )
                }
            },
            onCLick = {
                viewModel.removeWeightTypeFilter(it)
                viewModel.removeTargetMuscleFilter(it)
            }
        )

        // list
        if (isLoading)
            CircularProgressIndicator(color = Maroon70)
        else
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                items(exerciseListState.size) { exercise ->
                    ExerciseListItem(
                        navController = navController,
                        exercise = exerciseListState[exercise],
                        onClick = {}
                    )
                }

            }
    }

}

