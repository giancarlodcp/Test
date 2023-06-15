package pe.com.test.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import pe.com.test.MoviesApplication
import pe.com.test.databinding.FragmentFirstBinding
import pe.com.test.ui.adapters.AdapterMovieUpcoming
import pe.com.test.ui.adapters.MoviePopularAdapter
import pe.com.test.viewmodels.MyViewModel

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private var viewModel: MyViewModel? = null
    private var adapterUpcoming = AdapterMovieUpcoming()
    private var moviePopularAdapter = MoviePopularAdapter()

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

        initViews()
        val repository = (requireActivity().application as MoviesApplication).repository
        viewModel = MyViewModel(requireActivity().application, repository)

        viewModel!!.allPopularMovies.observe(viewLifecycleOwner){ list->
            moviePopularAdapter.addAll(list)
            moviePopularAdapter.notifyDataSetChanged()
        }

        viewModel!!.allUpcomingMovies.observe(viewLifecycleOwner) { follow ->
            adapterUpcoming.data = follow
            adapterUpcoming.notifyDataSetChanged()
        }

        viewModel!!.error.observe(viewLifecycleOwner) { error ->
            Snackbar.make(binding.baseView, error, Snackbar.LENGTH_LONG).show()
        }

        viewModel!!.init()
    }

    private fun initViews(){
        /** INIT Views */
        binding.moviePopularRecyclerView.adapter = moviePopularAdapter
        binding.moviePopularRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.movieUpcomingRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.movieUpcomingRecyclerView.adapter = adapterUpcoming
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}