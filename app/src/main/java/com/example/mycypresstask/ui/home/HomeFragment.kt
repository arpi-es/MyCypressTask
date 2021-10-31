package com.example.mycypresstask.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycypresstask.databinding.FragmentHomeBinding
import com.example.mycypresstask.ui.adapters.AlbumsAdapter
import com.example.mycypresstask.utils.Result
import com.example.mycypresstask.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var binding: FragmentHomeBinding by autoCleared()


    @Inject
    lateinit var albumAdapter: AlbumsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObserver()
    }

    private fun setupRecyclerView() {

        binding.rvParent.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL, false)
        binding.rvParent.adapter = albumAdapter
    }

    private fun setupObserver() {
        viewModel.albums.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let { list ->
                        albumAdapter.submitList(list)
                    }

                    binding.loading.visibility = View.GONE
                }

                Result.Status.ERROR -> {
//                    result.message?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() } //
                    binding.loading.visibility = View.GONE
                }

                Result.Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                }
            }
        })



        viewModel.hashMapPhotos.observe(viewLifecycleOwner, Observer {
            if (albumAdapter.photos != it) {
                albumAdapter.photos = it
                albumAdapter.notifyDataSetChanged()
            }
        })
    }
}
