package com.charging.practiceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.charging.practiceapp.R
import com.charging.practiceapp.databinding.CardViewDesignBinding
import com.charging.practiceapp.model.Details

class DetailsAdapter : ListAdapter<Details, DetailsViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val binding:CardViewDesignBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.card_view_design,parent,false);
        return DetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val data=getItem(position)
        holder.binding.cardData=data
    }
}

class DetailsViewHolder(val binding:CardViewDesignBinding): RecyclerView.ViewHolder(binding.root)

val DIFF_CALLBACK: DiffUtil.ItemCallback<Details> = object : DiffUtil.ItemCallback<Details>() {
    override fun areItemsTheSame(
        oldUser: Details, newUser: Details
    ): Boolean {
        // User properties may have changed if reloaded from the DB, but ID is fixed
        return oldUser.id == newUser.id
    }

    override fun areContentsTheSame(oldItem: Details, newItem: Details): Boolean {
        return oldItem.equals(newItem)
    }
}