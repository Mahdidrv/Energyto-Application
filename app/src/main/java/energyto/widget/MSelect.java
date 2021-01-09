package energyto.widget;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

public class MSelect {


  private Activity activiy;

  private CharSequence title;
  private CharSequence[] options;
  private CharSequence okLabel = "OK";
  private CharSequence cancelLabel = "Cancel";

  private int defaultValue = -1;
  private int selectedIndex = -1;

  private boolean isImmediate = false;
  private boolean isCancelable = true;

  private AlertDialog dialog;
  private OnItemSelectedListener listener;

  // get Context
  public MSelect(Activity activity) {
    this.activiy = activity;
  }

  // set Dialog title
  public MSelect setTitle(CharSequence title) {
    this.title = title;
    return this;
  }

  // set Dialog Item with ... (vararg in Kotlin)
  public MSelect setOption(CharSequence... options) {
    this.options = options;
    return this;
  }


  // set Negitave text with String
  public MSelect setCancelLabel(CharSequence cancelLabel) {
    this.cancelLabel = cancelLabel;
    return this;
  }

  // set Positive text with String
  public MSelect setOkLabel(CharSequence okLabel) {
    this.okLabel = okLabel;
    return this;
  }

  // set Negitave text with Resourse
  public MSelect setCancelLabel(@StringRes int cancelLabel) {
    this.cancelLabel = activiy.getString(cancelLabel);
    return this;
  }

  // set Positive text with Resourse
  public MSelect setOkLabel(@StringRes int okLabel) {
    this.okLabel = activiy.getString(okLabel);
    return this;
  }


  public interface OnItemSelectedListener {
    void OnItemSelected(int selectedIndex, CharSequence selectedValue);
  }


  public MSelect setIsCancelable(boolean isCancelable) {
    this.isCancelable = isCancelable;
    return this;
  }

  // get index with value
  private int search(String key) {
    for (int i = 0; i < options.length; i++) {
      if (options[i].equals(key))
        return i;
    }
    return -1;
  }

  // set default selected item with string
  public MSelect setDefaultValue(String defaultValue) {
    this.defaultValue = search(defaultValue);
    selectedIndex = this.defaultValue;
    return this;
  }

  // set default selected item with index
  public MSelect setDefaultValue(int defaultValue) {
    this.defaultValue = defaultValue;
    selectedIndex = defaultValue;
    return this;
  }

  // do somethings when select an item without click button
  public MSelect setIsImmediate(boolean isImmediate) {
    this.isImmediate = isImmediate;
    return this;
  }

  // listener
  public MSelect setOnItemSelectedListener(OnItemSelectedListener listener) {
    this.listener = listener;
    return this;
  }

  public void show() {
    AlertDialog.Builder builder = new AlertDialog.Builder(activiy);
    builder.setTitle(title);
    builder.setSingleChoiceItems(options, defaultValue, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int witch) {
        selectedIndex = witch;

        if (isImmediate) {
          if (listener != null) {
            listener.OnItemSelected(selectedIndex, options[selectedIndex]);
          }
          dialog.dismiss();
        }
      }
    });

    builder.setCancelable(isCancelable);

    if (!isImmediate) {
      builder.setPositiveButton(okLabel, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          if (listener != null) {
            listener.OnItemSelected(selectedIndex, options[selectedIndex]);
          }
        }
      });
    }

    if (isCancelable) {
      builder.setNegativeButton(cancelLabel, null);
    }

    dialog = builder.create();
    dialog.show();
  }
}
