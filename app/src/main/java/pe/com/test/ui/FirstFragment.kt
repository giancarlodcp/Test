package pe.com.test.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import pe.com.test.MoviesApplication
import pe.com.test.ui.adapters.AdapterMovieUpcoming
import pe.com.test.ui.adapters.MoviePopularAdapter
import pe.com.test.databinding.FragmentFirstBinding
import pe.com.test.viewmodels.MyViewModel

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    var view_model: MyViewModel? = null
    var adapterUpcoming = AdapterMovieUpcoming()
    lateinit var moviePopularAdapter: MoviePopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** INIT Views */
        moviePopularAdapter = MoviePopularAdapter()
        binding.moviePopularRecyclerView.adapter = moviePopularAdapter
        binding.moviePopularRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        val repository = (requireActivity().application as MoviesApplication).repository
        view_model = MyViewModel(requireActivity().application, repository)

        view_model!!.allPopularMovies.observe(viewLifecycleOwner){
            it.let {
                moviePopularAdapter.addAll(it)
                moviePopularAdapter.notifyDataSetChanged()
            }
        }

        view_model!!.error.observe(viewLifecycleOwner) { error ->
            Snackbar.make(binding.baseView, error, Snackbar.LENGTH_LONG).show()
        }

        view_model!!.movieUpcoming.observe(viewLifecycleOwner) { follow ->
            adapterUpcoming.data = follow
            binding.movieUpcomingRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            binding.movieUpcomingRecyclerView.adapter = adapterUpcoming
        }

        view_model!!.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}