package energyto.fragment

import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.github.clans.fab.FloatingActionButton
import energyto.activity.*
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.mahdidrv.energyto.R

class QuoteFragment : BaseFragment() {

    override var layoutRes: Int = R.layout.fragment_quote
    var disposable = CompositeDisposable()
    lateinit var  mainVM: MainViewModel
    lateinit var  quoteAdapter: QuoteAdapter

    override fun setViews() {
        mainVM = MainViewModel(context)
        quoteAdapter = QuoteAdapter()

        var fab = view!!.findViewById<FloatingActionButton>(R.id.fab_add)
        var rv = view!!.findViewById<RecyclerView>(R.id.rv_quote_list)
        rv.adapter = quoteAdapter


        mainVM.allQuote
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<List<Quote>> {
                    override fun onSuccess(t: List<Quote>) {
                        Log.i("TAG", "onSuccess: " + t.size)
                        quoteAdapter.setQuote(t)

                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable.add(d)
                    }

                    override fun onError(e: Throwable) {
                        Log.i("TAG", "onSuccess: " + e.message)
                    }

                })


        fab.setOnClickListener {
            val quote = Quote("hihihi","hi")
            quoteAdapter.addQuote(quote)
            addQuote(quote)
            rv.smoothScrollToPosition(quoteAdapter.itemCount)
        }


        quoteAdapter.setOnRvItemClickListener(object : OnRvItemClickListener<Quote> {
            override fun onItemClick(item: Quote, position: Int) {
                Log.i("TAG", "click on item ${item.auther} on position $position")
            }

            override fun onItemDelete(item: Quote, position: Int) {
                removeQuote(item)
                quoteAdapter.removeQuote(item,position)
            }

            override fun onItemShare(item: Quote, position: Int) {

            }

        })


        Log.i("TAG", "onSuccess: ")
        quoteAdapter.addQuote(Quote("سلام سلام سلام", "مهدی"))
        rv.smoothScrollToPosition(0)

    }

    fun addQuote(quote: Quote){
        mainVM.insertQuote(quote)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onComplete() {
                        Log.i("TAG", "quote inserted")
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable.add(d)
                    }

                    override fun onError(e: Throwable) {
                        Log.i("TAG", "onSuccess: " + e.message)
                    }
                })

    }

    fun removeQuote(quote: Quote){
        mainVM.removeQuote(quote)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onComplete() {
                        Log.i("TAG", "quote removed")
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable.add(d)
                    }

                    override fun onError(e: Throwable) {
                        Log.i("TAG", "onSuccess: " + e.message)
                    }
                })

    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}