import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;

import a.b.c.quizmania.Entities.UserListItem;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    UserListItem uli;

    @Override
    public void onNewToken(String s) {

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String newToken = s;



    }





}



