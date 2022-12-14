package com.kiwizitos.moviedatabase.features.episodeDetail.presentation

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.kiwizitos.moviedatabase.R
import com.kiwizitos.moviedatabase.databinding.FragmentEpisodeDetailBinding

class EpisodeDetailFragment : Fragment() {
    private var _binding: FragmentEpisodeDetailBinding? = null
    private val binding get() = _binding!!

    private val args: EpisodeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val details = args.episodeDetails

        binding.imgEpisodeImage.load(details.image)
        binding.txtEpisodeName.text = details.name
        binding.txtEpisodeSeason.text =
            getString(R.string.episode_season, details.season.toString())
        binding.txtEpisodeNumber.text =
            getString(R.string.episode_number, details.number.toString())
        binding.txtEpisodeResume.text = (Html.fromHtml(details.summary))
    }
}