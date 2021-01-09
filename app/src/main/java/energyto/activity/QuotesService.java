package energyto.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;
import ir.mahdidrv.energyto.R;

public class QuotesService extends Service implements MyBroadcastReceiver.SocketQuoteReceiver {


  private static final int NOTIFICATION_ID = 5002;
  private static final String NOTIFICATION_CHANNEL = "ir.mahdidrc";
  private NotificationManager notificationManager;
  private NotificationCompat.Builder notification;
  private Notification newContentNotify;
  RemoteViews remoteViews;
  public static boolean IS_RUNNING = false;


  @Override
  public void onCreate() {
    IS_RUNNING = true;
    MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    myBroadcastReceiver.registerCallback(this);
    notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL, "default", NotificationManager.IMPORTANCE_LOW);
      notificationManager.createNotificationChannel(notificationChannel);
    }

    super.onCreate();
  }


  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {

    // convert to foreground service

    newNotif("از زندگی\u200Cات رؤیا و از رؤیاهایت واقعیت بساز.");
    return super.onStartCommand(intent, flags, startId);
  }


  @Override
  public void onDestroy() {
    IS_RUNNING = false;
    super.onDestroy();
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

/*
  public void updateNotify(String quotes) {
    remoteViews.setImageViewBitmap(R.id.IvTitle, textToBitmap(this, quotes));
    newContentNotify = notification.build();
    notificationManager.notify(NOTIFICATION_ID, newContentNotify);

  }

  public void createNotification() {
    notification.setSmallIcon(R.drawable.ic_bolt_notifaction);
    notification.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
    remoteViews = new RemoteViews(getPackageName(), R.layout.notif_collapsed);
    notification.setCustomContentView(remoteViews);
    notification.setSound(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.juntos));
    newContentNotify = notification.build();
    startForeground(NOTIFICATION_ID, newContentNotify);

  }*/

  public void newNotif(String quotes) {

    notification = new NotificationCompat.Builder(QuotesService.this, NOTIFICATION_CHANNEL);
    notification.setSmallIcon(R.drawable.ic_bolt_notifaction);
    notification.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
    remoteViews = new RemoteViews(getPackageName(), R.layout.notif_collapsed);
    notification.setCustomContentView(remoteViews);
    notification.setSound(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.juntos));
    remoteViews.setImageViewBitmap(R.id.IvTitle, textToBitmap(this, quotes));
    newContentNotify = notification.build();
    notificationManager.notify(NOTIFICATION_ID, newContentNotify);
    startForeground(NOTIFICATION_ID, newContentNotify);
  }

  @Override
  public void onQoute(String qoute) {
    newNotif(qoute);
  }


  private String getArray(String[] input, int from, int to) {
    StringBuilder splitText = new StringBuilder();
    for (int i = from; i < to; i++) {
      splitText.append(input[i]).append(" ");
    }
    return splitText.toString();

  }


  private Bitmap textToBitmap(Context context, String text) {
    String[] quote = new String[4];
    String[] bar = text.split(" ");
    if (bar.length < 8) {
      quote[0] = getArray(bar, 0, bar.length);
    }
    if (bar.length >= 8) {
      quote[0] = getArray(bar, 0, 8);
      quote[1] = getArray(bar, 8, bar.length);
    }
    if (bar.length >= 16) {
      quote[0] = getArray(bar, 0, 8);
      quote[1] = getArray(bar, 8, 16);
      quote[2] = getArray(bar, 16, bar.length);
    }
    if (bar.length >= 21) {
      quote[0] = getArray(bar, 0, 8);
      quote[1] = getArray(bar, 8, 16);
      quote[2] = getArray(bar, 16, 21);
      quote[3] = getArray(bar, 21, bar.length);
    }

    Typeface typeFace = ResourcesCompat.getFont(context, R.font.iransans_bold);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    paint.setTextSize(43);
    paint.setColor(getResources().getColor(R.color.text_color));
    paint.setTextAlign(Paint.Align.RIGHT);
    paint.setTypeface(typeFace);
    Bitmap image = Bitmap.createBitmap(1080, 300, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(image);
    float baseline = 100;

    for (String s : quote) {

      if (s != null) {
        canvas.drawText(s, 1000, baseline, paint);
        baseline += 60;
      }
    }


    return image;

  }
}
