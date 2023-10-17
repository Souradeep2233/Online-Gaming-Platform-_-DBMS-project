import android.app.Person;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.sagarkhurana.quizforfun.other.Converter;

@Database(
entities = {QuizUser.class, QuizAttempt.class},
exportSchema = false,
version = 1
)
public abstract class QuizDatabase extends RoomDatabase {

    public abstract QuizUserDao quizUserDao();

    private static QuizDatabase instance;

    public static QuizDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, QuizDatabase.class, "quiz_database")
                    .build();
        }
        return instance;
    }
}
