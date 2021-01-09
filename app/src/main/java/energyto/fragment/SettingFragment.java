package energyto.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import energyto.activity.MyBroadcastReceiver;
import energyto.activity.QuotesService;
import energyto.activity.TimerAlertDialog;
import energyto.widget.TimeHelper;
import ir.mahdidrv.energyto.R;

public class SettingFragment extends Fragment {

  private AlarmManager alarmManager;
  private PendingIntent pendingIntent;
  private Intent intent;
  private Intent serviceIntent;

  private ImageView dailyNotifIv;
  private ImageView hourNotifIv;
  private ImageView cancelNotifIv;
  private ImageView creatorIv;

  private CoordinatorLayout coordinator;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_setting, container, false);
  }


  @Override
  public void onViewCreated(@NonNull View layoutView, @Nullable Bundle savedInstanceState) {

    dailyNotifIv = layoutView.findViewById(R.id.iv_daily_notif);
    hourNotifIv = layoutView.findViewById(R.id.iv_hour_notif);
    creatorIv = layoutView.findViewById(R.id.iv_creator);
    cancelNotifIv = layoutView.findViewById(R.id.iv_cancel_notif);

    coordinator = layoutView.findViewById(R.id.coordinatorLayout);


    serviceIntent = new Intent(getContext(), QuotesService.class);


    dailyNotifIv.setOnClickListener(v -> {
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
    });

    hourNotifIv.setOnClickListener(v -> {
      new TimerAlertDialog(getContext(), coordinator, time -> {
        alarmManager(time);
        getActivity().startService(serviceIntent);
      });
    });

    cancelNotifIv.setOnClickListener(v -> {
      if (QuotesService.IS_RUNNING) {
        getActivity().stopService(serviceIntent);
        alarmManager(true);
        Snackbar.make(coordinator, "خب انرژیتو دیگه بهت ناتیفیکیشن نمیده :(", Snackbar.LENGTH_LONG).show();
      } else {
        Snackbar.make(coordinator, "تایمری تنظیم نیست", Snackbar.LENGTH_LONG).show();
      }

    });

    creatorIv.setOnClickListener(v -> {
      getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://mahdidrv.ir")));
    });

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
