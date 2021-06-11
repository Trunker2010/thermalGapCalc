package com.example.thermalgapcalc.screens.calculation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.thermalgapcalc.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculationsFragment : Fragment() {
    val viewModel by viewModels<CalculationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculations, container, false)
    }
}