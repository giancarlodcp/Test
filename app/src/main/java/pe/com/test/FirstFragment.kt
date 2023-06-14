package pe.com.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import pe.com.test.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var binding: FragmentFirstBinding? = null
    var view_model: MyViewModel? = null
    var adapterUpcoming = AdapterMovieUpcoming()
    lateinit var moviePopularAdapter: MoviePopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(inflater, container, false)

        view_model = MyViewModel(requireActivity().application)

        view_model!!.data.observe(viewLifecycleOwner) { user ->
            moviePopularAdapter = MoviePopularAdapter(user)
            binding?.moviePopularRecyclerView?.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            binding?.moviePopularRecyclerView!!.adapter = moviePopularAdapter
        }

        view_model!!.error.observe(viewLifecycleOwner) { error ->
            Snackbar.make(binding!!.baseView, error, Snackbar.LENGTH_LONG).show()
        }

        view_model!!.movieUpcoming.observe(viewLifecycleOwner) { follow ->
            adapterUpcoming.data = follow
            binding?.movieUpcomingRecyclerView?.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            binding?.movieUpcomingRecyclerView!!.adapter = adapterUpcoming
        }

        view_model!!.data()

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}