package com.mprechl.cocktailapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mprechl.cocktailapp.databinding.ItemCocktailBinding
import com.mprechl.cocktailapp.model.Cocktail

class CocktailAdapter(private val cocktails: List<Cocktail>, private val listener: OnCocktailSelectedListener) : RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CocktailViewHolder(
        ItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val item = cocktails[position]
        holder.name.text = item.strDrink
        holder.id.text = item.idDrink.toString()
    }

    override fun getItemCount(): Int = cocktails.size

    inner class CocktailViewHolder(val binding: ItemCocktailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.cocktailNameTextView
        val id = binding.cocktailIdTextView

        init {
            binding.root.setOnClickListener {
                //Toast.makeText(context, "Clicked: " + id.text, Toast.LENGTH_SHORT).show()
                listener.onCocktailSelected(id.text.toString())
            }
        }

    }

    interface OnCocktailSelectedListener {
        fun onCocktailSelected(cocktailId: String?)
    }

}