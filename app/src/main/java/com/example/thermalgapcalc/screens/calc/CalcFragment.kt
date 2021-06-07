package com.example.thermalgapcalc.screens.calc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thermalgapcalc.CylinderRVAdapter
import com.example.thermalgapcalc.TextWatchers.EngineParamsTextWatcher
import com.example.thermalgapcalc.databinding.FragmentCalcBinding

class CalcFragment : Fragment() {
    private var _binding: FragmentCalcBinding? = null
    private val binding get() = _binding!!
    private lateinit var calcViewModel: CalcViewModel
    private lateinit var inGapParamsTextWatcher: EngineParamsTextWatcher
    private lateinit var exGapParamsTextWatcher: EngineParamsTextWatcher
    private lateinit var inTolerancesTextWatcher: EngineParamsTextWatcher
    private lateinit var exTolerancesTextWatcher: EngineParamsTextWatcher

    //111
    private val adapter = CylinderRVAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calcViewModel = ViewModelProvider(this).get(CalcViewModel::class.java)
        initTextWatchers()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCalcBinding.inflate(inflater, container, false)
        binding.cylindersSizeTextView.text = binding.cylindersSeekBar.progress.toString()


        binding.valvesRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (group.id == binding.valvesRadioGroup.id) {
                when (checkedId) {
                    binding.radioValve2.id -> {
                        if (calcViewModel.valveCount != 2) {
                            calcViewModel.valveCount = 2
                            calcViewModel.updateCylinderCount(binding.cylindersSeekBar.progress)
                        }

                    }
                    binding.radioValve4.id -> {
                        if (calcViewModel.valveCount != 4) {
                            calcViewModel.valveCount = 4
                            calcViewModel.updateCylinderCount(binding.cylindersSeekBar.progress)
                        }
                    }
                }
                adapter.setCylinders(calcViewModel.engine.cylindersList)
            }
        }

        if (calcViewModel.valveCount == 0) {
            binding.valvesRadioGroup.check(binding.radioValve2.id)
        }
        if (calcViewModel.cylinderCount == 0) {
            calcViewModel.updateCylinderCount(binding.cylindersSeekBar.progress)
        }
        addTextWatchers()

        binding.cylindersSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.cylindersSizeTextView.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {


            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                calcViewModel.updateCylinderCount(seekBar!!.progress)

                adapter.setCylinders(calcViewModel.engine.cylindersList)
                adapter.notifyDataSetChanged()


            }
        })

        binding.cylindersRv.adapter = adapter

        adapter.setCylinders(calcViewModel.engine.cylindersList)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initTextWatchers() {
        inGapParamsTextWatcher = EngineParamsTextWatcher { s ->
            if (s!!.isNotEmpty()) {
                calcViewModel.updateInGapParams(s.toString().toDouble())
            } else {
                calcViewModel.updateInGapParams(0.0)
            }
        }
        exGapParamsTextWatcher = EngineParamsTextWatcher { s ->
            if (s!!.isNotEmpty()) {
                calcViewModel.updateExGapParams(s.toString().toDouble())
            } else {
                calcViewModel.updateExGapParams(0.0)
            }
        }
        inTolerancesTextWatcher = EngineParamsTextWatcher { s ->
            if (s!!.isNotEmpty()) {
                calcViewModel.updateInTolerances(s.toString().toDouble())
            } else {
                calcViewModel.updateInTolerances(0.0)
            }

        }
        exTolerancesTextWatcher = EngineParamsTextWatcher { s ->
            if (s!!.isNotEmpty()) {
                calcViewModel.updateExTolerances(s.toString().toDouble())
            } else {
                calcViewModel.updateExTolerances(0.0)
            }
        }
    }

    private fun addTextWatchers() {
        binding.inGapParamsEditText.addTextChangedListener(inGapParamsTextWatcher)
        binding.exGapParamsEditText.addTextChangedListener(exGapParamsTextWatcher)
        binding.inTolerancesEditText.addTextChangedListener(inTolerancesTextWatcher)
        binding.exTolerancesEditText.addTextChangedListener(exTolerancesTextWatcher)
    }
}