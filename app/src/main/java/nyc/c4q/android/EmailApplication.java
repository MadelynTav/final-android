package nyc.c4q.android;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;

import java.util.List;

import nyc.c4q.android.model.Email;
import nyc.c4q.android.rest.FakeEmailService;
import nyc.c4q.android.ui.EmailDetailActivity;

public class EmailApplication extends Application {
  public static final int EMAIL_POLL_IN_SEC = 5;

  public static final int MILLIS_PER_SEC = 1000;
  public static final int DELAY_MILLIS = EMAIL_POLL_IN_SEC * MILLIS_PER_SEC;

  private static final FakeEmailService emailService = new FakeEmailService();

  private HandlerThread handlerThread;
  private NotificationManager notificationManager;
  private Runnable emailCheck;

  @Override public void onCreate() {
    super.onCreate();

    // TODO - finish this
    notificationManager = null;

    handlerThread = new HandlerThread("email-timer");
    handlerThread.start();
    Looper looper = handlerThread.getLooper();
    final Handler handler = new Handler(looper);

    emailCheck = new Runnable() {
      @Override public void run() {
        if (emailService.hasNewMail()) {
            List<Email> emails= emailService.getEmails();
            Email email= emails.get(emails.size()-1);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(EmailApplication.this)
                          .setContentTitle(getResources().getString(R.string.you_got_email))
                          .setContentText(getResources().getString(R.string.notification_email_from));

          Intent resultIntent = new Intent(EmailApplication.this, EmailDetailActivity.class);
          resultIntent.putExtra("email",email);

          TaskStackBuilder sB = TaskStackBuilder.create(EmailApplication.this);
          sB.addParentStack(EmailDetailActivity.class);
          sB.addNextIntent(resultIntent);
          PendingIntent pendingIntent = sB.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
          builder.setContentIntent(pendingIntent);
          NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
          mNotificationManager.notify(123, builder.build());

          // 1) get the most recent email and..
          // a) send a notification to the user notifying of the new email
          // b) use R.string.you_got_email as title
          // c) use R.string.notification_email_from (accounting for who sent the email)
          // d) when user clicks on notification, go to EmailDetailActivity

        }


        handler.postDelayed(emailCheck, DELAY_MILLIS);
      }
    };

    handler.postDelayed(emailCheck, DELAY_MILLIS);
  }
}
