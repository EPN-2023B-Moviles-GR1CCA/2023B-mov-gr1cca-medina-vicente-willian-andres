package com.example.apprestaurante

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FloraActivity : AppCompatActivity() {
    val scope = CoroutineScope(Dispatchers.Default)
    var id:Int = -1
    var id_parque:Int = -1
    var db = Database()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flora)
        val intent = intent

        var textnombre = findViewById(R.id.textnombre2) as TextView;
        var textubicacion = findViewById(R.id.textubicacion2) as TextView;
        var texttipo = findViewById(R.id.texttipo2) as TextView;
        var textreferencia = findViewById(R.id.textreferencia2) as TextView;


        val flora = intent.getSerializableExtra("clave_Datos") as? Flora
        val parque = intent.getSerializableExtra("clave_Parque") as? Parque
        if (flora != null && parque!=null) {
            this.id = flora.numero; var id_flora = flora.numero
            this.id_parque = parque.numero

            scope.launch {
                val flora = db.getParque(id_parque).getFlora(id_flora)

                runOnUiThread {
                    textnombre.text = flora?.nombre
                    textubicacion.text = flora?.ubicacion
                    texttipo.text = flora?.tipo
                    textreferencia.text = flora?.referencia
                }
            }


            var btnActualizar = findViewById(R.id.actualizar2) as Button;
            btnActualizar.setOnClickListener {
                showCustomDialog()
            }
            var btnEliminar = findViewById(R.id.eliminar2) as Button;
            btnEliminar.setOnClickListener {
                val ctx = this
                scope.launch {
                    var parque = db.getParque(id_parque)
                    parque.deleteFlora(id_flora)
                    db.updateParque(parque.numero, parque)

                    runOnUiThread {
                        val intent = Intent(ctx, ParqueActivity::class.java)
                        intent.putExtra("clave_Datos", parque)
                        startActivity(intent)
                    }
                }
            }

            var btnBack = findViewById(R.id.back) as Button;
            btnBack.setOnClickListener {
                val intent = Intent(this, ParqueActivity::class.java)
                intent.putExtra("clave_Datos", parque)
                startActivity(intent)
            }
            var btnRefresh = findViewById(R.id.refresh) as Button;
            btnRefresh.setOnClickListener {
                finish();
                startActivity(getIntent());
            }
        }

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

        var text_nombre: EditText = dialogView.findViewById(R.id.text_nombre1)
        var text_ubicacion: EditText = dialogView.findViewById(R.id.text_ubicacion1)
        var text_tipo: EditText = dialogView.findViewById(R.id.text_tipo)
        var text_referencia: EditText = dialogView.findViewById(R.id.text_referencia)

        val id_flora = this.id
        scope.launch {
            val flora = db.getParque(id_parque).getFlora(id_flora)

            runOnUiThread {
                text_nombre.setText(flora?.nombre)
                text_ubicacion.setText(flora?.ubicacion)
                text_tipo.setText(flora?.tipo)
                text_referencia.setText(flora?.referencia)
            }
        }
        btnEnviar.setOnClickListener {
            var newFlora = Flora(
                this.id,
                text_nombre.text.toString(),
                text_tipo.text.toString(),
                text_ubicacion.text.toString(),
                text_referencia.text.toString()
            )
            scope.launch {
                var parque = db.getParque(id_parque)
                parque.updateFlora(newFlora)
                db.updateParque(parque.numero, parque)

                runOnUiThread {
                    builder.hide()
                }
            }
        }

        builder.show()
    }
}