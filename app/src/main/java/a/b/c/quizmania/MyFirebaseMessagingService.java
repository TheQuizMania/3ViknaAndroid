package a.b.c.quizmania;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import a.b.c.quizmania.UI.ChallengeListActivity;
import a.b.c.quizmania.UI.MultiPlayerResultsActivity;

import static android.support.v4.app.NotificationCompat.PRIORITY_MAX;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    final String CHALLENGE = "challenge";
    final String RESULT = "result";
    Intent challengeReceived;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String fromName = remoteMessage.getData().get("fromName");
        String type = remoteMessage.getData().get("type");
        String challengeID = remoteMessage.getData().get("challengeID");

        if(type.equals(CHALLENGE)) {
            challengeReceived = new Intent(getApplicationContext(), ChallengeListActivity.class);

            PendingIntent pendingIntentChallenge = PendingIntent.getActivity(this, 0, challengeReceived, PendingIntent.FLAG_UPDATE_CURRENT);

            android.app.Notification build = new NotificationCompat.Builder(this, CHALLENGE)
                    .setSmallIcon(R.drawable.web_hi_res_512)
                    .setPriority(PRIORITY_MAX)
                    .setContentTitle(String.format("%s has challenged you to a battle of wits!", fromName))
                    .setVibrate(new long[3000])
                    .setChannelId(CHALLENGE)
                    .setContentIntent(pendingIntentChallenge)
                    .build();

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager == null) {
                return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHALLENGE, CHALLENGE, importance);
                notificationManager.createNotificationChannel(mChannel);
            }

            notificationManager.notify(1, build);
        }

        else if(type.equals(RESULT)){
            Intent challengeReceived = new Intent(getApplicationContext(), MultiPlayerResultsActivity.class);
            challengeReceived.putExtra("challengeID", challengeID);
            PendingIntent pendingIntentChallenge = PendingIntent.getActivity(this, 0, challengeReceived, PendingIntent.FLAG_UPDATE_CURRENT);

            android.app.Notification build = new NotificationCompat.Builder(this, RESULT)
                    .setSmallIcon(R.drawable.web_hi_res_512)
                    .setPriority(PRIORITY_MAX)
                    .setContentTitle(String.format("%s has completed the quiz!", fromName))
                    .setVibrate(new long[3000])
                    .setChannelId(RESULT)
                    .setContentIntent(pendingIntentChallenge)
                    .build();

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager == null) {
                return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(RESULT, RESULT, importance);
                notificationManager.createNotificationChannel(mChannel);
            }

            notificationManager.notify(1, build);
        }

        else{
            return;
        }
    }
}