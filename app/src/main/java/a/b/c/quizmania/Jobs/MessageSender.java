package a.b.c.quizmania.Jobs;

import com.google.firebase.auth.FirebaseAuth;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MessageSender {
    public void sendMessage(String id){
        String to = id;
        String url = "https://us-central1-vikna-20adf.cloudfunctions.net";
        OkHttpClient client = new OkHttpClient();

        FirebaseAuth.getInstance().getCurrentUser().


        Request request = new Request.Builder()
                .url(String
                        .format("%s/sendNotification?to=%s&fromPushId=%s&fromId=%s&fromName=%s&type=%s",
                                url,
                                to,
                                me.getPushId(),,
                                getCurrentUserId(),
                                me.getName(),
                                "invite"))
                .build();
    }

}
