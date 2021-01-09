package energyto.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.mahdidrv.energyto.R

class QuoteAdapter : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    private var onRvItemClickListener: OnRvItemClickListener<Quote>? = null
    protected var items = arrayListOf<Quote>()

    fun setQuote(items: List<Quote>) {
        this.items = items as ArrayList<Quote>
        notifyDataSetChanged()
    }

    fun addQuote(quote: Quote) {
        items.add(itemCount, quote)
        notifyItemInserted(itemCount)
    }

    fun removeQuote(quote: Quote, position: Int) {
        items.remove(quote)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quote, parent, false)
        return QuoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setOnRvItemClickListener(onRvItemClickListener: OnRvItemClickListener<Quote>) {
        this.onRvItemClickListener = onRvItemClickListener
    }


    inner class QuoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var authorTv: TextView
        var quoteTv: TextView
        var eraserIv: ImageView
        var shareIv: ImageView


        init {
            authorTv = view.findViewById(R.id.tv_item_author)
            quoteTv = view.findViewById(R.id.tv_item_quote)
            eraserIv = view.findViewById(R.id.iv_item_eraser)
            shareIv = view.findViewById(R.id.iv_item_share)
        }

        fun bind(quote: Quote) {
            authorTv.text = quote.auther
            quoteTv.text = quote.content

            if (onRvItemClickListener != null) {
                itemView.setOnClickListener { onRvItemClickListener!!.onItemClick(items[position], adapterPosition) }
                eraserIv.setOnClickListener { onRvItemClickListener!!.onItemDelete(items[position], adapterPosition) }
                shareIv.setOnClickListener { onRvItemClickListener!!.onItemShare(items[position], adapterPosition) }
            }
        }

    }

}


interface OnRvItemClickListener<Quote> {
    fun onItemClick(item: Quote, position: Int)
    fun onItemDelete(item: Quote, position: Int)
    fun onItemShare(item: Quote, position: Int)
}