package com.example.apprestaurante

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setMargins
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParqueActivity : AppCompatActivity() {
    var db = Database()
    var id:Int = -1
    var scope = CoroutineScope(Dispatchers.Default)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parque)
        val intent = intent

        var textnombre = findViewById(R.id.textnombre1) as TextView;
        var textubicacion = findViewById(R.id.textubicacion1) as TextView;
        var textresponsable = findViewById(R.id.textresponsable1) as TextView;
        val parque = intent.getSerializableExtra("clave_Datos") as? Parque
        if (parque != null) {
            this.id = parque.numero
            scope.launch {
                var parque = db.getParque(id)

                runOnUiThread {
                    textnombre.text = parque.nombre
                    textubicacion.text = parque.direccion
                    textresponsable.text = parque.responsable
                }

            }

            var btnActualizar = findViewById(R.id.actualizar1) as Button;
            btnActualizar.setOnClickListener {
                showCustomDialog2()
            }
            var btnEliminar = findViewById(R.id.eliminar1) as Button;
            btnEliminar.setOnClickListener {
                db.deleteParque(parque.numero)
                val intent = Intent(this, ParquesActivity::class.java)
                startActivity(intent)
            }
        }

        showLista()

        var btnBack = findViewById(R.id.back) as Button;
        btnBack.setOnClickListener {
            val intent = Intent(this, ParquesActivity::class.java)
            startActivity(intent)
        }
        var btnRefresh = findViewById(R.id.refresh) as Button;
        btnRefresh.setOnClickListener {
            finish();
            startActivity(getIntent());
        }


        var btnmore = findViewById(R.id.btnmores) as Button;
        btnmore.setOnClickListener {
            showCustomDialog()
        }



    }

    fun showLista(){
        val recyclerView: RecyclerView = findViewById(R.id.contenedor1)
        recyclerView.layoutManager = LinearLayoutManager(this)
        var context = this
        var index = this.id
        scope.launch {
            var parque = db.getParque(index)

            runOnUiThread {
                val miAdapter = FloraAdapter(parque.flora ?: arrayListOf<Flora>()){
                    val intent = Intent(context, FloraActivity::class.java)
                    intent.putExtra("clave_Parque", parque)
                    intent.putExtra("clave_Datos", it)
                    startActivity(intent)
                }
                recyclerView.adapter = miAdapter
            }
        }

    }

    @SuppressLint("MissingInflatedId")
    private fun showCustomDialog2() {
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

        scope.launch {
            var parque = db.getParque(id)

            runOnUiThread {
                text_nombre.setText(parque.nombre)
                text_ubicacion.setText(parque.direccion)
                text_responsable.setText(parque.responsable)
            }
        }



        btnEnviar.setOnClickListener {
            val aux = Parque(
                this.id,
                text_nombre.text.toString(),
                text_ubicacion.text.toString(),
                text_responsable.text.toString()
            )
            val index = this.id
            scope.launch {
                aux.flora= db.getParque(index).flora ?: arrayListOf()
                db.updateParque(aux.numero,aux)
                runOnUiThread {
                    builder.hide()
                }
            }
        }

        builder.show()
    }

    private fun showCustomDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_ingreso_datos_flora, null)

        val btnDismissDialog: Button = dialogView.findViewById(R.id.btnCancelar1)
        val btnEnviar: Button = dialogView.findViewById(R.id.btnEnviar1)

        val builder = Dialog(this)
        builder.setContentView(dialogView)

        btnDismissDialog.setOnClickListener {
            builder.dismiss()
        }

        val text_nombre: EditText = dialogView.findViewById(R.id.text_nombre1)
        val text_ubicacion: EditText = dialogView.findViewById(R.id.text_ubicacion1)
        val text_tipo: EditText = dialogView.findViewById(R.id.text_tipo)
        val text_referencia: EditText = dialogView.findViewById(R.id.text_referencia)


        btnEnviar.setOnClickListener {
            var index = this.id
            scope.launch {
                var parque = db.getParque(index)
                parque.addFlora(
                    Flora(
                        Data.seq_flora++,
                        text_nombre.text.toString(),
                        text_tipo.text.toString(),
                        text_ubicacion.text.toString(),
                        text_referencia.text.toString()
                    )
                )
                runOnUiThread {
                    showLista()
                }
                db.updateParque(parque.numero, parque)
            }
            builder.hide()
        }

        builder.show()
    }

}