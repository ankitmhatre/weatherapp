package com.chefling.app

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.chefling.app.models.Forecast

class ForecastRecyclerAdapter :RecyclerView.Adapter<ForecastRecyclerAdapter.MyViewHolder>() {

    private var forecaseItem: MutableList<Forecast> = ArrayList()


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var day: TextView
        internal var weatherIcon: ImageView
        internal var temp: TextView


        init {
            day = itemView.findViewById<View>(R.id.dayRv) as TextView

            weatherIcon = itemView.findViewById<View>(R.id.weatherIconRv) as ImageView
            temp = itemView.findViewById<View>(R.id.tempRv) as TextView


        }
    }

    interface OnItemLongClickListener {
        fun onItemLongClicked(position: Int): Boolean
    }

    fun update(newsItems: MutableList<Forecast>) {
        this.forecaseItem.clear()
        this.forecaseItem = newsItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.day.text = forecaseItem[position].dt.toString()
        holder.temp.text = "${forecaseItem[position].temp_min}/${forecaseItem[position].temp_max}"





        holder.weatherIcon.load("http://openweathermap.org/img/wn/${forecaseItem[0].icon}@2x.png") {
            crossfade(true)
        }


    }

    override fun getItemCount(): Int {
        return forecaseItem.size
    }


}