package com.example.playlistmaker.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.playlistmaker.App
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentSettingsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private val viewModel by viewModel<SettingViewModel>()
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var exitDialog:MaterialAlertDialogBuilder


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback { exitDialog.show() }
        exitDialog =
            MaterialAlertDialogBuilder(requireContext(), R.style.Theme_PlDelTr_Dialog_Alert)
                .setTitle(getString(R.string.exit_dialog))
                .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                    requireActivity().finish()

                }.setNegativeButton(R.string.no) { dialog, which ->

                }
        var currentMode = viewModel.getThemeLD()
        binding.SettingSwitchDarkTheme.isChecked = currentMode
        binding.SettingSwitchDarkTheme.setOnCheckedChangeListener { _, checked ->
            viewModel.changeThemeLD(checked)
        }

        viewModel.getThemeLiveData().observe(viewLifecycleOwner) { newMode ->
            if (newMode != currentMode) {
                (requireContext().applicationContext as App).switchTheme(newMode)
                currentMode = newMode
            }

        }

        binding.ShareButton.setOnClickListener {
            viewModel.shareApp()
        }

        binding.SupportButton.setOnClickListener {
            viewModel.writeSupport()
        }
        binding.UserCondButton.setOnClickListener {
            viewModel.openTerms()
        }
    }

}