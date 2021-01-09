package energyto.fragment

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import energyto.activity.BaseFragment
import energyto.activity.MainViewModel
import energyto.activity.Quote
import energyto.activity.QuoteAdapter
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.mahdidrv.energyto.R

class QuoteFragment : BaseFragment() {

    override var layoutRes: Int = R.layout.fragment_quote
    var disposable = CompositeDisposable()

    override fun setViews() {
        val mainVM = MainViewModel(context)
        val quoteAdapter = QuoteAdapter()
        var rv = view!!.findViewById<RecyclerView>(R.id.rv_quote_list)
        rv.adapter = quoteAdapter

        mainVM.allQuote
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: SingleObserver<List<Quote>>{
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
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}