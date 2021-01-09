package energyto.widget;

import android.app.Activity;
import android.app.TimePickerDialog;

import java.util.Calendar;

public class TimeHelper {

  public static void openTimePicker(Activity activity, int hour, int min, boolean is24Formated, TimePickerDialog.OnTimeSetListener listener) {
    show(activity, listener, hour, min, is24Formated);
  }

  public static void openTimePicker(Activity activity, int hour, int min, TimePickerDialog.OnTimeSetListener listener) {
    show(activity, listener, hour, min, false);
  }

  public static void openTimePicker(Activity activity, boolean is24Formated, TimePickerDialog.OnTimeSetListener listener) {
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int min = calendar.get(Calendar.MINUTE);
    show(activity, listener, hour, min, is24Formated);
  }

  public static void openTimePicker(Activity activity, TimePickerDialog.OnTimeSetListener listener) {
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int min = calendar.get(Calendar.MINUTE);
    show(activity, listener, hour, min, false);
  }


  private static void show(Activity activity, TimePickerDialog.OnTimeSetListener listener, int hour, int minutes, boolean is24Formated) {

    new TimePickerDialog(activity, listener, hour, minutes, is24Formated).show();

  }
}
