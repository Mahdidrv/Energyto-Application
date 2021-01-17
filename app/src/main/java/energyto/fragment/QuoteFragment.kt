package energyto.fragment

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.github.clans.fab.FloatingActionButton
import energyto.main.*
import energyto.base.BaseFragment
import energyto.model.Quote
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.mahdidrv.energyto.R

class QuoteFragment : BaseFragment(){

    override var layoutRes: Int = R.layout.fragment_quote
    var disposable = CompositeDisposable()
    lateinit var  mainVM: MainViewModel
    lateinit var  quoteAdapter: QuoteAdapter
    lateinit var  bottomSheet: AddQuoteBottomSheetDialog

    override fun setViews() {
        mainVM = MainViewModel(context)
        quoteAdapter = QuoteAdapter()
        bottomSheet = AddQuoteBottomSheetDialog()

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
            bottomSheet.show(activity!!.supportFragmentManager, "BottomSheetEx")
        }

        bottomSheet.setAddQuoteCallBack(object: AddQuoteCallBack<Quote>{
            override fun add(quote: Quote) {
                Toast.makeText(context,"جمله ی شما اضافه شد :)",Toast.LENGTH_LONG).show()
                quoteAdapter.addQuote(quote)
                rv.smoothScrollToPosition(quoteAdapter.itemCount)
                addQuote(quote)
            }

        })


        quoteAdapter.setOnRvItemClickListener(object : OnRvItemClickListener<Quote> {
            override fun onItemClick(item: Quote, position: Int) {
                Log.i("TAG", "click on item ${item.auther} on position $position")
            }

            override fun onItemDelete(item: Quote, position: Int) {
                removeQuote(item)
                quoteAdapter.removeQuote(item,position)
            }

            override fun onItemShare(item: Quote, position: Int) {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.putExtra(Intent.EXTRA_TEXT, "${item.content} \n ${item.auther}")
                shareIntent.type = "text/plain"
                startActivity(Intent.createChooser(shareIntent,"گزینهای را انتخاب کنید"))
            }

        })
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