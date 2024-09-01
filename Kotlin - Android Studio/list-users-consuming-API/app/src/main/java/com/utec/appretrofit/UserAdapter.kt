package com.utec.appretrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val users: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
        holder.itemView.setOnClickListener {
            val user = users[position]
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Información del usuario")
            builder.setMessage("Nombre completo: ${user.name}\n" +
                    "Usuario: ${user.username}\n" +
                    "Correo: ${user.email}\n" +
                    "Telefono: ${user.phone}\n" +
                    "Sitio Web: ${user.website}")
            builder.setPositiveButton("Aceptar", null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    override fun getItemCount() = users.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.findViewById<TextView>(R.id.idTextView).text = "ID: " + user.id.toString()
            itemView.findViewById<TextView>(R.id.nameTextView).text = "Username: " + user.username
        }
    }
}