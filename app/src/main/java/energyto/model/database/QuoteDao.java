package energyto.model.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import energyto.model.Quote;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface QuoteDao {

  @Query("select * from tbl_quote where id in (:id)")
  Single<Quote> getQuote(int id);

  @Query("select * from tbl_quote")
  Single<List<Quote>> getAllQuote();

  @Insert
  Completable insert(Quote quote);

  @Insert
  void insertWithoutRx(Quote quote);

  @Query("DELETE FROM tbl_quote")
  void deleteAll();

  @Delete
  Completable remove(Quote quote);

}
