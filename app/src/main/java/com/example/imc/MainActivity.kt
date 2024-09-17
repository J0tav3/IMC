package com.example.imc

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.calcularbutton.setOnClickListener {
            val peso = binding.pesoEdit.text.toString().toDouble()
            val altura = binding.alturaEdit.text.toString().toDouble()
            if (peso != null && altura !=null) {
                calcularIMC(altura, peso)
            } else {
                binding.resultadoIMC.text = "Erro! Por favor, insira valores válidos."
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun calcularIMC (altura: Double, peso: Double){
        val usuario = Usuario( "usuario", 0, peso, altura )
        val imc = usuario.calcularIMC()
        val descricao = usuario.descricaoIMC()
        binding.resultadoIMC.text = "IMC: %.2f - %s".format(imc, descricao)

    }
}

class Usuario (val nome:String, val idade: Int, val peso: Double, val altura: Double){

    fun calcularIMC(): Double = peso / (altura * altura)

    fun descricaoIMC(): String{
        val imc = calcularIMC()

        return when {
            imc < 18.5 -> "Abaixo do peso"
            imc < 24.9 -> "Peso normal"
            imc < 29.9 -> "Sobrepeso"
            imc < 34.9 -> "Obesidade grau I"
            imc < 39.9 -> "Obesidade grau II"
            else -> "Obesidade grau III"
        }
    }

}