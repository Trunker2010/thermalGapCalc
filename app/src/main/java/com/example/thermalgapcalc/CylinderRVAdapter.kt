package com.example.thermalgapcalc

import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thermalgapcalc.TextWatchers.CylinderParamsTextWatcher
import com.example.thermalgapcalc.databinding.ItemCylinder2ValveBinding
import com.example.thermalgapcalc.databinding.ItemCylinder4ValvesBinding
import com.example.thermalgapcalc.models.engine.Cylinder
import java.util.*

class CylinderRVAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var cylindersList = Collections.emptyList<Cylinder>()

    fun setCylinders(cylinders: List<Cylinder>) {
        cylindersList = cylinders
        notifyDataSetChanged()
    }

    //4 клапана
    class CylinderFourViewHolder(val binding: ItemCylinder4ValvesBinding) :
        RecyclerView.ViewHolder(binding.root), ListensTextChanges {
        lateinit var cParamsTextWatcher: CylinderParamsTextWatcher
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

            }
        }

        override fun updatePosition(pos: Int) {
            cParamsTextWatcher.updatePosition(pos)
        }

        override fun setTextWatcher(cylinderParamsTextWatcher: CylinderParamsTextWatcher) {
            cParamsTextWatcher = cylinderParamsTextWatcher
        }

        override fun enableTextWatcher() {
            with(binding) {
                //зазор выпускной 2
                exGap2EditText.addTextChangedListener(cParamsTextWatcher)

                //зазор выпускной 1
                exGap1EditText.addTextChangedListener(cParamsTextWatcher)

                //шайба выпускная 2
                exInstalledWasher2EditText.addTextChangedListener(cParamsTextWatcher)

                //шайба выпускная 1
                exInstalledWasher1EditText.addTextChangedListener(cParamsTextWatcher)

                //шайба впускная 2
                inInstalledWasher2EditText.addTextChangedListener(cParamsTextWatcher)

                //шайба впускная 1
                inInstalledWasher1EditText.addTextChangedListener(cParamsTextWatcher)

                //зазор впускной 2
                inGap2EditText.addTextChangedListener(cParamsTextWatcher)
                //зазор впускной 1
                inGap1EditText.addTextChangedListener(cParamsTextWatcher)
            }
        }

        override fun disableTextWatcher() {
            with(binding) {
                //зазор выпускной 2
                exGap2EditText.removeTextChangedListener(cParamsTextWatcher)

                //зазор выпускной 1
                exGap1EditText.removeTextChangedListener(cParamsTextWatcher)

                //шайба выпускная 2
                exInstalledWasher2EditText.removeTextChangedListener(cParamsTextWatcher)

                //шайба выпускная 1
                exInstalledWasher1EditText.removeTextChangedListener(cParamsTextWatcher)


                //шайба впускная 2
                inInstalledWasher2EditText.removeTextChangedListener(cParamsTextWatcher)

                //шайба впускная 1
                inInstalledWasher1EditText.removeTextChangedListener(cParamsTextWatcher)

                //зазор впускной 2
                inGap2EditText.removeTextChangedListener(cParamsTextWatcher)
                //зазор впускной 1
                inGap1EditText.removeTextChangedListener(cParamsTextWatcher)
            }
        }
    }

    //два клапана на цилиндр
    class CylinderTwoViewHolder(val binding: ItemCylinder2ValveBinding) :
        RecyclerView.ViewHolder(binding.root), ListensTextChanges {
        lateinit var cParamsTextWatcher: CylinderParamsTextWatcher

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

                cylinderNumber.text = (position + 1).toString()
            }
        }

        override fun updatePosition(pos: Int) {
            cParamsTextWatcher.updatePosition(pos)
        }

        override fun setTextWatcher(cylinderParamsTextWatcher: CylinderParamsTextWatcher) {
            cParamsTextWatcher = cylinderParamsTextWatcher
        }

        override fun enableTextWatcher() {
            with(binding) {
                exGapEditText.addTextChangedListener(cParamsTextWatcher)
                //шайба выпускная
                exInstalledWasherEditText.addTextChangedListener(cParamsTextWatcher)
                //зазор впускной
                inGapEditText.addTextChangedListener(cParamsTextWatcher)
                //шайбы впуск
                inInstalledWasher1EditText.addTextChangedListener(cParamsTextWatcher)
            }
        }

        override fun disableTextWatcher() {
            with(binding) {
                exGapEditText.removeTextChangedListener(cParamsTextWatcher)
                //шайба выпускная
                exInstalledWasherEditText.removeTextChangedListener(cParamsTextWatcher)
                //зазор впускной
                inGapEditText.removeTextChangedListener(cParamsTextWatcher)
                //шайбы впуск
                inInstalledWasher1EditText.removeTextChangedListener(cParamsTextWatcher)
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

                val holder = CylinderFourViewHolder(
                    ItemCylinder4ValvesBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
                holder.setTextWatcher(CylinderParamsTextWatcher { pos: Int, charSequence: CharSequence? ->
                    with(holder.binding) {

                        when (charSequence.hashCode()) {
                            exGap1EditText.text.hashCode() -> {
                                cylindersList[pos].exValveList[0].measuredGap =
                                    charSequence.toString().toDouble()
                            }

                            exGap2EditText.text.hashCode() -> {
                                cylindersList[pos].exValveList[1].measuredGap =
                                    charSequence.toString().toDouble()
                            }

                            exInstalledWasher1EditText.text.hashCode() -> {
                                cylindersList[pos].exValveList[0].washer =
                                    charSequence.toString().toDouble()
                            }

                            exInstalledWasher2EditText.text.hashCode() -> {
                                cylindersList[pos].exValveList[1].washer =
                                    charSequence.toString().toDouble()
                            }

                            inGap1EditText.text.hashCode() -> {
                                cylindersList[pos].inValveList[0].measuredGap =
                                    charSequence.toString().toDouble()
                            }

                            inGap2EditText.text.hashCode() -> {
                                cylindersList[pos].inValveList[1].measuredGap =
                                    charSequence.toString().toDouble()
                            }

                            inInstalledWasher1EditText.text.hashCode() -> {
                                cylindersList[pos].inValveList[0].washer =
                                    charSequence.toString().toDouble()
                            }

                            inInstalledWasher2EditText.text.hashCode() -> {
                                cylindersList[pos].inValveList[1].washer =
                                    charSequence.toString().toDouble()
                            }
                        }
                    }
                })

                return holder
            }
            else -> {

                val holder = CylinderTwoViewHolder(
                    ItemCylinder2ValveBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

                holder.setTextWatcher(CylinderParamsTextWatcher { pos: Int, charSequence: CharSequence? ->
                    with(holder.binding) {
                        when (charSequence.hashCode()) {
                            exGapEditText.text.hashCode() -> {
                                cylindersList[pos].exValveList[0].measuredGap =
                                    charSequence.toString().toDouble()
                                Log.d(
                                    "cylindersList",
                                    cylindersList[pos].exValveList[0].measuredGap.toString()
                                )
                            }

                            exInstalledWasherEditText.text.hashCode() -> {
                                cylindersList[pos].exValveList[0].washer =
                                    charSequence.toString().toDouble()
                            }

                            inGapEditText.text.hashCode() -> {
                                cylindersList[pos].inValveList[0].measuredGap =
                                    charSequence.toString().toDouble()
                            }

                            inInstalledWasher1EditText.text.hashCode() -> {
                                cylindersList[pos].inValveList[0].washer =
                                    charSequence.toString().toDouble()
                            }
                        }
                    }
                })
                return holder
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        (holder as ListensTextChanges).enableTextWatcher()
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        (holder as ListensTextChanges).disableTextWatcher()
    }

    override fun getItemCount(): Int {
        return cylindersList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val adapterPos = holder.adapterPosition
        when (holder) {

            is CylinderFourViewHolder -> {
                holder.updatePosition(adapterPos)
                holder.bind(cylindersList[adapterPos], adapterPos)
            }
            is CylinderTwoViewHolder -> {
                holder.updatePosition(adapterPos)
                holder.bind(cylindersList[adapterPos], adapterPos)
            }
        }
    }
}

