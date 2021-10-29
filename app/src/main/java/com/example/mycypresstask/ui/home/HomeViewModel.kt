package com.example.mycypresstask.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycypresstask.data.repository.MainRepository
import com.example.mycypresstask.model.AlbumsItem
import com.example.mycypresstask.utils.Result
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeViewModel @ViewModelInject constructor(private val repository: MainRepository) : ViewModel() {

    private val _albums = MutableLiveData<Result<List<AlbumsItem>>>()
    val albums = _albums

    private val compositeDisposable = CompositeDisposable()

    init {
        fetchAlbums()
    }

    private fun fetchAlbums() {
        viewModelScope.launch {
            repository.fetchAllAlbums().collect {
                _albums.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
