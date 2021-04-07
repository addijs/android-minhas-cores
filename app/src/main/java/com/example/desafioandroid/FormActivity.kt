package com.example.desafioandroid

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.desafioandroid.model.Cor

class FormActivity : AppCompatActivity() {
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

        this.nomeEditText = findViewById(R.id.formEt)
        this.seekBarRed = findViewById(R.id.formSbRed)
        this.seekBarGreen = findViewById(R.id.formSbGreen)
        this.seekBarBlue = findViewById(R.id.formSbBlue)
        this.rgbButton = findViewById(R.id.formRGBButton)
        this.saveButton = findViewById(R.id.formSaveButton)
        this.cancelButton = findViewById(R.id.formCancelButton)

        this.seekBarRed.setOnSeekBarChangeListener(ChangeColor())
        this.seekBarGreen.setOnSeekBarChangeListener(ChangeColor())
        this.seekBarBlue.setOnSeekBarChangeListener(ChangeColor())

        this.saveButton.setOnClickListener(SaveButtonOnClick())
        this.cancelButton.setOnClickListener(CancelButtonOnClick())
    }

    inner class SaveButtonOnClick: View.OnClickListener {
        override fun onClick(v: View?) {
            val corNome = this@FormActivity.nomeEditText.text.toString()
            val cor = Cor(corNome, rgbColor)

            val intent = Intent()
            intent.putExtra("COR", cor)

            setResult(RESULT_OK, intent)
            finish()
        }
    }

    inner class CancelButtonOnClick: View.OnClickListener {
        override fun onClick(v: View?) {
            setResult(RESULT_CANCELED)
        }
    }

    inner class ChangeColor: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            val redValue = this@FormActivity.seekBarRed.progress
            val greenValue = this@FormActivity.seekBarGreen.progress
            val blueValue = this@FormActivity.seekBarBlue.progress

            val color: Int = Color.rgb(redValue, greenValue, blueValue)

            this@FormActivity.rgbColor = color
            this@FormActivity.rgbButton.setBackgroundColor(color)
            this@FormActivity.rgbButton.text = Cor.toHex(color)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }
    }
}