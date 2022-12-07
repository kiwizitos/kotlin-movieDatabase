package com.kiwizitos.moviedatabase.features.detail.presentation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kiwizitos.moviedatabase.R
import com.kiwizitos.moviedatabase.databinding.FragmentDetailPageBinding
import com.kiwizitos.moviedatabase.databinding.ShowEpisodeCellBinding
import com.kiwizitos.moviedatabase.features.detail.domain.entities.ShowEpisodeCell
import io.github.enicolas.genericadapter.AdapterHolderType
import io.github.enicolas.genericadapter.adapter.GenericRecyclerAdapter
import io.github.enicolas.genericadapter.adapter.GenericRecylerAdapterDelegate


class DetailPageFragment : Fragment() {

    private var _binding: FragmentDetailPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailPageViewModel by viewModels()
    private val args: DetailPageFragmentArgs by navArgs()
    private val adapter = GenericRecyclerAdapter()

    private var isExpanded = false

    private var recyclerViewAdapter = object : GenericRecylerAdapterDelegate {
        override fun numberOfRows(adapter: GenericRecyclerAdapter): Int {
            return viewModel.episodesList.size
        }

        override fun cellForPosition(
            adapter: GenericRecyclerAdapter,
            cell: RecyclerView.ViewHolder,
            position: Int
        ) {
            val episode = viewModel.episodesList[position]
            (cell as? ShowEpisodeCell)?.set(
                id = episode.id,
                name = episode.name,
                season = episode.season,
                number = episode.number,
                image = episode.image
            )
        }

        override fun registerCellAtPosition(
            adapter: GenericRecyclerAdapter,
            position: Int
        ): AdapterHolderType {
            return AdapterHolderType(
                ShowEpisodeCellBinding::class.java,
                ShowEpisodeCell::class.java,
                0
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupFragment()
    }

    private fun expandableText() {
        if (isExpanded) {
            binding.showSummaryButton.animate().alpha(1.0f).duration = 375
            val textAnimation = ObjectAnimator.ofInt(
                binding.showSummary,
                "maxLines",
                3
            )
            textAnimation.duration = 375
            textAnimation.start()
            binding.showSummary.ellipsize = TextUtils.TruncateAt.END
        } else {
            binding.showSummaryButton.animate().alpha(0.0f).duration = 375
            val textAnimation = ObjectAnimator.ofInt(
                binding.showSummary,
                "maxLines",
                999
            )
            textAnimation.duration = 375
            textAnimation.start()
            binding.showSummary.ellipsize = null
        }
        isExpanded = !isExpanded
    }

    private fun setupFragment() {
        val details = args.showDetails

        binding.showName.text = details.name
        binding.showImage.load(details.image)
        binding.showSummary.text = (Html.fromHtml(details.summary))

        binding.showSummary.setOnClickListener { expandableText() }
        binding.showSummaryButton.setOnClickListener { expandableText() }

        details.genres.getOrNull(0).let {
            binding.showGenre1.text = it.toString()
            binding.showGenre1.isVisible = it != null
        }
        details.genres.getOrNull(1).let {
            binding.showGenre2.text = it.toString()
            binding.showGenre2.isVisible = it != null
        }
        details.genres.getOrNull(2).let {
            binding.showGenre3.text = it.toString()
            binding.showGenre3.isVisible = it != null
        }

        setupRecyclerView()
        fetchData()
    }

    private fun setupRecyclerView() {
        adapter.delegate = recyclerViewAdapter
        binding.rcvEpisodes.adapter = adapter
        adapter.snapshot?.snapshotList = emptyList()
    }

    private fun setupSpinner(seasonList: List<String>) {
        binding.spnSeasons.adapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            seasonList
        )

        binding.spnSeasons.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectSeason(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }
    }

    private fun fetchData() {
        viewModel.getEpisodesResponse(id = args.showDetails.id).observe(viewLifecycleOwner) {
            adapter.snapshot?.snapshotList = it
            adapter.notifyDataSetChanged()
        }
        viewModel.getSeasonsResponse(id = args.showDetails.id)
            .observe(viewLifecycleOwner) { seasons ->
                adapter.snapshot?.snapshotList = seasons
                adapter.notifyDataSetChanged()

                val seasonList =
                    seasons.map { getString(R.string.show_season, it.number.toString()) }
                setupSpinner(seasonList)
            }
    }

    fun selectSeason(index: Int) {
        val season = viewModel.seasonsList[index]
        viewModel.getEpisodesResponse(id = season.id).observe(viewLifecycleOwner) {
            adapter.snapshot?.snapshotList = it
            adapter.notifyDataSetChanged()
        }
    }
}