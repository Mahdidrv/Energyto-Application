package energyto.activity;

import android.content.Context;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class MainViewModel {
  private Context context;
  private QuoteDao quoteDao;

  public MainViewModel(Context context) {
    this.context = context;
    AppDatabase appDatabase = AppDatabase.getInstance(context);
    quoteDao = appDatabase.quoteDao();
  }

  public Completable insertQuote(Quote quote) {
    return quoteDao.insert(quote);
  }

  public void insertQuoteWithoutRx(Quote quote) {
    quoteDao.insertWithoutRx(quote);
  }

  public Single<Quote> getQuote(int id) {
    return quoteDao.getQuote(id);
  }

  public Single<List<Quote>> getAllQuote() {
    return quoteDao.getAllQuote();
  }

  public void deleteAll() {
    quoteDao.deleteAll();
  }
}
