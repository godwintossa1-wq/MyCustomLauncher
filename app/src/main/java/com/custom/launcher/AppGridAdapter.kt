package com.custom.launcher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppGridAdapter(
    private val appsList: List<AppItem>,
    private val onAppClicked: (AppItem, View) -> Unit
) : RecyclerView.Adapter<AppGridAdapter.AppViewHolder>() {

    class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.appIconImageView)
        val nameTextView: TextView = itemView.findViewById(R.id.appNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app_icon, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = appsList[position]
        holder.iconImageView.setImageDrawable(app.icon)
        holder.nameTextView.text = app.appName

        holder.itemView.setOnClickListener { view ->
            onAppClicked(app, view)
        }
    }

    override fun getItemCount(): Int = appsList.size
}
