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
import com.example.thermalgapcalc.databinding.EngineSettingsBarBinding
import com.example.thermalgapcalc.databinding.FragmentCalcBinding

class SetParamsFragment : Fragment() {
    private var _binding: FragmentCalcBinding? = null
    private val binding get() = _binding!!
    private lateinit var setParamsViewModel: SetParamsViewModel
    private lateinit var inGapParamsTextWatcher: EngineParamsTextWatcher
    private lateinit var exGapParamsTextWatcher: EngineParamsTextWatcher
    private lateinit var inTolerancesTextWatcher: EngineParamsTextWatcher
    private lateinit var exTolerancesTextWatcher: EngineParamsTextWatcher
    private lateinit var engineSettingsBarBinding: EngineSettingsBarBinding
    private val adapter = CylinderRVAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setParamsViewModel = ViewModelProvider(this).get(SetParamsViewModel::class.java)
        initTextWatchers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCalcBinding.inflate(inflater, container, false)
        engineSettingsBarBinding = binding.engineSettings
        engineSettingsBarBinding.cylindersSizeTextView.text = engineSettingsBarBinding.cylindersSeekBar.progress.toString()

        engineSettingsBarBinding.valvesRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (group.id == engineSettingsBarBinding.valvesRadioGroup.id) {
                when (checkedId) {
                    engineSettingsBarBinding.radioValve2.id -> {
                        if (setParamsViewModel.valveCount != 2) {
                            setParamsViewModel.valveCount = 2
                            setParamsViewModel.updateCylinderCount(engineSettingsBarBinding.cylindersSeekBar.progress)
                        }
                    }
                    engineSettingsBarBinding.radioValve4.id -> {
                        if (setParamsViewModel.valveCount != 4) {
                            setParamsViewModel.valveCount = 4
                            setParamsViewModel.updateCylinderCount(engineSettingsBarBinding.cylindersSeekBar.progress)
                        }
                    }
                }
                adapter.setCylinders(setParamsViewModel.engine.cylindersList)
            }
        }

        if (setParamsViewModel.valveCount == 0) {
            engineSettingsBarBinding.valvesRadioGroup.check(engineSettingsBarBinding.radioValve2.id)
        }
        if (setParamsViewModel.cylinderCount == 0) {
            setParamsViewModel.updateCylinderCount(engineSettingsBarBinding.cylindersSeekBar.progress)
        }
        addTextWatchers()

        engineSettingsBarBinding.cylindersSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                engineSettingsBarBinding.cylindersSizeTextView.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                setParamsViewModel.updateCylinderCount(seekBar!!.progress)
                adapter.setCylinders(setParamsViewModel.engine.cylindersList)
                adapter.notifyDataSetChanged()
            }
        })

        binding.cylindersRv.adapter = adapter
        adapter.setCylinders(setParamsViewModel.engine.cylindersList)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initTextWatchers() {
        inGapParamsTextWatcher = EngineParamsTextWatcher { s ->
            if (s!!.isNotEmpty()) {
                setParamsViewModel.updateInGapParams(s.toString().toDouble())
            } else {
                setParamsViewModel.updateInGapParams(0.0)
            }
        }
        exGapParamsTextWatcher = EngineParamsTextWatcher { s ->
            if (s!!.isNotEmpty()) {
                setParamsViewModel.updateExGapParams(s.toString().toDouble())
            } else {
                setParamsViewModel.updateExGapParams(0.0)
            }
        }
        inTolerancesTextWatcher = EngineParamsTextWatcher { s ->
            if (s!!.isNotEmpty()) {
                setParamsViewModel.updateInTolerances(s.toString().toDouble())
            } else {
                setParamsViewModel.updateInTolerances(0.0)
            }

        }
        exTolerancesTextWatcher = EngineParamsTextWatcher { s ->
            if (s!!.isNotEmpty()) {
                setParamsViewModel.updateExTolerances(s.toString().toDouble())
            } else {
                setParamsViewModel.updateExTolerances(0.0)
            }
        }
    }

    private fun addTextWatchers() {
        engineSettingsBarBinding.inGapParamsEditText.addTextChangedListener(inGapParamsTextWatcher)
        engineSettingsBarBinding.exGapParamsEditText.addTextChangedListener(exGapParamsTextWatcher)
        engineSettingsBarBinding.inTolerancesEditText.addTextChangedListener(inTolerancesTextWatcher)
        engineSettingsBarBinding.exTolerancesEditText.addTextChangedListener(exTolerancesTextWatcher)
    }
}