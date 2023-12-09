package com.example.vamosrachar

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.util.Locale

class MainActivity : AppCompatActivity() {
    // Cada variável é chamada de uma forma
    private lateinit var total: EditText
    private lateinit var pessoas: EditText

    private lateinit var tts: TextToSpeech
    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Pega o que for escrito pelo EditText
        total = findViewById(R.id.edit)
        pessoas = findViewById(R.id.edit_2)

        // Adiciona TextChangedListener
        total.addTextChangedListener(textWatcher)
        pessoas.addTextChangedListener(textWatcher)

        // Permite o uso de tts
        var speakButton = findViewById<Button>(R.id.micButton)
        var el = findViewById<TextView>(R.id.result)

        tts = TextToSpeech(this){status-> // Suporte de idioma e resposta caso não possua
            if(status == TextToSpeech.SUCCESS){
                val result = tts.setLanguage(Locale.getDefault())
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(this, "language is not suported", Toast.LENGTH_LONG).show()
                }
            }
        }

        // tts fazendo sua função
        speakButton.setOnClickListener{
            if (el.text.toString().trim().isNotEmpty()){
                tts.speak(el.text.toString().trim(), TextToSpeech.QUEUE_FLUSH, null, null)
            } else {
                Toast.makeText(this, "Required", Toast.LENGTH_LONG).show()
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Sem necessidade
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Quando alterado, o texto chama essa função
            calcule()
        }

        override fun afterTextChanged(s: Editable?) {
            // Sem necessidade
        }
    }

    // Divisão pelo número de pessoas
    private fun calcule() {
        val vTotal = total.text.toString().toDoubleOrNull()
        val Pessoas = pessoas.text.toString().toDoubleOrNull()

        if (vTotal != null && Pessoas != null) {
            // Caso ambos não sejam null (ou seja, sejam double) calcula a razão
            val razao = vTotal / Pessoas

            // Atualiza o valor de result pela razão da função
            val resultadoFinal = findViewById<TextView>(R.id.result)
            resultadoFinal.text = razao.toString()
        }
    }
}