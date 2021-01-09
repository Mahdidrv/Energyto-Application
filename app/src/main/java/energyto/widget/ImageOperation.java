package energyto.widget;

class ImageOperation {

/*

  private Bitmap getBitmapFromView(View view) {
    try {
      DisplayMetrics displayMetrics = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
      int height = displayMetrics.heightPixels;
      int width = displayMetrics.widthPixels;

      view.setDrawingCacheEnabled(true);

      view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
      view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

      view.buildDrawingCache(true);
      Bitmap returnedBitmap = Bitmap.createBitmap(view.getDrawingCache());

      //Define a bitmap with the same size as the view
      view.setDrawingCacheEnabled(false);
      Log.i("TAG", "getBitmapFromView: ");
      return returnedBitmap;
    } catch (Exception e) {
    }
    return null;
  }


  private void storeImage(Bitmap image) {
    File pictureFile = new File(Environment.getExternalStorageDirectory() + "/test1.png");

    if (pictureFile == null) {
      Log.d("TAG",
        "Error creating media file, check storage permissions: ");// e.getMessage());
      return;
    }
    try {
      FileOutputStream fos = new FileOutputStream(pictureFile);
      image.compress(Bitmap.CompressFormat.PNG, 90, fos);
      fos.close();
    } catch (FileNotFoundException e) {
      Log.d("TAG", "File not found: " + e.getMessage());
    } catch (IOException e) {
      Log.d("TAG", "Error accessing file: " + e.getMessage());
    }

  }


  private void shareImg(Bitmap bitmap) {
    Bitmap b = bitmap;
    Intent share = new Intent(Intent.ACTION_SEND);
    share.setType("image/jpeg");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    String path = MediaStore.Images.Media.insertImage(getContentResolver(),
      b, "Title", null);
    Uri imageUri = Uri.parse(path);
    share.putExtra(Intent.EXTRA_STREAM, imageUri);
    startActivity(Intent.createChooser(share, "Select"));
  }*/

}
