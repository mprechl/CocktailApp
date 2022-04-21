package com.mprechl.cocktailapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mprechl.cocktailapp.databinding.ItemIngredientBinding
import com.mprechl.cocktailapp.model.Ingredient

class IngredientAdapter(private val ingredients: List<Ingredient>, private val listener: OnIngredientSelectedListener) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IngredientViewHolder(
        ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val item = ingredients[position]
        holder.name.text = item.strIngredient
        holder.id.text = item.idIngredient.toString()
    }

    override fun getItemCount(): Int = ingredients.size

    inner class IngredientViewHolder(val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.ingredientNameTextView
        val id = binding.ingredientIdTextView

        init {
            binding.root.setOnClickListener {
                //Toast.makeText(context, "Clicked: " + id.text, Toast.LENGTH_SHORT).show()
                listener.onIngredientSelected(id.text.toString())
            }
        }

    }

    interface OnIngredientSelectedListener {
        fun onIngredientSelected(ingredientId: String?)
    }

}