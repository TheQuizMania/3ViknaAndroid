package a.b.c.quizmania.Jobs;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import a.b.c.quizmania.utilities.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MessageSender {

    public void MessageSender(){}

    public void sendMessage(String id){
        String to = id;
        String url = "https://us-central1-vikna-20adf.cloudfunctions.net/sendNotification2";
        OkHttpClient client = new OkHttpClient();

        String me = FirebaseInstanceId.getInstance().getToken();
        String myID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String myName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();


        Request request = new Request.Builder()
                .url(String
                        .format("%s/sendNotification?to=%s&fromPushId=%s&fromId=%s&fromName=%s&type=%s",
                                url,
                                to,
                                me,
                                myID,
                                myName,
                                "invite"))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    public void onNewToken() {

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String newToken = FirebaseInstanceId.getInstance().getToken();

        Utility.updatePushToken(userID, newToken);
        }

}
