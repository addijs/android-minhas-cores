package com.example.desafioandroid

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import androidx.annotation.RequiresApi
import com.example.desafioandroid.adapter.Cadastro
import com.example.desafioandroid.adapter.CorAdapter
import com.example.desafioandroid.model.Cor
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

  }
}