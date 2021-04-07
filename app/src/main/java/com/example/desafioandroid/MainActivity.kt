package com.example.desafioandroid

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.desafioandroid.DAO.CorDAO
import com.example.desafioandroid.adapter.Cadastro
import com.example.desafioandroid.adapter.CorAdapter
import com.example.desafioandroid.model.Cor
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
  val CREATE_ITEM = 1
  val UPDATE_ITEM = 2

  private lateinit var corDAO: CorDAO
  private lateinit var cadastro: Cadastro
  private lateinit var listView: ListView
  private lateinit var floatingActionButton: FloatingActionButton

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    this.corDAO = CorDAO(this)
    this.cadastro = Cadastro()

    this.listView = findViewById(R.id.mainLvCor)
    this.listView.adapter = CorAdapter(cadastro, this)
    this.listView.onItemClickListener = ClickListItem()
    this.listView.onItemLongClickListener = LongClickListItem()

    val colors = this.corDAO.findAll()
    (this.listView.adapter as CorAdapter).setList(colors)

    this.floatingActionButton = findViewById(R.id.mainFabAdd)
    this.floatingActionButton.setOnClickListener(ClickFloatingActionButton())
  }

  inner class ClickFloatingActionButton: View.OnClickListener {
    override fun onClick(v: View?) {
      val intent = Intent(this@MainActivity, FormActivity::class.java)
      startActivityForResult(intent, CREATE_ITEM)
    }
  }

  inner class ClickListItem: AdapterView.OnItemClickListener{
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
      val color = this@MainActivity.listView.adapter.getItem(position) as Cor
      val intent = Intent(this@MainActivity, FormActivity::class.java)
      intent.putExtra("COR", color)
      intent.putExtra("LIST_VIEW_ITEM_INDEX", position)

      startActivityForResult(intent, UPDATE_ITEM)
    }
  }

  inner class LongClickListItem: AdapterView.OnItemLongClickListener{
    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
      val color = this@MainActivity.listView.adapter.getItem(position) as Cor
      this@MainActivity.corDAO.delete(color.id)
      (this@MainActivity.listView.adapter as CorAdapter).removeItem(position)
      (this@MainActivity.listView.adapter as CorAdapter).notifyDataSetChanged()

      return true
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (resultCode == RESULT_OK) {
      if (requestCode == CREATE_ITEM) {
        val cor = data?.getSerializableExtra("COR") as Cor

        (this.listView.adapter as CorAdapter).add(cor)
        (this.listView.adapter as CorAdapter).notifyDataSetChanged()
        Toast.makeText(this, "Cor criada com sucesso", Toast.LENGTH_SHORT).show()
      }

      if (requestCode == UPDATE_ITEM) {
        val cor = data?.getSerializableExtra("COR") as Cor
        val index = data?.getIntExtra("LIST_VIEW_ITEM_INDEX", 0)

        (this.listView.adapter as CorAdapter).updateItem(cor, index)
        (this.listView.adapter as CorAdapter).notifyDataSetChanged()
        Toast.makeText(this, "Cor atualizada com sucesso", Toast.LENGTH_SHORT).show()
      }
    }
  }
}