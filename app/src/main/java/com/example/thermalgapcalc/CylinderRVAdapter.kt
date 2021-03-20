package com.example.thermalgapcalc

import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thermalgapcalc.databinding.ItemCylinder2ValveBinding
import com.example.thermalgapcalc.databinding.ItemCylinder4ValvesBinding
import com.example.thermalgapcalc.models.Cylinder
import java.util.*

//interface twoValveTextChangeCallbacks {
//    fun warpEx(charSequence: CharSequence?, cylinder: Cylinder)
//    fun gapEx(charSequence: CharSequence?, cylinder: Cylinder)
//    fun warpIn(charSequence: CharSequence?, cylinder: Cylinder)
//    fun gapIn(charSequence: CharSequence?, cylinder: Cylinder)
//}

class CylinderRVAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var cylindersList = Collections.emptyList<Cylinder>()

    fun setCylinders(cylinders: List<Cylinder>) {
        cylindersList = cylinders
        notifyDataSetChanged()
    }

    //4 клапана
    class CylinderFourViewHolder(private val binding: ItemCylinder4ValvesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cylinder: Cylinder, position: Int) {
            val cylinderN = position + 1
            with(binding) {
                cylinderNumber.text = cylinderN.toString()

                exGap1EditText.text =
                    SpannableStringBuilder(cylinder.exValveList[0].measuredGap.toString())
                exGap2EditText.text =
                    SpannableStringBuilder(cylinder.exValveList[1].measuredGap.toString())
                exInstalledWasher1EditText.text =
                    SpannableStringBuilder(cylinder.exValveList[0].washer.toString())
                exInstalledWasher2EditText.text =
                    SpannableStringBuilder(cylinder.exValveList[1].washer.toString())

                inInstalledWasher1EditText.text =
                    SpannableStringBuilder(cylinder.inValveList[0].washer.toString())
                inInstalledWasher2EditText.text =
                    SpannableStringBuilder(cylinder.inValveList[1].washer.toString())
                inGap1EditText.text =
                    SpannableStringBuilder(cylinder.inValveList[0].measuredGap.toString())
                inGap2EditText.text =
                    SpannableStringBuilder(cylinder.inValveList[1].measuredGap.toString())

                //зазор выпускной 2
                exGap2EditText.addTextChangedListener(EngineParamsTextWatcher { gap ->
                    cylinder.exValveList[1].measuredGap =
                        if (gap!!.isNotEmpty()) gap.toString().toDouble() else 0.0

                })

                //зазор выпускной 1
                exGap1EditText.addTextChangedListener(EngineParamsTextWatcher { gap ->
                    Log.d("exGap1EditText", cylinder.exValveList.size.toString())
                    cylinder.exValveList[0].measuredGap =
                        if (gap!!.isNotEmpty()) gap.toString().toDouble() else 0.0

                })

                //шайба выпускная 2
                exInstalledWasher2EditText.addTextChangedListener(EngineParamsTextWatcher { washer ->
                    cylinder.exValveList[1].washer =
                        if (washer!!.isNotEmpty()) washer.toString().toDouble() else 0.0
                })

                //шайба выпускная 1
                exInstalledWasher1EditText.addTextChangedListener(EngineParamsTextWatcher { washer ->
                    cylinder.exValveList[0].washer =
                        if (washer!!.isNotEmpty()) washer.toString().toDouble() else 0.0

                })


                //шайба впускная 2
                inInstalledWasher2EditText.addTextChangedListener(EngineParamsTextWatcher { washer ->
                    cylinder.inValveList[1].washer =
                        if (washer!!.isNotEmpty()) washer.toString().toDouble() else 0.0
                })

                //шайба впускная 1
                inInstalledWasher1EditText.addTextChangedListener(EngineParamsTextWatcher { washer ->
                    cylinder.inValveList[0].washer =
                        if (washer!!.isNotEmpty()) washer.toString().toDouble() else 0.0
                })

                //зазор впускной 2
                inGap2EditText.addTextChangedListener(EngineParamsTextWatcher { gap ->
                    cylinder.inValveList[1].measuredGap =
                        if (gap!!.isNotEmpty()) gap.toString().toDouble() else 0.0
                })
                //зазор впускной 1
                inGap1EditText.addTextChangedListener(EngineParamsTextWatcher { gap ->
                    cylinder.inValveList[0].measuredGap =
                        if (gap!!.isNotEmpty()) gap.toString().toDouble() else 0.0
                })


            }


        }
    }

    //два клапана на цилиндр
    class CylinderTwoViewHolder(private val binding: ItemCylinder2ValveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            cylinder: Cylinder,
            position: Int,

            ) {
            with(binding) {

                exGapEditText.text =
                    SpannableStringBuilder(cylinder.exValveList[0].measuredGap.toString())
                inGapEditText.text =
                    SpannableStringBuilder(cylinder.inValveList[0].measuredGap.toString())

                exInstalledWasherEditText.text =
                    SpannableStringBuilder(cylinder.exValveList[0].washer.toString())
                inInstalledWasher1EditText.text =
                    SpannableStringBuilder(cylinder.inValveList[0].washer.toString())


                //зазор выпускной
                exGapEditText.addTextChangedListener(EngineParamsTextWatcher { exGapSize ->
                    cylinder.exValveList[0].measuredGap =
                        if (exGapSize!!.isNotEmpty()) exGapSize.toString().toDouble() else 0.0
                })
                //шайба выпускная
                exInstalledWasherEditText.addTextChangedListener(EngineParamsTextWatcher { exWasher ->
                    cylinder.exValveList[0].washer =
                        if (exWasher!!.isNotEmpty()) exWasher.toString().toDouble() else 0.0
                })

                //зазор впускной
                inGapEditText.addTextChangedListener(EngineParamsTextWatcher { inGapSize ->
                    cylinder.inValveList[0].measuredGap =
                        if (inGapSize!!.isNotEmpty()) inGapSize.toString().toDouble() else 0.0
                })
                //шайбы впуск
                inInstalledWasher1EditText.addTextChangedListener(EngineParamsTextWatcher { inWasher ->
                    cylinder.inValveList[0].washer =
                        if (inWasher!!.isNotEmpty()) inWasher.toString().toDouble() else 0.0


                })

                cylinderNumber.text = (position + 1).toString()


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
                holder.bind(cylindersList[position], position)

            }
        }
    }
}

