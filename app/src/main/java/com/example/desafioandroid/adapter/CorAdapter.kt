package com.example.desafioandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.desafioandroid.R
import com.example.desafioandroid.model.Cor

class CorAdapter(private var cadastro: Cadastro, private var context: Context): BaseAdapter() {
  fun add(color: Cor) {
    this.cadastro.add(color)
  }

  fun setList(newList: ArrayList<Cor>) {
    this.cadastro.set(newList)
  }

  fun updateItem(color: Cor, position: Int) {
    this.cadastro.update(color, position)
  }

  fun removeItem(position: Int) {
    this.cadastro.remove(position)
  }

  override fun getCount(): Int {
    return this.cadastro.count()
  }

  override fun getItem(position: Int): Any {
    return this.cadastro.get(position)
  }

  override fun getItemId(position: Int): Long {
    return -1
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    val cor = this.cadastro.get(position)
    val linha: View

    linha = if (convertView == null) {
      val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      inflater.inflate(R.layout.listview_item, null)
    } else {
      convertView
    }

    val icon = linha.findViewById<ImageView>(R.id.ivLvCor)
    val tvNome = linha.findViewById<TextView>(R.id.tvNomeLvCor)
    val tvCodigo = linha.findViewById<TextView>(R.id.tvCodigoLvCor)

    icon.setColorFilter(cor.codigo)
    tvNome.text = cor.nome
    tvCodigo.text = cor.toHex()

    return linha
  }
}