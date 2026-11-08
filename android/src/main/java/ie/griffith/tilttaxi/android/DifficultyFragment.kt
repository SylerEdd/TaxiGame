package ie.griffith.tilttaxi.android

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ie.griffith.tilttaxi.R
import ie.griffith.tilttaxi.databinding.FragmentDifficultyBinding

class DifficultyFragment : Fragment(R.layout.fragment_difficulty) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDifficultyBinding.bind(view)
        val preferences = requireContext()
            .getSharedPreferences("difficulty", Context.MODE_PRIVATE)

        if (savedInstanceState == null) {
            binding.mapSpinner.setSelection(
                preferences.getInt("map", 0).coerceIn(0, binding.mapSpinner.count - 1)
            )
            binding.trafficSpinner.setSelection(
                preferences.getInt("traffic", 0)
                    .coerceIn(0, binding.trafficSpinner.count - 1)
            )
        }

        binding.saveButton.setOnClickListener {
            preferences.edit()
                .putInt("map", binding.mapSpinner.selectedItemPosition)
                .putInt("traffic", binding.trafficSpinner.selectedItemPosition)
                .apply()

            parentFragmentManager.popBackStack()
        }
    }
}
