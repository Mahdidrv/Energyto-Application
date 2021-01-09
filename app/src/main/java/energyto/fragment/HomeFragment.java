package energyto.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import energyto.activity.MainViewModel;
import energyto.activity.MyBroadcastReceiver;
import energyto.activity.Quote;
import energyto.activity.QuotesService;
import energyto.activity.TimerAlertDialog;
import energyto.activity.TypeWriter;
import energyto.widget.TimeHelper;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.mahdidrv.energyto.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

  private TypeWriter quoteTv;
  private TypeWriter authorTv;
  private ConstraintLayout nextLayout;
  private MainViewModel mainViewModel;
  private Intent serviceIntent;
  private CoordinatorLayout coordinator;

  private AlarmManager alarmManager;
  private PendingIntent pendingIntent;
  private Intent intent;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


    mainViewModel = new MainViewModel(getContext());

    getNewQuote();

    serviceIntent = new Intent(getContext(), QuotesService.class);

    quoteTv = view.findViewById(R.id.tv_main_quote);
    authorTv = view.findViewById(R.id.tv_main_thinker);
    coordinator = view.findViewById(R.id.coordinator);
    nextLayout = view.findViewById(R.id.layout_next);
    nextLayout.setOnClickListener(v -> {
        getNewQuote();
      }
    );

    FloatingActionButton test = view.findViewById(R.id.fab_hour);
    test.setOnClickListener(v -> {
      new TimerAlertDialog(getContext(), coordinator, time -> {
        alarmManager(time);
        getActivity().startService(serviceIntent);
      });
      }
    );

    nextLayout.setOnClickListener(v -> {
        getNewQuote();
      }
    );
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

  @Override
  public void onClick(View v) {
    switch (v.getId()) {

      case R.id.fab_day:
        TimeHelper.openTimePicker(getActivity(), true, (view, hour, minute) -> {
          Calendar calNow = Calendar.getInstance();
          Calendar calSet = (Calendar) calNow.clone();

          calSet.set(Calendar.SECOND, 0);
          calSet.set(Calendar.MINUTE, minute);
          calSet.set(Calendar.HOUR, hour);

          if (calSet.compareTo(calNow) <= 0) {
            calSet.add(Calendar.DATE, 1);
          }

          Snackbar.make(coordinator, "هر روز ساعت " + hour + " و " + minute + " دقیقه بهت یه جمله نشون میدم", Snackbar.LENGTH_LONG).show();
          getActivity().startService(serviceIntent);
          alarmManager(calSet.getTimeInMillis());

        });

        break;
      case R.id.fab_hour:
        new TimerAlertDialog(getContext(), coordinator, time -> {
          alarmManager(time);
          getActivity().startService(serviceIntent);
        });
        break;
      case R.id.fab_off:
        if (QuotesService.IS_RUNNING) {
          getActivity().stopService(serviceIntent);
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

    alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
    intent = new Intent(getContext(), MyBroadcastReceiver.class);
    pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);
    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), time, pendingIntent);
  }

  private void alarmManager(boolean mode) {
    alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
    intent = new Intent(getContext(), MyBroadcastReceiver.class);
    pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);
    alarmManager.cancel(pendingIntent);

  }

}
