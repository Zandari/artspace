package com.example.myapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapp.R
import com.example.myapp.data.Publication

class SecondFragment : Fragment() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(
            requireContext()
        ).apply {
            setContent{


                val viewModel: AppViewModel = viewModel(requireActivity())

                val publication by viewModel.currentPublication.collectAsState()

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(publication.title) },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        parentFragmentManager.beginTransaction()
                                            .replace(R.id.fragment_container, MainFragment.newInstance())
                                            .commit()
                                    }) {
                                    Icon(Icons.Default.ArrowBack, contentDescription = "go back")
                                }
                            })
                    }
                ) {
                    MaterialTheme {
                        Screen(publication = publication)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SecondFragment()
    }
}

@Composable
fun Screen(modifier: Modifier = Modifier, publication: Publication) {
    Column(modifier = modifier.padding(top=64.dp)) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            ImageComponent(
                imageUrl = publication.imageUrl,
                modifier = Modifier.fillMaxWidth()
            ) {}
        }

        Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            ImageComponent(
                imageUrl = publication.owner.imageUrl,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {}
            Text(publication.owner.name, modifier=Modifier.padding(start=20.dp))
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {}) {
                Icon(Icons.Default.Add, "add")
            }
        }

        Column(modifier = Modifier.padding(20.dp, 30.dp, 20.dp, 20.dp)) {
            Text(text = publication.title, fontWeight = FontWeight.W500, fontSize = 20.sp)
            Text(text = publication.description)
        }
    }

}