package energyto.widget;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import ir.mahdidrv.energyto.R;

public class TimerAlertDialog {


  private Context context;
  private int selectedItem = -1;

  public TimerAlertDialog(Context context, CoordinatorLayout coordinator, onTime onTime) {
    this.context = context;
    CharSequence[] charSequence = new CharSequence[]{"نیم ساعت", "یک ساعت", "دو ساعت", "چهار ساعت", "هشت ساعت"};
    AlertDialog.Builder aBuilder = new AlertDialog.Builder(context);
    aBuilder.setTitle("هر چند ساعت میخوای ناتیفیکیشن نمایش داده بشه؟");
    aBuilder.setSingleChoiceItems(charSequence, -1, (dialog, which) -> {
      selectedItem = which;
    });

    aBuilder.setIcon(R.drawable.ic_timer_primary);
    aBuilder.setPositiveButton("ثبت تایمر", (dialog, which) -> {
      switch (selectedItem) {
        case 0:
          setData(onTime, AlarmManager.INTERVAL_HOUR / 2, "انرژیتو هر نیم ساعت بهت یه نوتیفیکیشن نشون میده", coordinator);
          break;
        case 1:
          setData(onTime, AlarmManager.INTERVAL_HOUR, "انرژیتو هر یک ساعت بهت یه نوتیفیکیشن نشون میده", coordinator);
          break;
        case 2:
          setData(onTime, AlarmManager.INTERVAL_HOUR * 2, "انرژیتو هر دو ساعت بهت یه نوتیفیکیشن نشون میده", coordinator);
          break;
        case 3:
          setData(onTime, AlarmManager.INTERVAL_HOUR * 4, "انرژیتو هر چهار ساعت بهت یه نوتیفیکیشن نشون میده", coordinator);
          break;
        case 4:
          setData(onTime, AlarmManager.INTERVAL_HOUR * 8, "انرژیتو هر هشت ساعت بهت یه نوتیفیکیشن نشون میده", coordinator);
          break;
      }

    });

    aBuilder.setCancelable(true);
    aBuilder.setNegativeButton("بیخیال", (dialog, which) -> dialog.dismiss());

    aBuilder.show();
  }

  private void setData(onTime onTime, Long timeBack, String toast, View coordinator) {
    onTime.onTimeBack(timeBack);
    Snackbar.make(coordinator, toast, Snackbar.LENGTH_LONG).show();
  }

  public interface onTime {
    void onTimeBack(Long time);
  }
}
