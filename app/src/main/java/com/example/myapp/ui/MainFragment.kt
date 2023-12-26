package com.example.myapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import com.example.myapp.R
import com.example.myapp.data.Group
import com.example.myapp.data.Publication


class MainFragment : Fragment() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return ComposeView(
            requireContext()
        ).apply {
            setContent {
                val viewModel: AppViewModel = viewModel(requireActivity())

                val group by viewModel.group.collectAsState()
                val publications by viewModel.publications.collectAsState()

                var changeFragment: () -> Unit =  {parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SecondFragment.newInstance())
                    .commit()}

                viewModel.setGroup(1)
                viewModel.getPublications()
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(onClick = {

                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    },
                    topBar = {
                        TopAppBar(
                            title = { Text(group.title) },
                            navigationIcon = {
                                IconButton(
                                    onClick = { /*TODO*/ }) {
                                    Icon(Icons.Default.ArrowBack, contentDescription = "go back")
                                }
                            })
                    }
                ) {
                    MaterialTheme() {
                        MainScreen(Modifier.padding(top = 64.dp), viewModel, changeFragment, group, publications)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment()
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier,
               viewModel: AppViewModel,
               switchFrag: () -> Unit,
               group: Group,
               publications: List<Publication>) {
    LazyColumn(modifier = modifier) {
        item {
            Column {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                ) {
                    ImageComponent(
                        imageUrl = group.coverUrl,
                        modifier = Modifier.fillMaxWidth()
                    ) {}
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(20.dp)
                ) {
                    Column() {
                        Text(text = group.title, fontWeight = FontWeight.W500, fontSize = 20.sp)
                        Text(text = group.description)
                    }
                }
            }
        }

        items(publications) {
            Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                Box(modifier = Modifier.padding(top = 20.dp)) {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(3.dp)
                    ) {
                        ImageComponent(
                            imageUrl = it.imageUrl,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            viewModel.setCurrentPublication(it.id)
                            switchFrag()
                        }
                        Text(text = it.title, modifier = Modifier.padding(9.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun ImageComponent(imageUrl: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = null
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                Column(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            is AsyncImagePainter.State.Error -> {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(stringResource(id = R.string.image_loading_error))
                }
            }

            else -> {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = modifier.clickable(onClick = onClick),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
