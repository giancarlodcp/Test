package pe.com.test.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import pe.com.test.R
import java.util.concurrent.Executors

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tv = view.findViewById<TextView>(R.id.textViewName)
        tv.text = arguments?.getString("title")

        val tvd = view.findViewById<TextView>(R.id.textViewDescription)
        tvd.text = arguments?.getString("overview")

        val img = view.findViewById<ImageView>(R.id.imageViewPoster)

        val imageViewBackground = view.findViewById<ImageView>(R.id.imageViewBackground)
        val imageBackgroundURL = "https://image.tmdb.org/t/p/w500/${arguments?.getString("backdrop_path")}"
        Glide.with(requireContext())
            .load(imageBackgroundURL)
            .centerCrop()
            .into(imageViewBackground)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null
        executor.execute {
            val imageURL = "https://image.tmdb.org/t/p/w185/${arguments?.getString("posterPath")}"
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
                handler.post {
                    img.setImageBitmap(image)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}