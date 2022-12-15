package com.kiwizitos.moviedatabase.features.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kiwizitos.moviedatabase.databinding.FragmentHomeBinding
import com.kiwizitos.moviedatabase.databinding.ShowCellBinding
import com.kiwizitos.moviedatabase.features.detail.domain.entities.ShowCell
import com.kiwizitos.moviedatabase.features.detail.domain.entities.ShowEntity
import io.github.enicolas.genericadapter.AdapterHolderType
import io.github.enicolas.genericadapter.adapter.GenericRecyclerAdapter
import io.github.enicolas.genericadapter.adapter.GenericRecylerAdapterDelegate

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = GenericRecyclerAdapter()

    val searchListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean { return false }

        override fun onQueryTextSubmit(query: String?): Boolean {
            viewModel.getSearch(query ?: "").observe(viewLifecycleOwner) {
                updateList(it ?: emptyList())
            }
            return true
        }
    }

    private var recyclerViewAdapter = object : GenericRecylerAdapterDelegate {

        override fun numberOfRows(adapter: GenericRecyclerAdapter): Int {
            return viewModel.showList.size
        }

        override fun cellForPosition(
            adapter: GenericRecyclerAdapter,
            cell: RecyclerView.ViewHolder,
            position: Int
        ) {
            val show = viewModel.showList[position]
            (cell as? ShowCell)?.set(
                id = show.id,
                name = show.name,
                summary = show.summary ?: "Sem informações",
                image = show.image,
                genres = show.genres,
                rating = show.rating?.toString() ?: "N/A"
            )
        }

        override fun registerCellAtPosition(
            adapter: GenericRecyclerAdapter,
            position: Int
        ): AdapterHolderType {
            return AdapterHolderType(
                ShowCellBinding::class.java,
                ShowCell::class.java,
                0
            )
        }

        override fun didSelectItemAtIndex(adapter: GenericRecyclerAdapter, index: Int) {
            val details = viewModel.showList[index]
            val direction = HomeFragmentDirections.actionHomeFragmentToDetailPageFragment(details)
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupFragment()
    }

    private fun setupFragment() {
        setupRecyclerView()
        fetchData()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        adapter.delegate = recyclerViewAdapter
        binding.recyclerView.adapter = adapter
        adapter.snapshot?.snapshotList = emptyList()
    }

    private fun setupSearchView() {
        binding.srvShow.setOnQueryTextListener(
            searchListener
        )
        binding.srvShow.setOnCloseListener {
            fetchData()
            true
        }
    }

    private fun fetchData() {
        viewModel.getResponse().observe(viewLifecycleOwner) {
            updateList(it)
        }
    }

    private fun updateList(items: List<ShowEntity>) {
        adapter.snapshot?.snapshotList = items
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}