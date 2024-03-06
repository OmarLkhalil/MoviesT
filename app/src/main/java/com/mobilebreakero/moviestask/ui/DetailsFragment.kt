package com.mobilebreakero.moviestask.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.mobilebreakero.moviestask.R
import com.mobilebreakero.moviestask.databinding.MovieDetailBinding
import com.mobilebreakero.moviestask.domain.model.DetailsDto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.movie_detail) {

    private lateinit var binding: MovieDetailBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MovieDetailBinding.bind(view)

        val movieId = arguments?.getLong("movieId") ?: 1096197

        viewModel.getMovieDetails(movieId.toInt(), "en-US")

        lifecycleScope.launch {
            viewModel.detailsState.collect { detailsState ->
                if (!detailsState.isLoading && detailsState.error.isEmpty()) {
                    val details = detailsState.details
                    details?.let {
                        initDetails(it)
                    }
                } else if (detailsState.error.isNotEmpty()) {
                    // Handle error case
                }
            }
        }
    }

    private fun initDetails(movie: DetailsDto) {
        binding.apply {
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/${movie.posterPath}")
                .into(imagePoster)
            textTitle.text = movie.title
            textOverview.text = movie.overview
        }
    }

}
