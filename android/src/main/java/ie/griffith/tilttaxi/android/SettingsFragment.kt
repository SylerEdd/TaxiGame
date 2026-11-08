package ie.griffith.tilttaxi.android

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ie.griffith.tilttaxi.R
import ie.griffith.tilttaxi.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSettingsBinding.bind(view)
        val settings = SettingsStore(requireContext())

        if (savedInstanceState == null) {
            val steeringOption = if (settings.useTilt) {
                R.id.tiltOption
            } else {
                R.id.touchOption
            }

            binding.steeringGroup.check(steeringOption)
            binding.sensitivitySlider.progress = settings.sensitivityLevel
            binding.flipPedalsSwitch.isChecked = settings.flipPedals
        }

        binding.saveButton.setOnClickListener {
            settings.save(
                useTilt = binding.tiltOption.isChecked,
                sensitivityLevel = binding.sensitivitySlider.progress,
                flipPedals = binding.flipPedalsSwitch.isChecked
            )

            parentFragmentManager.popBackStack()
        }
    }
}
