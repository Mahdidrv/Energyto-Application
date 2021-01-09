package energyto.activity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Quote.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
  private static AppDatabase appDatabase;
  private static MainViewModel mainViewModel;

  public abstract QuoteDao quoteDao();

  public static AppDatabase getInstance(Context context) {
    if (appDatabase == null) {
      appDatabase = Room.databaseBuilder(context, AppDatabase.class, "db_energyto2").addCallback(new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
          super.onCreate(db);
          AddDataToDb addDataToDb = new AddDataToDb(context);
          addDataToDb.addData();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
          super.onOpen(db);
        }
      })
        .build();
    }
    return appDatabase;
  }

}
