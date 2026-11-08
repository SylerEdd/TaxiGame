package ie.griffith.tilttaxi.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ie.griffith.tilttaxi.R
import ie.griffith.tilttaxi.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        val preferences = requireContext()
            .getSharedPreferences("difficulty", Context.MODE_PRIVATE)

        val maps = resources.getStringArray(R.array.map_options)
        val trafficOptions = resources.getStringArray(R.array.traffic_options)
        val map = maps[preferences.getInt("map", 0).coerceIn(maps.indices)]
        val traffic = trafficOptions[
            preferences.getInt("traffic", 0).coerceIn(trafficOptions.indices)
        ]

        binding.selectionText.text = getString(
            R.string.home_selection_format,
            map,
            getString(R.string.difficulty_standard_taxi),
            traffic
        )

        binding.playButton.setOnClickListener {
            startActivity(Intent(requireContext(), AndroidLauncher::class.java))
        }

        binding.difficultyButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.screenContainer, DifficultyFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.settingsButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.screenContainer, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
