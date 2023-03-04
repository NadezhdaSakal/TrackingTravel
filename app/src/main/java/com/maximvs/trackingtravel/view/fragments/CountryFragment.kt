package com.maximvs.trackingtravel.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.maximvs.trackingtravel.R
import com.maximvs.trackingtravel.databinding.FragmentCountryBinding
import com.maximvs.trackingtravel.viewmodel.CountryFragmentViewModel

class CountryFragment : Fragment() {

    companion object {
        private const val MONTENEGRO = 1
        private const val ENGLAND = 2
       // private const val NETHERLANDS = 3
    }

    private lateinit var binding: FragmentCountryBinding
    private val viewModel: CountryFragmentViewModel by viewModels()


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
        viewModel.countryPropertyLifeData.observe(viewLifecycleOwner) {
            when (it) {
                MONTENEGRO -> binding.radioGroup.check(R.id.btnSelectMontenegro)
                ENGLAND -> binding.radioGroup.check(R.id.btnSelectEngland)
               // NETHERLANDS -> binding.radioGroup.check(R.id.btnSelectNetherlands)
            }
        }
        //Слушатель для отправки нового состояния в настройки
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.btnSelectMontenegro -> viewModel.putCountryProperty(1)
                R.id.btnSelectEngland -> viewModel.putCountryProperty(2)
               // R.id.btnSelectNetherlands -> viewModel.putCountryProperty(3)
            }
        }
    }
}
