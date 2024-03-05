package com.example.apprestaurante

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.proyecto.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Intilization
        var btnShow = findViewById(R.id.botonGo) as Button;
        btnShow.setOnClickListener { // Crear un Intent para abrir la segunda actividad
            val intent = Intent(this, ParquesActivity::class.java)
            startActivity(intent)
        }
    }
}