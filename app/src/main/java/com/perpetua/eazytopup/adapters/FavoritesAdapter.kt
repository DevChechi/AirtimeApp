package com.perpetua.eazytopup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.perpetua.eazytopup.databinding.ItemFavoriteBinding
import com.perpetua.eazytopup.models.Contact

class FavoritesAdapter: RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {
    private var _binding: ItemFavoriteBinding? = null
    private val binding get() = _binding!!

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        _binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val contact = differ.currentList[position]
        holder.itemView.apply {
            binding.favoriteName.text = contact.name
            binding.favoritePhoneNumber.text = contact.phoneNumber
            setOnClickListener{
                onItemClickListener?.let { it(contact) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    val diffCallback = object: DiffUtil.ItemCallback<Contact>(){
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.phoneNumber == newItem.phoneNumber
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffCallback)

    //listener
    private var onItemClickListener: ((Contact) -> Unit)? = null

    fun setOnItemClickListener(listener: (Contact) -> Unit){
        onItemClickListener = listener
    }
}