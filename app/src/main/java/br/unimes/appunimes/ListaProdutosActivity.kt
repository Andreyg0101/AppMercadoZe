package br.unimes.appunimes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.unimes.appunimes.databinding.ActivityListaProdutosBinding
import com.google.firebase.firestore.FirebaseFirestore

data class Produto(val id: String, val nome: String, val preco: Double, val quantidade: Int)

class ListaProdutosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaProdutosBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var produtoAdapter: ProdutoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        carregarProdutos()
    }

    private fun setupRecyclerView() {
        produtoAdapter = ProdutoAdapter { produto ->
            excluirProduto(produto)
        }
        binding.rvProdutos.apply {
            layoutManager = LinearLayoutManager(this@ListaProdutosActivity)
            adapter = produtoAdapter
        }
    }

    private fun carregarProdutos() {
        db.collection("produtos").get().addOnSuccessListener { result ->
            val produtos = result.map { document ->
                Produto(
                    document.id,
                    document.getString("nome") ?: "",
                    document.getDouble("preço") ?: 0.0,
                    document.getLong("quantidade")?.toInt() ?: 0
                )
            }
            produtoAdapter.submitList(produtos)
        }
    }

    private fun excluirProduto(produto: Produto) {
        db.collection("produtos").document(produto.id).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Produto excluído com sucesso!", Toast.LENGTH_SHORT).show()
                carregarProdutos() // Recarrega a lista
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao excluir o produto", Toast.LENGTH_SHORT).show()
            }
    }
}