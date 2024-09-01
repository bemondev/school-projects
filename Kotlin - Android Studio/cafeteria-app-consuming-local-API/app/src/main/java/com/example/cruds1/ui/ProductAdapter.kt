package com.example.cruds1.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cruds1.R
import com.example.cruds1.model.Product

class ProductAdapter(private val productos: MutableList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreEditText)
        val precioTextView: TextView = itemView.findViewById(R.id.precioEditText)
        val descTextView: TextView = itemView.findViewById(R.id.descriptionText)
        val imgView: ImageView = itemView.findViewById(R.id.imageView)
/*        val editButton: ImageButton = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.nombreTextView.text = producto.name
        holder.precioTextView.text = "$" + producto.price.toString()
        holder.descTextView.text = producto.description
        // Usar Glide para cargar la imagen desde la URL en imgView
        Glide.with(holder.itemView.context)
            .load(producto.urlImagen) // URL de la imagen
            .placeholder(R.drawable.placeholder) // Imagen de placeholder mientras carga
            .error(R.drawable.error) // Imagen a mostrar si ocurre un error
            .into(holder.imgView)
/*        holder.editButton.setOnClickListener { onEditClick(producto) }
        holder.deleteButton.setOnClickListener { onDeleteClick(producto) }*/
    }

    fun updateData(newProductos: List<Product>) {
        productos.clear()
        productos.addAll(newProductos)
        notifyDataSetChanged()
    }

    override fun getItemCount() = productos.size
    }
