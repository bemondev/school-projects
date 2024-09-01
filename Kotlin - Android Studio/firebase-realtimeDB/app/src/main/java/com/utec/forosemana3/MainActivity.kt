package com.utec.forosemana3

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    private val usersReference = database.getReference("users")
    private lateinit var logs: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        logs = findViewById(R.id.logs)

        leerDatos()
        escribirDatos()
    }

    private fun leerDatos() {
        usersReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val logBuilder = StringBuilder();
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null) {
                        val logMessage = "Usuario: ${user.name}, Email: ${user.email}\n"
                        logBuilder.append(logMessage)
                    }
                }
                logs.text = logBuilder.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Error al leer los datos: ${error.message}")
            }
        })
    }
    private fun escribirDatos() {
        val userId = usersReference.push().key ?: return  // Genera una clave única
        val nuevoUsuario = User(name = "Juan Pérez", email = "juanperez@example.com")

        usersReference.child(userId).setValue(nuevoUsuario)
            .addOnSuccessListener {
                Log.d("Exito","Usuario guardado exitosamente con ID: $userId\n")
            }
            .addOnFailureListener { exception ->
                Log.e("Error","Error al guardar el usuario: ${exception.message}\n")
            }
    }
}
/*        // Dato guardado de prueba
        val userId = "usuario_123"
        val user = User(name = "John Doe", email = "johndoe@example.com")
        database.child("users").child(userId).setValue(user)*/

data class User(
    val name: String? = null,
    val email: String? = null
)
