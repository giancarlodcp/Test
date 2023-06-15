package pe.com.test.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.com.test.models.MoviePopular
import pe.com.test.R

class MoviePopularAdapter : RecyclerView.Adapter<MoviePopularAdapter.MoviePopularViewHolder>() {

    private val moviePopular = ArrayList<MoviePopular>()

    fun addAll (list: List<MoviePopular>){
        moviePopular.clear()
        moviePopular.addAll(list)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePopularViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_popular, parent, false)
        return MoviePopularViewHolder(view)
    }

    override fun getItemCount(): Int = moviePopular.size

    override fun onBindViewHolder(holder: MoviePopularViewHolder, position: Int) {
        val item = moviePopular[position]
        holder.bind(item)
        val bundle = bundleOf(
            "title" to item.title,
            "backdrop_path" to item.backdropPath,
            "posterPath" to item.posterPath,
            "overview" to item.overview
        )
        holder.itemView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_FirstFragment_to_DetailFragment, bundle)
        }
    }

    class MoviePopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(moviePopular: MoviePopular) {
            val imageView = itemView.findViewById<ImageView>(R.id.posterImageView)
            val imageURL = "https://image.tmdb.org/t/p/w185/${moviePopular.posterPath}"
            Glide.with(itemView.context)
                .load(imageURL)
                .centerCrop()
                .into(imageView)

        }
    }
}