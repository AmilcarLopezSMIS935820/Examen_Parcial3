package com.smis935820.examen_parcial3

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.smis935820.examen_parcial3.databinding.ItemDogBinding
import com.squareup.picasso.Picasso

class DogViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val binding = ItemDogBinding.bind(view)

    fun bind(image:String){
        Picasso.get().load(image).into(binding.ivDog)
    }
}