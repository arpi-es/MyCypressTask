package com.example.mycypresstask.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycypresstask.databinding.FragmentHomeBinding
import com.example.mycypresstask.ui.adapters.AlbumsAdapter
import com.example.mycypresstask.utils.Resource.*
import com.example.mycypresstask.utils.Result
import com.example.mycypresstask.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var binding: FragmentHomeBinding by autoCleared()
    private lateinit var albumAdapter: AlbumsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObserver()
    }

    private fun setupRecyclerView() {
        albumAdapter = AlbumsAdapter()
        binding.rvParent.layoutManager = LinearLayoutManager(requireContext())
        binding.rvParent.adapter = albumAdapter
    }


    private fun setupObserver() {
        viewModel.albums.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let { list ->
                        Log.i("MYTAG", list.toString())
                        albumAdapter.submitList(list)
                        list.forEach {

                        }
                    }
//                        loading.visibility = View.GONE
                }

                Result.Status.ERROR -> {
                    result.message?.let {
//                            showError(it)
                    }
//                        loading.visibility = View.GONE
                }

                Result.Status.LOADING -> {
//                        loading.visibility = View.VISIBLE
                }
            }
        })
    }
}