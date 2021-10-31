package kg.zima.gesvitaly.zanozakg.fragments;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.master.permissionhelper.PermissionHelper;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import kg.zima.gesvitaly.zanozakg.BuildConfig;
import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.activities.MainActivity;

import static android.app.Activity.RESULT_OK;

public class CallCenterFragment extends Fragment {
    private File file;
    private Uri uri;
    private Intent CamIntent, GalIntent;
    private LinearLayout fl_selectedPhoto;

    private int REQUEST_CAMERA = 3, SINGLE_SELECT_GALLERY = 2, MULTI_SELECT_GALLERY = 1, RESULT_CROP = 400, SELECT_FILE = 4;
    private ArrayList<Uri> photoList = new ArrayList<>();

    private PermissionHelper cameraAndStorageHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_call_center, container, false);
        fl_selectedPhoto = (LinearLayout) rootView.findViewById(R.id.fl_selectedPhoto);

        Button cameraBtn = (Button) rootView.findViewById(R.id.camera_btn);
        Button chooseSingleFromGalleryBtn = (Button) rootView.findViewById(R.id.choose_single_from_gallery_btn);
        Button chooseMultiFromGalleryBtn = (Button) rootView.findViewById(R.id.choose_multi_from_gallery_btn);
        Button chooseFileBtn = (Button) rootView.findViewById(R.id.choose_file_btn);

        //region Button clicks
        cameraBtn.setOnClickListener(v -> {
            cameraAndStorageHelper = new PermissionHelper(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 100);
            cameraAndStorageHelper.request(new PermissionHelper.PermissionCallback() {

                @Override
                public void onPermissionGranted() {
                    ClickImageFromCamera();
                }

                @Override
                public void onIndividualPermissionGranted(@NotNull final String[] strings) {
                    //not needed
                }

                @Override
                public void onPermissionDenied() {
                    Toast.makeText(getContext(), "Разрешите доступ к камере и памяти устройства, чтобы снять фото", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionDeniedBySystem() {
                    Toast.makeText(getContext(), "Перейдите в настройки и разрешите доступ к камере и памяти устройства, чтобы снять фото", Toast.LENGTH_LONG).show();
                }
            });
        });

        chooseSingleFromGalleryBtn.setOnClickListener(v -> {
            cameraAndStorageHelper = new PermissionHelper(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            cameraAndStorageHelper.request(new PermissionHelper.PermissionCallback() {
                @Override
                public void onPermissionGranted() {
                    SingleGetImageFromGallery();
                }

                @Override
                public void onIndividualPermissionGranted(@NotNull final String[] strings) {
                    //not needed
                }

                @Override
                public void onPermissionDenied() {
                    Toast.makeText(getContext(), "Разрешите доступ к памяти устройства, чтобы выбрать фото", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionDeniedBySystem() {
                    Toast.makeText(getContext(), "Перейдите в настройки и разрешите доступ к памяти устройства, чтобы выбрать фото", Toast.LENGTH_LONG).show();
                }
            });
        });

        chooseMultiFromGalleryBtn.setOnClickListener(v -> {
            cameraAndStorageHelper = new PermissionHelper(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            cameraAndStorageHelper.request(new PermissionHelper.PermissionCallback() {
                @Override
                public void onPermissionGranted() {
                    multiGetImageFromGallery();
                }

                @Override
                public void onIndividualPermissionGranted(@NotNull final String[] strings) {
                    //not needed
                }

                @Override
                public void onPermissionDenied() {
                    Toast.makeText(getContext(), "Разрешите доступ к памяти устройства, чтобы выбрать фото", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionDeniedBySystem() {
                    Toast.makeText(getContext(), "Перейдите в настройки и разрешите доступ к памяти устройства, чтобы выбрать фото", Toast.LENGTH_LONG).show();
                }
            });
        });

        chooseFileBtn.setOnClickListener(v -> {
            cameraAndStorageHelper = new PermissionHelper(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            cameraAndStorageHelper.request(new PermissionHelper.PermissionCallback() {
                @Override
                public void onPermissionGranted() {
                    chooseFile();
                }

                @Override
                public void onIndividualPermissionGranted(@NotNull final String[] strings) {
                    //not needed
                }

                @Override
                public void onPermissionDenied() {
                    Toast.makeText(getContext(), "Разрешите доступ к памяти устройства, чтобы выбрать файлы", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionDeniedBySystem() {
                    Toast.makeText(getContext(), "Перейдите в настройки и разрешите доступ к памяти устройства, чтобы выбрать файлы", Toast.LENGTH_LONG).show();
                }
            });
        });
        // endregion

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Call-центр");
        ((MainActivity) getActivity()).checkNavigationItem(R.id.nav_about);
    }

    // Camera function
    private void ClickImageFromCamera() {
        file = new File(Environment.getExternalStorageDirectory(), "file" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        if (android.os.Build.VERSION.SDK_INT > 23) {
            uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        CamIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        CamIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        CamIntent.putExtra("return-data", true);
        startActivityForResult(CamIntent, REQUEST_CAMERA);

    }

    // single image pick function
    private void SingleGetImageFromGallery() {
        file = new File(Environment.getExternalStorageDirectory(), "file" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        if (android.os.Build.VERSION.SDK_INT > 23) {
            uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        GalIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalIntent, "Select Image From Gallery"), SINGLE_SELECT_GALLERY);
    }

    // multi image pick function
    private void multiGetImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), MULTI_SELECT_GALLERY);
    }

    //Any File pick function
    private void chooseFile() {
        //TODO: uncomment when add dependencies
        /*Intent i2 = new Intent(getContext(), FileChooser.class);
        i2.putExtra(Constants.SELECTION_MODE,Constants.SELECTION_MODES.MULTIPLE_SELECTION.ordinal());
        startActivityForResult(i2, SELECT_FILE);*/
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("*/*");
//        startActivityForResult(intent, SELECT_FILE);
        /*FilePickerBuilder.getInstance()
                .setActivityTheme(R.style.AppTheme)
                .pickFile(this);*/
        /*Intent intent = new Intent(getContext(), FileChooserActivity.class);
        startActivityForResult(intent, SELECT_FILE);*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (cameraAndStorageHelper != null) {
            cameraAndStorageHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) { //Camera result
                if (android.os.Build.VERSION.SDK_INT > 23) {
                    ImageCrop();
                } else {
                    ImageCropFunction();
                }
            } else if (requestCode == SINGLE_SELECT_GALLERY) {// One Image result
                uri = data.getData();
                String url = uri.toString();
                if (url.startsWith("content://com.google.android.apps.photos.content")) {
                    try {
                        InputStream is = getActivity().getContentResolver().openInputStream(uri);
                        if (is != null) {
                            Bitmap pictureBitmap = BitmapFactory.decodeStream(is);
                            uri = getImageUri(getContext(), pictureBitmap);
                            ImageCrop();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    ImageCrop();
                }

            } else if (requestCode == MULTI_SELECT_GALLERY) { // Multi Image result
                photoList.clear();
                fl_selectedPhoto.removeAllViews();

                if (data.getClipData() != null) {

                    int count=data.getClipData().getItemCount();
                    Toast.makeText(getContext(), count+" Image Selected", Toast.LENGTH_SHORT).show();

                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        uri = item.getUri();
                        photoList.add(uri);
                    }
                } else if (data.getClipData() == null) {
                    Toast.makeText(getContext(), "1 Image Selected", Toast.LENGTH_SHORT).show();
                    uri = data.getData();
                    photoList.add(uri);
                }
                AddPhotoOnLayout();
            } else if (requestCode == RESULT_CROP) { //Crop Image result
                if (data != null) {
                    uri = getImageContentUri(getContext(), file);
                    photoList.clear();
                    fl_selectedPhoto.removeAllViews();
                    photoList.add(uri);
                    AddPhotoOnLayout();
                }
            }
            else if (requestCode == SELECT_FILE) { //any file Result
                //TODO: uncomment when add dependencies
                /*if (data != null) {
                    ArrayList<Uri> selectedFiles = data.getParcelableArrayListExtra(Constants.SELECTED_ITEMS);
                    for (Uri file : selectedFiles){
                        String fileName = file.toString();
                        if(fileName.contains(".")) {
                            String type = fileName.substring(fileName.lastIndexOf("."));
                            switch (type) {
                                case ".pdf":
                                    addToSlider(type, R.drawable.pdf);
                                    break;
                                case ".jpg":
                                    addToSlider(type, file);
                                    break;
                                case ".jpeg":
                                    addToSlider(type, R.drawable.jpg);
                                    break;
                                case ".docx":
                                    addToSlider(type, R.drawable.doc);
                                    break;
                                case ".doc":
                                    addToSlider(type, R.drawable.doc);
                                    break;
                                case ".gif":
                                    addToSlider(type, R.drawable.gif);
                                    break;
                                case ".ppt":
                                    addToSlider(type, R.drawable.ppt);
                                    break;
                                case ".pptx":
                                    addToSlider(type, R.drawable.ppt);
                                    break;
                                case ".xls":
                                    addToSlider(type, R.drawable.xls);
                                    break;
                                case ".xlsx":
                                    addToSlider(type, R.drawable.xls);
                                    break;
                                case ".zip":
                                    addToSlider(type, R.drawable.zip);
                                    break;
                                case ".txt":
                                    addToSlider(type, R.drawable.txt);
                                    break;
                                default:
                                    addToSlider(type, R.drawable.icon_media);
                                    break;
                            }
                        }
                    }
                }*/
                /*fl_selectedPhoto.removeAllViews();
                String fileSelected = data.getStringExtra(Constants.KEY_FILE_SELECTED);
                if(fileSelected.contains(".")) {
                    String type = fileSelected.substring(fileSelected.lastIndexOf("."));
                    switch (type) {
                        case ".pdf":
                            addToSlider(type, R.drawable.pdf);
                            break;
                        case ".jpg":
                            addToSlider(type, R.drawable.jpg);
                            break;
                        case ".jpeg":
                            addToSlider(type, R.drawable.jpg);
                            break;
                        case ".docx":
                            addToSlider(type, R.drawable.doc);
                            break;
                        case ".doc":
                            addToSlider(type, R.drawable.doc);
                            break;
                        case ".gif":
                            addToSlider(type, R.drawable.gif);
                            break;
                        case ".ppt":
                            addToSlider(type, R.drawable.ppt);
                            break;
                        case ".pptx":
                            addToSlider(type, R.drawable.ppt);
                            break;
                        case ".xls":
                            addToSlider(type, R.drawable.xls);
                            break;
                        case ".xlsx":
                            addToSlider(type, R.drawable.xls);
                            break;
                        case ".zip":
                            addToSlider(type, R.drawable.zip);
                            break;
                        case ".txt":
                            addToSlider(type, R.drawable.txt);
                            break;
                        default:
                            addToSlider(type, R.drawable.icon_media);
                            break;
                    }
                }
                else {
                    Toast.makeText(getContext(), "file extension not showing", Toast.LENGTH_SHORT).show();
                }*/
            }
        }
    }

    private void addToSlider(String type, int icon) {
        Toast.makeText(getContext(), "File: " + type, Toast.LENGTH_SHORT).show();

        ImageView image = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 130);
        lp.setMargins(0, 0, getResources().getDimensionPixelSize(R.dimen.fab_margin), 0);
        int smallMargin = getResources().getDimensionPixelSize(R.dimen.small_margin);
        lp.setMargins(0, smallMargin, smallMargin, smallMargin);
        image.setLayoutParams(lp);
        Glide.with(requireContext())
                .load(icon)
                .centerCrop()
                .into(image);

        fl_selectedPhoto.addView(image);
    }

    private void addToSlider(String type, Uri uri) {
        Toast.makeText(getContext(), "File: " + type, Toast.LENGTH_SHORT).show();

        ImageView image = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 130);
        int smallMargin = getResources().getDimensionPixelSize(R.dimen.small_margin);
        lp.setMargins(0, smallMargin, smallMargin, smallMargin);
        image.setLayoutParams(lp);
        Glide.with(requireContext())
                .load(uri)
                .centerCrop()
                .into(image);

        fl_selectedPhoto.addView(image);
    }

    private void AddPhotoOnLayout() {
        for (int i = 0; i <= photoList.size() - 1; i++) {
            ImageView image = new ImageView(getContext());
            image.setId(i);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, 200);
            lp.setMargins(210, 0, 0, 0);
           /* lp.addRule(RelativeLayout.CENTER_IN_PARENT);*/
            image.setLayoutParams(lp);
            Glide.with(requireContext())
                    .load(photoList.get(i))
                    .fitCenter()
                    .into(image);

            fl_selectedPhoto.addView(image);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    //Scrop function for min SDK 23
    private void ImageCropFunction() {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(uri, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 128);
        cropIntent.putExtra("outputY", 128);
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, RESULT_CROP);
    }

    //Scrop function for max SDK 23
    private void ImageCrop() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 4);
        intent.putExtra("outputX", 180);
        intent.putExtra("outputY", 180);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, RESULT_CROP);
    }
}
