package com.mobilebreakero.moviestask.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.navArgument
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobilebreakero.moviestask.R
import com.mobilebreakero.moviestask.data.dto.ResultsItem
import com.mobilebreakero.moviestask.databinding.MovieItemBinding
import com.mobilebreakero.moviestask.domain.model.MovieItem

class MoviesAdapter(private val context: Context) :
    ListAdapter<ResultsItem, MoviesAdapter.ViewHolder>(MoviesDiffCallBack()) {
    private lateinit var navController: NavController

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.binding.txvMovieItem.text = movie?.title
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500${movie?.posterPath}")
            .into(holder.binding.imvMovieItem)

        holder.itemView.setOnClickListener {
            navController = Navigation.findNavController(it)
            navController.navigate(
                R.id.action_homeFragment_to_detailsFragment, bundleOf(
                    "movieId" to movie?.id
                )
            )
        }
    }

    inner class ViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    class MoviesDiffCallBack : DiffUtil.ItemCallback<ResultsItem>() {
        override fun areItemsTheSame(
            oldItem: ResultsItem,
            newItem: ResultsItem,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResultsItem,
            newItem: ResultsItem,
        ): Boolean {
            return oldItem == newItem
        }

    }
}