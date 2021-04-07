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
  val LIST_VIEW = 1

  private lateinit var cadastro: Cadastro
  private lateinit var listView: ListView
  private lateinit var floatingActionButton: FloatingActionButton

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    this.cadastro = Cadastro()

    this.listView = findViewById(R.id.mainLvCor)
    this.listView.adapter = CorAdapter(cadastro, this)

    this.floatingActionButton = findViewById(R.id.mainFabAdd)
    this.floatingActionButton.setOnClickListener(ClickFloatingActionButton())
  }

  inner class ClickFloatingActionButton: View.OnClickListener {
    override fun onClick(v: View?) {
      val intent = Intent(this@MainActivity, FormActivity::class.java)
      startActivityForResult(intent, LIST_VIEW)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (resultCode == RESULT_OK) {
      if (requestCode == LIST_VIEW) {
        val cor = data?.getSerializableExtra("COR") as Cor

        (this.listView.adapter as CorAdapter).add(cor)
        (this.listView.adapter as CorAdapter).notifyDataSetChanged()
      }
    }
  }
}