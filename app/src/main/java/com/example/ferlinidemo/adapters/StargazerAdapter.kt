package com.example.ferlinidemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ferlinidemo.R
import com.example.ferlinidemo.responses.Stargazer
import com.squareup.picasso.Picasso

class StargazerAdapter(private val dataSet: List<Stargazer>) : RecyclerView.Adapter<StargazerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.stargazer_avatar)
        val textView: TextView = view.findViewById(R.id.stargazer_user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stargazer_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(dataSet[position].getAvatar()).into(holder.imageView)
        holder.textView.text = dataSet[position].getLogin()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}