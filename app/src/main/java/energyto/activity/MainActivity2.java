package energyto.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.mahdidrv.energyto.R;


/*

  this is my first application in android platform :)))
  checkout my blog: mahdidrv.ir

*/


public class MainActivity2 extends AppCompatActivity {

  private TypeWriter quoteTv;
  private TypeWriter authorTv;
  private ConstraintLayout nextLayout;
  private MainViewModel mainViewModel;
  private Intent serviceIntent;
  private CoordinatorLayout coordinator;

  private AlarmManager alarmManager;
  private PendingIntent pendingIntent;
  private Intent intent;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.notif_layout);


    mainViewModel = new MainViewModel(this);

    setupView();
    subscribe();
    getNewQuote();

    serviceIntent = new Intent(this, QuotesService.class);


  }


  private void setupView() {
    quoteTv = findViewById(R.id.tv_main_quote);
    authorTv = findViewById(R.id.tv_main_thinker);
    coordinator = findViewById(R.id.coordinator);
    nextLayout = findViewById(R.id.layout_next);
    nextLayout.setOnClickListener(v -> {
        getNewQuote();
      }
    );

  }

  private void subscribe() {

  }

  private void getNewQuote() {
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
          quoteTv.setCharacterDelay(30);
          quoteTv.animateText(quote.getContent());

          authorTv.setCharacterDelay(30);
          authorTv.animateText(quote.getAuther());
        }

        @Override
        public void onError(Throwable e) {

        }
      });


  }
/*

  @Override
  public void onClick(View v) {
    switch (v.getId()) {

      case R.id.fab_day:
        TimeHelper.openTimePicker(MainActivity2.this, true, (view, hour, minute) -> {
          Calendar calNow = Calendar.getInstance();
          Calendar calSet = (Calendar) calNow.clone();

          calSet.set(Calendar.SECOND, 0);
          calSet.set(Calendar.MINUTE, minute);
          calSet.set(Calendar.HOUR, hour);

          if (calSet.compareTo(calNow) <= 0) {
            calSet.add(Calendar.DATE, 1);
          }

          Snackbar.make(coordinator, "هر روز ساعت " + hour + " و " + minute + " دقیقه بهت یه جمله نشون میدم", Snackbar.LENGTH_LONG).show();
          startService(serviceIntent);
          alarmManager(calSet.getTimeInMillis());

        });

        break;
      case R.id.fab_hour:
        new TimerAlertDialog(MainActivity2.this, coordinator, time -> {
          alarmManager(time);
          startService(serviceIntent);
        });
        break;
      case R.id.fab_off:
        if (QuotesService.IS_RUNNING) {
          stopService(serviceIntent);
          alarmManager(true);
          Snackbar.make(coordinator, "خب انرژیتو دیگه بهت ناتیفیکیشن نمیده :(", Snackbar.LENGTH_LONG).show();
        } else {
          Snackbar.make(coordinator, "تایمری تنظیم نیست", Snackbar.LENGTH_LONG).show();
        }


        break;
      case R.id.fab_share:
        Snackbar.make(coordinator, "تو ریلیز بعدی حلش می کنیم :)", Snackbar.LENGTH_LONG).show();
        break;
    }
  }

  private void alarmManager(long time) {

    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    intent = new Intent(MainActivity2.this, MyBroadcastReceiver.class);
    pendingIntent = PendingIntent.getBroadcast(MainActivity2.this, 1, intent, 0);
    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), time, pendingIntent);
  }

  private void alarmManager(boolean mode) {
    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    intent = new Intent(MainActivity2.this, MyBroadcastReceiver.class);
    pendingIntent = PendingIntent.getBroadcast(MainActivity2.this, 1, intent, 0);
    alarmManager.cancel(pendingIntent);

  }

*/

}
