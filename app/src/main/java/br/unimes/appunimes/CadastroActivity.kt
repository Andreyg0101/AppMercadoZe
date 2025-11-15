package br.unimes.appunimes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.unimes.appunimes.databinding.ActivityCadastroBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.fabLista.setOnClickListener {
            val intent = Intent(this, ListaProdutosActivity::class.java)
            startActivity(intent)
        }

        val db = Firebase.firestore
        binding.btnCadastrar.setOnClickListener {
            val nome = binding.edtNome.text.toString()
            val precoStr = binding.edtPreco.text.toString()
            val quantidadeStr = binding.edtQuantidade.text.toString()

            if (nome.isNotEmpty() && precoStr.isNotEmpty() && quantidadeStr.isNotEmpty()) {
                val preco = precoStr.toDouble()
                val quantidade = quantidadeStr.toInt()
                val produto = hashMapOf(
                    "nome" to nome,
                    "preço" to preco,
                    "quantidade" to quantidade
                )
                db.collection("produtos")
                    .add(produto)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                            binding.edtNome.setText("")
                            binding.edtPreco.setText("")
                            binding.edtQuantidade.setText("")
                        } else {
                            Toast.makeText(this, "Erro ao cadastrar o produto", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}