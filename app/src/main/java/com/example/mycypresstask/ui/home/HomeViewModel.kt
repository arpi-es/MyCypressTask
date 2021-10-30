package com.example.mycypresstask.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycypresstask.data.repository.MainRepository
import com.example.mycypresstask.model.AlbumsItem
import com.example.mycypresstask.model.PhotosItem
import com.example.mycypresstask.utils.Result
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeViewModel @ViewModelInject constructor(private val repository: MainRepository) :
    ViewModel() {

    private val _albums = MutableLiveData<Result<List<AlbumsItem>>>()
    val albums = _albums

    private val _photos = HashMap<Int, List<PhotosItem>>()
    val hashMapPhotos = MutableLiveData<HashMap<Int, List<PhotosItem>>>() //Save PhotoAdapter Lists [Key:AlbumsItem.ID Value: List<PhotosItem>]

    private val compositeDisposable = CompositeDisposable()

    init {
        getCachedData() // for faster performance first get cached data. Can Be commented.
        fetchAlbums() // Get Data from Api
    }

    private fun getCachedData() {
        viewModelScope.launch {
            repository.getCachedAlbums().collect { result ->
                _albums.value = result
                result.data?.let { list ->
                    list.forEach {
                        repository.getCachedPhotos(it.id).collect { lstPhotos -> _photos[it.id] = lstPhotos }
                        }
                    }
                    hashMapPhotos.postValue(_photos)
                }
            }
        }

    private fun fetchAlbums() {
        viewModelScope.launch {
            repository.fetchAllAlbums().collect { result ->
                if (result != _albums.value) { // prevent from multiple call to same data

                    _albums.value = result

                    if (result.status == Result.Status.SUCCESS) {
                        result.data?.let { list -> fetchPhotos(list) }
                    }
                }
            }
        }
    }


    private fun fetchPhotos(lstAlbumItem: List<AlbumsItem>) {
        viewModelScope.launch {
            lstAlbumItem.forEach {

                repository.fetchAllPhotos(it.id).collect { result ->
                    result.data?.let { lstPhotos -> _photos[it.id] = lstPhotos }
                }
            }
            hashMapPhotos.postValue(_photos)
        }
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
