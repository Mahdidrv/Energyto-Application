package energyto.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.mahdidrv.energyto.R

class QuoteAdapter: RecyclerView.Adapter<QuoteViewHolder>() {

    private var onRvItemClickListener: OnRvItemClickListener<Quote>? = null
    protected var items: List<Quote> = arrayListOf()

    fun setQuote(items: List<Quote>){
        this.items = items
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quote,parent, false)
        return QuoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(items[position])
    }

}

class QuoteViewHolder(view: View): RecyclerView.ViewHolder(view){

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
    fun bind(quote: Quote){
        authorTv.text = quote.auther
        quoteTv.text = quote.content
    }

}

/*
fun setOnRvItemClickListener(onRvItemClickListener: OnRvItemClickListener<Quote>) {
    onRvItemClickListener = onRvItemClickListener
}*/

interface OnRvItemClickListener<Quote> {
    fun onItemClick(item: Quote, position: Int)
}