package com.example.myapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.data.Group
import com.example.myapp.data.Publication
import com.example.myapp.data.RepositoryInstance
import com.example.myapp.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel: ViewModel() {
    private val _publications = MutableStateFlow(listOf<Publication>())
    private val _group = MutableStateFlow(RepositoryInstance.blankGroup)

    val publications: StateFlow<List<Publication>> = _publications.asStateFlow()
    val group  = _group.asStateFlow()

    private val _currentPublication = MutableStateFlow(RepositoryInstance.blankPublication)

    val currentPublication = _currentPublication.asStateFlow()

    fun setGroup(groupId: Int) {
        viewModelScope.launch {
            _group.update { RepositoryInstance.getGroup(groupId) }
        }
    }

    fun getPublications() {
       viewModelScope.launch {
           _publications.update {
               RepositoryInstance.getPublications(_group.value.id)
           }
       }
    }

    fun setCurrentPublication(pubId: Int) {
        viewModelScope.launch {
           _currentPublication.update {
                RepositoryInstance.getPublication(pubId)
           }
        }
    }

}