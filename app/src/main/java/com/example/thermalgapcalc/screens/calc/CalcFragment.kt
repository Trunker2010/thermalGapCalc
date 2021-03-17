package com.example.thermalgapcalc.screens.calc

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.SeekBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thermalgapcalc.EngineParamsTextWatcher
import com.example.thermalgapcalc.R
import com.example.thermalgapcalc.databinding.FragmentCalcBinding
import com.example.thermalgapcalc.databinding.ItemCylinder2ValveBinding
import com.example.thermalgapcalc.databinding.ItemCylinder4ValveBinding
import com.example.thermalgapcalc.databinding.ItemCylinder4ValvesBinding
import com.example.thermalgapcalc.models.Cylinder
import java.util.*


class CalcFragment : Fragment() {
    private var _binding: FragmentCalcBinding? = null
    private val binding get() = _binding!!
    private lateinit var calcViewModel: CalcViewModel
    private lateinit var inGapParamsTextWatcher: EngineParamsTextWatcher
    private lateinit var exGapParamsTextWatcher: EngineParamsTextWatcher
    private lateinit var inTolerancesTextWatcher: EngineParamsTextWatcher
    private lateinit var exTolerancesTextWatcher: EngineParamsTextWatcher
    private val adapter = CylinderRVAdapter()

    class CylinderRVAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var cylindersList = Collections.emptyList<Cylinder>()

        fun setCylinders(cylinders: List<Cylinder>) {
            cylindersList = cylinders
            notifyDataSetChanged()
        }


        class CylinderFourViewHolder(private val binding: ItemCylinder4ValvesBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(cylinder: Cylinder, position: Int) {
                with(binding) {
                    cylinderNumber.text = (position + 1).toString()
                }


            }
        }

        class CylinderTwoViewHolder(private val binding: ItemCylinder2ValveBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(cylinder: Cylinder) {
                with(binding) {
                    inFirstValveLabelTextView.text = cylinder.inValveList[0].gap.toString()
                    exFirstValveLabelTextView.text = cylinder.exValveList[0].gap.toString()
                }


            }
        }


        override fun getItemViewType(position: Int): Int {
            return when (cylindersList[position].exValveList.size) {
                1 -> {
                    R.layout.item_cylinder_2_valve


                }
                2 -> R.layout.item_cylinder_4_valves
                else -> -1
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            when (viewType) {
                R.layout.item_cylinder_4_valves -> {


                    return CylinderFourViewHolder(
                        ItemCylinder4ValvesBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )

                }
                else -> {

                    return CylinderTwoViewHolder(
                        ItemCylinder2ValveBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )
                }
            }
        }


        override fun getItemCount(): Int {
            return cylindersList.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is CylinderFourViewHolder -> {
                    holder.bind(cylindersList[position], position)
                }
                is CylinderTwoViewHolder -> {
                    holder.bind(cylindersList[position])
                }
            }
        }
    }

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
                        calcViewModel.valveCount = 2
                        calcViewModel.setExValve(2)
                        calcViewModel.setInValve(2)
                        binding.cylindersRv.layoutManager = GridLayoutManager(requireContext(),2,LinearLayoutManager.VERTICAL,false)

                    }
                    binding.radioValve4.id -> {
                        calcViewModel.valveCount = 4
                        calcViewModel.setExValve(4)
                        calcViewModel.setInValve(4)
                        binding.cylindersRv.layoutManager =
                            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                }
            }
            adapter.setCylinders(calcViewModel.engine.cylindersList)

        }


        if (calcViewModel.valveCount == 0) {
            binding.valvesRadioGroup.check(binding.radioValve2.id)
        }
        calcViewModel.setCylinderCount(binding.cylindersSeekBar.progress)
        addTextWatchers()




        binding.cylindersSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.cylindersSizeTextView.text = progress.toString()


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {


            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                calcViewModel.setCylinderCount(seekBar!!.progress)
                adapter.setCylinders(calcViewModel.engine.cylindersList)


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