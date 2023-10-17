import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.sagarkhurana.quizforfun.data.User;

public class QuizSharedPref {

    private static QuizSharedPref instance = null;

    private static final String SHARED_PREFERENCES_NAME = "quiz_shared_pref";

    private SharedPreferences sharedPreferences;

    private QuizSharedPref(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static QuizSharedPref getInstance(Context context) {
        if (instance == null) {
            instance = new QuizSharedPref(context);
        }
        return instance;
    }

    public void setUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.USER_KEY, new Gson().toJson(user));
        editor.apply();
    }

    public User getUser() {
        return new Gson().fromJson(sharedPreferences.getString(Constants.USER_KEY, ""), User.class);
    }

    public void clearSharedPref() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
