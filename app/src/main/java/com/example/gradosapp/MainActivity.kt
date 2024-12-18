package com.example.gradosapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var temperatureTextView: TextView
    private lateinit var updateButton: Button
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        temperatureTextView = findViewById(R.id.temperatureTextView)
        updateButton = findViewById(R.id.btnActu)


        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("temperatura")


        updateButton.setOnClickListener {
            obtenerTemperatura()
        }


        obtenerTemperatura()
    }

    private fun obtenerTemperatura() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (child in snapshot.children) {
                        val temperatura = child.child("temperatura").getValue(String::class.java)
                        if (temperatura != null) {
                            temperatureTextView.text = "$temperatura"
                        }
                    }
                } else {
                    temperatureTextView.text = "No hay datos disponibles"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                temperatureTextView.text = "Error: ${error.message}"
            }
        })
    }
}


