package com.example.thermalgapcalc.screens.params

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.thermalgapcalc.CylinderRVAdapter
import com.example.thermalgapcalc.TextWatchers.EngineParamsTextWatcher
import com.example.thermalgapcalc.databinding.EngineSettingsBarBinding
import com.example.thermalgapcalc.databinding.FragmentParamsBinding
import com.example.thermalgapcalc.models.engine.Engine.Companion.cylinderCount
import com.example.thermalgapcalc.models.engine.Engine.Companion.exGapParams
import com.example.thermalgapcalc.models.engine.Engine.Companion.exTolerances
import com.example.thermalgapcalc.models.engine.Engine.Companion.inGapParams
import com.example.thermalgapcalc.models.engine.Engine.Companion.inTolerances
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetParamsFragment : Fragment() {
    private var _binding: FragmentParamsBinding? = null
    private val binding get() = _binding!!
    val setParamsViewModel by viewModels<SetParamsViewModel>()
    private lateinit var inGapParamsTextWatcher: EngineParamsTextWatcher
    private lateinit var exGapParamsTextWatcher: EngineParamsTextWatcher
    private lateinit var inTolerancesTextWatcher: EngineParamsTextWatcher
    private lateinit var exTolerancesTextWatcher: EngineParamsTextWatcher
    private lateinit var engineSettingsBarBinding: EngineSettingsBarBinding
    private lateinit var navController: NavController
    private val adapter = CylinderRVAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        navController = findNavController()
        super.onCreate(savedInstanceState)
        initTextWatchers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentParamsBinding.inflate(inflater, container, false)
        initEngineSettingsBar()
        initRecyclerView()
        initFab()
        return binding.root
    }

    private fun initFab() {
        binding.floatingActionButton.setOnClickListener {
            navController.navigate(
                SetParamsFragmentDirections.actionSetParamsFragmentToCalculationsFragment(
                )
            )
        }
    }

    private fun initRecyclerView() {
        binding.cylindersRv.adapter = adapter
        adapter.setCylinders(setParamsViewModel.engine.cylindersList)
    }

    private fun initEngineSettingsBar() {
        engineSettingsBarBinding = binding.engineSettings
        engineSettingsBarBinding.apply {
            //колличество цилиндров
            cylindersSizeTextView.text =
                cylindersSeekBar.progress.toString()
            //колличество клапанов
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
            if (cylinderCount == 0) {
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
            inGapParamsEditText.setText(inGapParams.toString())
            exGapParamsEditText.setText(exGapParams.toString())
            inTolerancesEditText.setText(inTolerances.toString())
            exTolerancesEditText.setText(exTolerances.toString())
        }
    }
}