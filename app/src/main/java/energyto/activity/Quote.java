package energyto.activity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_quote")
public class Quote {
  @PrimaryKey(autoGenerate = true)
  private int id;
  @ColumnInfo(name = "quote_content")
  private String content;
  @ColumnInfo(name = "quote_auther")
  private String auther;

  public Quote(String content, String auther) {
    this.content = content;
    this.auther = auther;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getAuther() {
    return auther;
  }

  public void setAuther(String auther) {
    this.auther = auther;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
