package energyto.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import energyto.model.Quote
import ir.mahdidrv.energyto.R

open class AddQuoteBottomSheetDialog() : BottomSheetDialogFragment() {

    private var addQuoteCallBack: AddQuoteCallBack<Quote>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.bottom_sheet_add_quote, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val content = view.findViewById<TextInputEditText>(R.id.edt_addQuote_content)
        val author = view.findViewById<TextInputEditText>(R.id.edt_addQuote_author)
        val submit = view.findViewById<Button>(R.id.btn_addQuote)

        submit.setOnClickListener {
            addQuoteCallBack!!.add(Quote(content.text.toString(), author.text.toString()))
            content.setText(null)
            author.setText(null)
            dismiss()
        }

    }

    fun setAddQuoteCallBack(addQuoteCallBack: AddQuoteCallBack<Quote>) {
        this.addQuoteCallBack = addQuoteCallBack
    }
}


interface AddQuoteCallBack<Quote> {
    fun add(quote: Quote)
}