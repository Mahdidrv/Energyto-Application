package energyto.activity;

import android.content.Context;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

class AddDataToDb {
  MainViewModel mainViewModel;
  private Context context;
  String content;
  String auther;

  // counstructor
  public AddDataToDb(Context context) {
    this.context = context;
    mainViewModel = new MainViewModel(context);

  }

  public void addData() {

    Thread thread = new Thread(() -> {
      for (int i = 1; i < 50; i++) {

        // get All String resource by NAME
        content = getStringResourceByName("quote" + i);
        auther = getStringResourceByName("author" + i);

        // add data to database
        mainViewModel.insertQuote(new Quote(content, auther))
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
            }
          });
      }
    });

    thread.start();
  }

  // getString From Resource Id
  private String getStringResourceByName(String resourceId) {
    String packageName = context.getPackageName();
    int resId = context.getResources().getIdentifier(resourceId, "string", packageName);
    return context.getString(resId);
  }


}
