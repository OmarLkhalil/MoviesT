package com.mobilebreakero.moviestask.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobilebreakero.moviestask.R
import com.mobilebreakero.moviestask.data.dto.ResultsItem
import com.mobilebreakero.moviestask.databinding.FragmentHomeBinding
import com.mobilebreakero.moviestask.domain.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var adapter: MoviesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val context = requireContext()
        adapter = MoviesAdapter(context)

        binding.moviesRec.apply {
            layoutManager = LinearLayoutManager(context)
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        binding.latestRec.apply {
            layoutManager = LinearLayoutManager(context)
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        binding.moviesRec.adapter = adapter
        binding.latestRec.adapter = adapter

        lifecycleScope.launch {
            viewModel.moviesCached.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is DataState.Loading -> {
                        // Show loading indicator
                    }
                    is DataState.Success -> {
                        adapter?.submitList(result.data as MutableList<ResultsItem>)
                        // Hide loading indicator
                    }
                    is DataState.Error -> {
                        // Handle error: display error message, retry button, etc.
                        Toast.makeText(requireContext(), "Error: ${result.message}", Toast.LENGTH_LONG).show()
                    }

                    else -> {}
                }
            }
        }
    }
}