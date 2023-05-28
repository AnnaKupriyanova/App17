package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.domain.Weather
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemWeatherColdHolderBinding
import com.example.myapplication.databinding.ItemWeatherWarmHolderBinding
import com.example.myapplication.ui.dataclass.WeatherUI

private const val TYPE_COLD = 0
private const val TYPE_WARM = 1

class WeatherAdapter(private val onLongClick: (WeatherUI) -> Unit) :
    ListAdapter<WeatherUI, RecyclerView.ViewHolder>(WeatherDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_COLD -> {
                val bindingCold = ItemWeatherColdHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return WeatherColdHolder(bindingCold)
            }
            TYPE_WARM -> {
                val bindingWarm = ItemWeatherWarmHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return WeatherWarmHolder(bindingWarm)
            }
            else -> {
                throw IllegalArgumentException("Missing type of holder")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_COLD -> (holder as WeatherColdHolder).bind(getItem(position), onLongClick)
            TYPE_WARM -> (holder as WeatherWarmHolder).bind(getItem(position), onLongClick)
            else -> throw IllegalArgumentException("Invalid temperature")
        }
    }

    override fun getItemViewType(position: Int): Int = if (getItem(position).temp < 10) {
        TYPE_COLD
    } else {
        TYPE_WARM
    }
}

private fun getImage(weather: WeatherUI) =
    "https://openweathermap.org/img/wn/${weather.icon}@2x.png"

class WeatherColdHolder(private val binding: ItemWeatherColdHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: WeatherUI, onLongClick: (WeatherUI) -> Unit) {
        val weatherTimeDate = weather.dtTxt.split(" ")
        binding.temperature.text =
            binding.root.context.getString(R.string.temperature, weather.temp.toString())
        binding.time.text = weatherTimeDate.first()
        binding.date.text = weatherTimeDate.last()
        /*Glide
            .with(binding.root)
            .load(getImage(weather))
            .into(binding.icon)*/
        binding.root.setOnLongClickListener(View.OnLongClickListener {
            onLongClick(weather)
            return@OnLongClickListener true
        })
    }
}

class WeatherWarmHolder(private val binding: ItemWeatherWarmHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: WeatherUI, onLongClick: (WeatherUI) -> Unit) {
        val weatherTimeDate = weather.dtTxt.split(" ")
        binding.temperature.text =
            binding.root.context.getString(R.string.temperature, weather.temp.toString())
        binding.time.text = weatherTimeDate.first()
        binding.date.text = weatherTimeDate.last()
        /*Glide
            .with(binding.root)
            .load(getImage(weather))
            .into(binding.icon)*/
        binding.root.setOnLongClickListener(View.OnLongClickListener {
            onLongClick(weather)
            return@OnLongClickListener true
        })
    }
}

class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherUI>() {
    override fun areItemsTheSame(
        oldItem: WeatherUI,
        newItem: WeatherUI
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: WeatherUI,
        newItem: WeatherUI
    ): Boolean = oldItem == newItem
}