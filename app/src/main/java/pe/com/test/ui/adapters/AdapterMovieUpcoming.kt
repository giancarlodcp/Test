package pe.com.test.ui.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.com.test.models.MovieUpcoming
import pe.com.test.R
import java.util.concurrent.Executors

class AdapterMovieUpcoming :
    RecyclerView.Adapter<AdapterMovieUpcoming.MovieUpcomingViewHolder>() {

    var data: List<MovieUpcoming?> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieUpcomingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_upcoming_item, parent, false)
        return MovieUpcomingViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieUpcomingViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item!!)
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

    class MovieUpcomingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movieUpcoming: MovieUpcoming) {
            val imageView = itemView.findViewById<ImageView>(R.id.posterImageView)
            val imageURL = "https://image.tmdb.org/t/p/w185/${movieUpcoming.posterPath}"
            Glide.with(itemView.context)
                .load(imageURL)
                .centerCrop()
                .into(imageView)
        }
    }

}