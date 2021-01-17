package energyto.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import energyto.model.Quote;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyBroadcastReceiver extends BroadcastReceiver {

  private static SocketQuoteReceiver socketQuoteReceiver;
  private MainViewModel mainViewModel;

  @Override
  public void onReceive(Context context, Intent intent) {
    mainViewModel = new MainViewModel(context);

        doTheJob();
/*    new Thread(() -> {
      while (true) {
        doTheJob();
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();*/




  }

  private void doTheJob() {
    {
/*
       i don't know how work this fucking request :D
      but i use flat map to make multiple request and combine two request
      this means my current request requirement previous request response
      */

      Single<Quote> quote = mainViewModel.getAllQuote()
        .flatMap(quotes -> mainViewModel.getQuote((int) (Math.random() * quotes.size())));

      quote.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new SingleObserver<Quote>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onSuccess(Quote quote) {
            // callback to send quote to my service for change notification
            socketQuoteReceiver.onQoute(quote.getContent());
          }

          @Override
          public void onError(Throwable e) {

          }
        });

    }
  }


  public void registerCallback(SocketQuoteReceiver socketQuoteReceiver) {
    MyBroadcastReceiver.socketQuoteReceiver = socketQuoteReceiver;
  }

  interface SocketQuoteReceiver {
    void onQoute(String qoute);
  }

}
