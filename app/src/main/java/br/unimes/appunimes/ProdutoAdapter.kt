package br.unimes.appunimes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ProdutoAdapter(private val onDelete: (Produto) -> Unit) : ListAdapter<Produto, ProdutoAdapter.ProdutoViewHolder>(ProdutoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_produto, parent, false)
        return ProdutoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val produto = getItem(position)
        holder.bind(produto, onDelete)
    }

    class ProdutoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeProduto: TextView = itemView.findViewById(R.id.tv_nome_produto)
        private val precoProduto: TextView = itemView.findViewById(R.id.tv_preco_produto)
        private val quantidadeProduto: TextView = itemView.findViewById(R.id.tv_quantidade_produto)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btn_delete)

        fun bind(produto: Produto, onDelete: (Produto) -> Unit) {
            nomeProduto.text = produto.nome
            precoProduto.text = "Preço: R$ ${String.format("%.2f", produto.preco)}"
            quantidadeProduto.text = "Quantidade: ${produto.quantidade}"
            btnDelete.setOnClickListener { onDelete(produto) }
        }
    }

    class ProdutoDiffCallback : DiffUtil.ItemCallback<Produto>() {
        override fun areItemsTheSame(oldItem: Produto, newItem: Produto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Produto, newItem: Produto): Boolean {
            return oldItem == newItem
        }
    }
}