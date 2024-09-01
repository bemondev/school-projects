package com.example.cruds1.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cruds1.R
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setup()
    }


    private fun setup() {

        title = "Registro de usuario"

        var email = findViewById<EditText>(R.id.txtEmail)
        var password = findViewById<EditText>(R.id.txtPassword)
        var confirmPassword = findViewById<EditText>(R.id.txtConfirmPassword)

        val btnSingup = findViewById<Button>(R.id.btnSingin)
        btnSingup.setOnClickListener {
            if (validateInput(email.text.toString(), password.text.toString(), confirmPassword.text.toString())) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    email.text.toString(),
                    password.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showRegisterAlert()
                        showHome()
                    } else {
                        showAlert()
                    }
                }
            }
        }

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            val authActivity = Intent(this, AuthActivity::class.java)
            startActivity(authActivity)
        }
    }

    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showNullAlert()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showInvalidEmailAlert()
            return false
        }

        if (password.length < 8) {
            showPasswordLengthAlert()
            return false
        }

        if (!password.matches(Regex(".*[A-Z].*")) ||
            !password.matches(Regex(".*[a-z].*")) ||
            !password.matches(Regex(".*\\d.*")) ||
            !password.matches(Regex(".*[!@#\$%^&*(),.?\":{}|<>].*"))
        ) {
            showPasswordComplexityAlert()
            return false
        }

        if (password != confirmPassword) {
            showPasswordAlert()
            return false
        }

        return true
    }

    private fun showInvalidEmailAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Correo inválido")
        builder.setMessage("Por favor ingrese un correo electrónico válido.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showPasswordLengthAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Contraseña demasiado corta")
        builder.setMessage("La contraseña debe tener al menos 8 caracteres.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showPasswordComplexityAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Contraseña no segura")
        builder.setMessage("La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showRegisterAlert() {
        Toast.makeText(this, "Registro completado, inicie sesión.", Toast.LENGTH_SHORT).show()
    }

    private fun showNullAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Datos inválidos")
        builder.setMessage("Debe rellenar todos los campos.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showPasswordAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error con la contraseña")
        builder.setMessage("Las dos contraseñas deben ser iguales")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome() {
        var authActivity = Intent(this, AuthActivity::class.java).apply { }
        startActivity(authActivity)
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Error con el registro de usuario. \nAsegúrese que la contraseña contenga 8 caractéres como mínimo.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


}