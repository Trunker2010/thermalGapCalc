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
        initEngineSettingsBar()

        binding.cylindersRv.adapter = adapter
        adapter.setCylinders(setParamsViewModel.engine.cylindersList)
        return binding.root
    }

    private fun initEngineSettingsBar() {
        engineSettingsBarBinding = binding.engineSettings
        engineSettingsBarBinding.apply {
            cylindersSizeTextView.text =
                cylindersSeekBar.progress.toString()

            valvesRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                if (group.id == valvesRadioGroup.id) {
                    when (checkedId) {
                        radioValve2.id -> {
                            if (setParamsViewModel.valveCount != 2) {
                                setParamsViewModel.valveCount = 2
                                setParamsViewModel.updateCylinderCount(cylindersSeekBar.progress)
                            }
                        }
                        radioValve4.id -> {
                            if (setParamsViewModel.valveCount != 4) {
                                setParamsViewModel.valveCount = 4
                                setParamsViewModel.updateCylinderCount(cylindersSeekBar.progress)
                            }
                        }
                    }
                    adapter.setCylinders(setParamsViewModel.engine.cylindersList)
                }
            }

            if (setParamsViewModel.valveCount == 0) {
                valvesRadioGroup.check(radioValve2.id)
            }
            if (setParamsViewModel.cylinderCount == 0) {
                setParamsViewModel.updateCylinderCount(cylindersSeekBar.progress)
            }

            cylindersSeekBar.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    cylindersSizeTextView.text = progress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    setParamsViewModel.updateCylinderCount(seekBar!!.progress)
                    adapter.setCylinders(setParamsViewModel.engine.cylindersList)
                    adapter.notifyDataSetChanged()
                }
            })
        }
        initEngineSettingsBarEditTextParams()
        addEngineSettingsBarTextWatchers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initTextWatchers() {
        inGapParamsTextWatcher = EngineParamsTextWatcher { s ->
            setParamsViewModel.updateInGapParams(s.toString().toDouble())
        }
        exGapParamsTextWatcher = EngineParamsTextWatcher { s ->
            setParamsViewModel.updateExGapParams(s.toString().toDouble())
        }
        inTolerancesTextWatcher = EngineParamsTextWatcher { s ->
            setParamsViewModel.updateInTolerances(s.toString().toDouble())
        }
        exTolerancesTextWatcher = EngineParamsTextWatcher { s ->
            setParamsViewModel.updateExTolerances(s.toString().toDouble())
        }
    }

    private fun addEngineSettingsBarTextWatchers() {
        engineSettingsBarBinding.apply {
            inGapParamsEditText.addTextChangedListener(inGapParamsTextWatcher)
            exGapParamsEditText.addTextChangedListener(exGapParamsTextWatcher)
            inTolerancesEditText.addTextChangedListener(inTolerancesTextWatcher)
            exTolerancesEditText.addTextChangedListener(exTolerancesTextWatcher)
        }
    }

    private fun initEngineSettingsBarEditTextParams() {
        engineSettingsBarBinding.apply {
            inGapParamsEditText.setText(setParamsViewModel.inGapParams.toString())
            exGapParamsEditText.setText(setParamsViewModel.exGapParams.toString())
            inTolerancesEditText.setText(setParamsViewModel.inTolerances.toString())
            exTolerancesEditText.setText(setParamsViewModel.exTolerances.toString())
        }
    }
}