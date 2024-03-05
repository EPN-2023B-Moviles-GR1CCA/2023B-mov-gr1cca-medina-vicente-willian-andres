package com.example.apprestaurante

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ParquesActivity : AppCompatActivity() {
    var db = Database()
    var scope = CoroutineScope(Dispatchers.Default)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parques)

        var btnmore = findViewById(R.id.btnmore) as Button
        btnmore.setOnClickListener {
            showCustomDialog()
        }

        showLista()

        var btnBack = findViewById(R.id.back) as Button;
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        var btnRefresh = findViewById(R.id.refresh) as Button;
        btnRefresh.setOnClickListener {
            finish();
            startActivity(getIntent());
        }
    }

    fun showLista(){
        val recyclerView: RecyclerView = findViewById(R.id.contenedor)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val context = this
        scope.launch {
            var parquess:ArrayList<Parque>
            try {
                parquess = db.getParques()
            }catch (e: Exception){
                    Log.i("MINEWM", "showLista: ${e}")
                parquess = ArrayList<Parque>()
            }

            runOnUiThread {
                val miAdapter = ParqueAdapter(parquess){
                    val intent = Intent(context, ParqueActivity::class.java)
                    intent.putExtra("clave_Datos", it)
                    startActivity(intent)
                }
                recyclerView.adapter = miAdapter
            }
        }
    }

    private fun showCustomDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_ingreso_datos_parque, null)

        val btnDismissDialog: Button = dialogView.findViewById(R.id.btnCancelar)
        val btnEnviar: Button = dialogView.findViewById(R.id.btnEnviar)

        val builder = Dialog(this)
        builder.setContentView(dialogView)

        btnDismissDialog.setOnClickListener {
            builder.dismiss()
        }

        val text_nombre: EditText = dialogView.findViewById(R.id.text_nombre)
        val text_ubicacion: EditText = dialogView.findViewById(R.id.text_ubicacion)
        val text_responsable: EditText = dialogView.findViewById(R.id.text_responsable)

        btnEnviar.setOnClickListener {
            db.addParque(Parque(
                Data.seq_parques++,
                text_nombre.text.toString(),
                text_ubicacion.text.toString(),
                text_responsable.text.toString()
            ))
            showLista()
            builder.hide()
        }

        builder.show()
    }

}