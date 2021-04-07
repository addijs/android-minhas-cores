package com.example.desafioandroid

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.desafioandroid.DAO.CorDAO
import com.example.desafioandroid.model.Cor

class FormActivity : AppCompatActivity() {
  private lateinit var corDAO: CorDAO
  private lateinit var nomeEditText: EditText
  private lateinit var seekBarRed: SeekBar
  private lateinit var seekBarGreen: SeekBar
  private lateinit var seekBarBlue: SeekBar
  private lateinit var rgbButton: Button
  private lateinit var saveButton: Button
  private lateinit var cancelButton: Button
  private var rgbColor: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_form)

    this.corDAO = CorDAO(this)

    this.nomeEditText = findViewById(R.id.formEt)
    this.seekBarRed = findViewById(R.id.formSbRed)
    this.seekBarGreen = findViewById(R.id.formSbGreen)
    this.seekBarBlue = findViewById(R.id.formSbBlue)
    this.rgbButton = findViewById(R.id.formRGBButton)
    this.saveButton = findViewById(R.id.formSaveButton)
    this.cancelButton = findViewById(R.id.formCancelButton)

    if (intent.hasExtra("COR")) {
      val colorToEdit = intent.getSerializableExtra("COR") as Cor

      this.nomeEditText.setText(colorToEdit.nome)
      this.seekBarRed.progress = Color.red(colorToEdit.codigo)
      this.seekBarGreen.progress = Color.green(colorToEdit.codigo)
      this.seekBarBlue.progress = Color.blue(colorToEdit.codigo)
      this.rgbColor = colorToEdit.codigo
      this.changeButtonColor(colorToEdit.codigo)
    }

    this.seekBarRed.setOnSeekBarChangeListener(ChangeColor())
    this.seekBarGreen.setOnSeekBarChangeListener(ChangeColor())
    this.seekBarBlue.setOnSeekBarChangeListener(ChangeColor())

    this.saveButton.setOnClickListener(SaveButtonOnClickListener())
    this.cancelButton.setOnClickListener(CancelButtonOnClickListener())

    this.rgbButton.setOnClickListener(RgbButtonOnClickListener())
  }

  inner class RgbButtonOnClickListener: View.OnClickListener {
    override fun onClick(v: View?) {
      val colorHex = (v as Button).text
      val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
      val clip = ClipData.newPlainText("label", colorHex)
      clipboard.setPrimaryClip(clip)

      Toast.makeText(this@FormActivity, "Copiado: ${colorHex}", Toast.LENGTH_SHORT).show()
    }
  }

  inner class SaveButtonOnClickListener: View.OnClickListener {
    override fun onClick(v: View?) {
      val corNome = this@FormActivity.nomeEditText.text.toString()
      val cor = Cor(corNome, rgbColor)
      val newIntent = Intent()

      if (intent.hasExtra("COR") && intent.hasExtra("LIST_VIEW_ITEM_INDEX")) {
        val intentColorId = (intent.getSerializableExtra("COR") as Cor).id
        newIntent.putExtra("LIST_VIEW_ITEM_INDEX", intent.getIntExtra("LIST_VIEW_ITEM_INDEX", 0))
        cor.id = intentColorId

        this@FormActivity.corDAO.update(cor)
      } else {
        val colorId = this@FormActivity.corDAO.insert(cor)
        cor.id = colorId.toInt()
      }

      newIntent.putExtra("COR", cor)

      setResult(RESULT_OK, newIntent)
      finish()
    }
  }

  inner class CancelButtonOnClickListener: View.OnClickListener {
    override fun onClick(v: View?) {
      setResult(RESULT_CANCELED)
      finish()
    }
  }

  inner class ChangeColor: SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
      val redValue = this@FormActivity.seekBarRed.progress
      val greenValue = this@FormActivity.seekBarGreen.progress
      val blueValue = this@FormActivity.seekBarBlue.progress

      val color: Int = Color.rgb(redValue, greenValue, blueValue)

      this@FormActivity.rgbColor = color
      this@FormActivity.changeButtonColor(color)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }
  }

  fun changeButtonColor(colorInt: Int) {
    this.rgbButton.setBackgroundColor(colorInt)
    this.rgbButton.text = Cor.toHex(colorInt)
  }
}