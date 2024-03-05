package com.example.apprestaurante

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FloraAdapter(
    private val listaItems: ArrayList<Flora>,
    private val onItemClick: (Flora) -> Unit
) : RecyclerView.Adapter<ParqueAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParqueAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_flora, parent, false)
        return ParqueAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParqueAdapter.ViewHolder, position: Int) {
        val item = listaItems[position]
        holder.nombreTextView.text = item.whoiam()

        // Agregar el listener de clic al elemento
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return listaItems.size
    }

}