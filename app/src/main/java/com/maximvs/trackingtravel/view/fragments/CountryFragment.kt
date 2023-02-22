package com.maximvs.trackingtravel.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.maximvs.trackingtravel.R
import com.maximvs.trackingtravel.databinding.FragmentCountryBinding
import com.maximvs.trackingtravel.viewmodel.CountryFragmentViewModel
import androidx.lifecycle.ViewModelProvider

class CountryFragment : Fragment() {
    private lateinit var binding: FragmentCountryBinding
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(CountryFragmentViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Слушаем какой у нас сейчас выбран вариант в настройках
        viewModel.countryPropertyLifeData.observe(viewLifecycleOwner, Observer<Int> {
            when(it) {
                ENGLAND_COUNTRY -> binding.radioGroup.check(R.id.btnSelectEngland)
                NETHERLANDS_COUNTRY -> binding.radioGroup.check(R.id.btnSelectNetherlands)
                MONTENEGRO_COUNTRY -> binding.radioGroup.check(R.id.btnSelectMontenegro)
            }
        })
        //Слушатель для отправки нового состояния в настройки
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.btnSelectEngland -> viewModel.putCountryProperty(2)
                R.id.btnSelectNetherlands -> viewModel.putCountryProperty(3)
                R.id.btnSelectMontenegro -> viewModel.putCountryProperty(1)
            }
        }
    }
    companion object {
        private const val ENGLAND_COUNTRY = 2
        private const val NETHERLANDS_COUNTRY = 3
        private const val MONTENEGRO_COUNTRY = 1
    }
}
