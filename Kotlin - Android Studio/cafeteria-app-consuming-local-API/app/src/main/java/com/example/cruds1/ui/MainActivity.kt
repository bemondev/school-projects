package com.example.cruds1.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cruds1.R
import com.example.cruds1.model.Categoria
import com.example.cruds1.model.Product
import com.example.cruds1.remote.ApiService
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var emailActual = ""
    private var providerActual = ""

    private lateinit var btnCategorias: Button
    private lateinit var searchEditText: EditText
    private lateinit var recycler: RecyclerView
    private lateinit var noResultsTextView: TextView

    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas después de setContentView
        btnCategorias = findViewById(R.id.btn_categorias)
        searchEditText = findViewById(R.id.txtSearch)
        recycler = findViewById(R.id.recyclerView)
        noResultsTextView = findViewById(R.id.txtNoResults)

        btnCategorias.setOnClickListener { view ->
            showPopupCategorias(view)
        }

        recycler.layoutManager = LinearLayoutManager(this)

        // Inicializar el adaptador vacío con una MutableList y asignarlo al RecyclerView
        productAdapter = ProductAdapter(mutableListOf())
        recycler.adapter = productAdapter

        // Configuración inicial
        setupRetrofitAndLoadProducts()

        // Recuperar datos del Intent
        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("email")
        val provider: String? = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")

        // Configurar el listener para el campo de búsqueda
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filtrarProductos(s.toString(), btnCategorias.text.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupRetrofitAndLoadProducts() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/servidorAppMobileCoffee-1.0-SNAPSHOT/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.obtenerProductos()

        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val productos: List<Product>? = response.body()
                    if (productos != null && productos.isNotEmpty()) {
                        noResultsTextView.visibility = View.GONE
                        productAdapter.updateData(productos)  // Actualizar los datos del adaptador
                    } else {
                        noResultsTextView.visibility = View.VISIBLE
                    }
                } else {
                    Log.d("FTC", "response.IsSuccessful = false")
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.d("FTC", "onFailure")
                noResultsTextView.visibility = View.VISIBLE
            }
        })
    }

    private fun filtrarProductos(nombre: String?, categoriaTexto: String?) {
        val categoria = when (categoriaTexto) {
            "Todos ▼" -> null
            "Bebidas Calientes ▼" -> "BEBIDA_CALIENTE"
            "Bebidas Frías ▼" -> "BEBIDA_FRIA"
            "Snacks ▼" -> "SNACK"
            "Postres ▼" -> "POSTRE"
            else -> null
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/servidorAppMobileCoffee-1.0-SNAPSHOT/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.obtenerPorFiltros(nombre, categoria)

        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val productos = response.body()
                    if (productos != null && productos.isNotEmpty()) {
                        noResultsTextView.visibility = View.GONE
                        productAdapter.updateData(productos)  // Actualizar los datos del adaptador
                    } else {
                        noResultsTextView.visibility = View.VISIBLE
                        productAdapter.updateData(emptyList())  // Mostrar lista vacía
                    }
                } else {
                    noResultsTextView.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.d("FTC", "Error en la carga de productos: ${t.message}")
                noResultsTextView.visibility = View.VISIBLE
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option -> {
                showPopup(findViewById(R.id.option))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // FUNCION PARA POPUP DE CATEGORIAS
    private fun showPopupCategorias(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.menu_categorias, popup.menu)

        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.todos -> {
                    btnCategorias.text = "Todos ▼"
                    filtrarProductos(searchEditText.text.toString(), "Todos ▼")
                    true
                }
                R.id.bebidas_calientes -> {
                    btnCategorias.text = "Bebidas Calientes ▼"
                    filtrarProductos(searchEditText.text.toString(), "Bebidas Calientes ▼")
                    true
                }
                R.id.bebidas_frias -> {
                    btnCategorias.text = "Bebidas Frías ▼"
                    filtrarProductos(searchEditText.text.toString(), "Bebidas Frías ▼")
                    true
                }
                R.id.snacks -> {
                    btnCategorias.text = "Snacks ▼"
                    filtrarProductos(searchEditText.text.toString(), "Snacks ▼")
                    true
                }
                R.id.postres -> {
                    btnCategorias.text = "Postres ▼"
                    filtrarProductos(searchEditText.text.toString(), "Postres ▼")
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    // FUNCION PARA POPUP DE MENU - CERRAR SESIÓN
    private fun showPopup(anchor: View) {
        val popup = PopupMenu(this, anchor)
        popup.menuInflater.inflate(R.menu.menu_options, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.optionLogOut -> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, AuthActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                    true
                }

                else -> false
            }
        }
        popup.show()
    }

    private fun setup(email: String, provider: String) {
        title = "Menú"
        emailActual = email
        providerActual = provider
    }
}
