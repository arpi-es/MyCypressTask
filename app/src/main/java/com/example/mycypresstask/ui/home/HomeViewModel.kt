package com.example.mycypresstask.ui.home

import android.util.Log
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
    val hashMapPhotos = MutableLiveData<HashMap<Int, List<PhotosItem>>>() // To Save PhotoAdapter List

    private val compositeDisposable = CompositeDisposable()

    init {
        getCachedData() // for faster performance first get cached data
        fetchAlbums() // Get Data from Api
    }

    private fun getCachedData() {
        viewModelScope.launch {
            repository.getAllAlbumsCached().collect { result ->
                _albums.value = result
                result.data?.let { list ->
                    list.forEach {
                        repository.getAllPhotosCached(it.id).collect { lstPhotos ->
                                _photos[it.id] = lstPhotos
//                                hashMapPhotos.postValue(_photos)
                            }
                        }
                    }
                    hashMapPhotos.postValue(_photos)
                }
            }
         fetchAlbums() // Get Data from Api
        }







//    private fun fetchAlbums() {
//        viewModelScope.launch {
//            repository.fetchAllAlbums().collect { result->
//
////                val number = result.takeUnless1 { result != _albums.value)
//                _albums.value = result
//                 if (result.status == Result.Status.SUCCESS ) {
//                     result.data?.let { list ->
//
//                         list.forEach {
//                             repository.fetchAllPhotos(it.id).collect { result->
//                                 Log.i("MYTAG", result.toString())
//                         }
//                     }
//
//                    }
//                }
//
//            }
//        }
//    }


    private fun fetchAlbums() {
        viewModelScope.launch {
            repository.fetchAllAlbums().collect { result ->
                if (result != _albums.value) { // prevent from multiple call to same data

                    Log.i("MYTAG5", "HEREEE")
                    _albums.value = result
                    if (result.status == Result.Status.SUCCESS) {
                        result.data?.let { list ->
                            fetchPhotos(list)
                        }
                    }
                }
            }
        }
    }


    private fun fetchPhotos(lstAlbumItem: List<AlbumsItem>) {
        viewModelScope.launch {
            lstAlbumItem.forEach {

                repository.fetchAllPhotos(it.id).collect { result ->
                    result.data?.let { lstPhotos ->
                        _photos[it.id] = lstPhotos
                    }
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
