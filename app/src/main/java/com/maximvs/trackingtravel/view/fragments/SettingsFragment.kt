package com.maximvs.trackingtravel.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maximvs.trackingtravel.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() { // фрагмент для настроек
    private lateinit var binding: FragmentSettingsBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }
}
