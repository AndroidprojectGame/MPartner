package com.luminous.mpartner.ui.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.luminous.mpartner.Global;
import com.luminous.mpartner.R;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.models.Function;
import com.luminous.mpartner.utils.AppSharedPrefrences;
import com.luminous.mpartner.utils.AppUtility;
import com.luminous.mpartner.utils.FontProvider;
import com.luminous.mpartner.utils.Navigator;

import org.kobjects.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SuggestionsActivity extends AppCompatActivity {

    private ArrayList<Function> mFunctionsNames;
    private Spinner spFunctions;
    private ImageView ivImage;
    private EditText etSuggestion;
    private final int REQUEST_CAMERA = 2001;
    private final int REQUEST_GALLERY = 2002;
    private final int REQUEST_CAMERA_USE = 200;
    private Uri imageUri;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        prepareViews();
    }

    private void prepareViews() {
        spFunctions = (Spinner) findViewById(R.id.activity_suggestions_sp_functions);
        ivImage = (ImageView) findViewById(R.id.activity_suggestions_iv_attachment);
        etSuggestion = (EditText) findViewById(R.id.activity_suggestions_et_suggestion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.tv_title);
        title.setText("Suggestion");
        title.setTypeface(FontProvider.getInstance().tfBold);

        getFunctions();
    }

    private void getFunctions() {
        UserController userController = new UserController(this, "updateFunctions");
        userController.getFunctionNames();
    }


    public void updateFunctions(Object object) {
        mFunctionsNames = ((UserController) object).mFunctionNames;
        if (mFunctionsNames != null) {
            //Global.showToast("" + mFunctionsNames.size());
            spFunctions.setAdapter(new ArrayAdapter<Function>(this, android.R.layout.simple_dropdown_item_1line, mFunctionsNames));
        }
    }

    public void goBack(View v) {
        finish();
    }

    public void openSuggestions(View v) {
        Navigator.getInstance().navigateToActivity(this, SuggestionsActivity.class);
    }

    public void selectImageDialog(View v) {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Attachment!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    takeImageFromCamera();
                } else if (items[item].equals("Choose from Library")) {
                    takeImageFromGallery();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void takeImageFromCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_USE);

            return;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_USE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "Permission Granted");
                } else {
                    Log.e(TAG, "Permission Denied");
                }
                return;
            }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void takeImageFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap thumbnail = null;
            if (requestCode == REQUEST_CAMERA) {
                thumbnail = (Bitmap) data.getExtras().get("data");
                imageUri = data.getData();

            } else if (requestCode == REQUEST_GALLERY) {
                try {
                    Uri selectedImage = data.getData();
                    imageUri = selectedImage;
                    thumbnail = AppUtility.getBitmap(getApplicationContext(), selectedImage);

                } catch (Exception e) {
                    e.printStackTrace();
                    Global.showToast("Please select another image");
                }
            }
            AppSharedPrefrences.getInstance(this).setProfilePicPath(AppUtility.profileImgsaveToInternalSorage(this, thumbnail));
            ivImage.setImageBitmap(thumbnail);
        }
    }

    public void sendSuggestion(View v) {
        String image_str = null;
        if (etSuggestion.getText().toString().trim().length() > 0) {
            BitmapDrawable drawable = (BitmapDrawable) ivImage.getDrawable();
            if(drawable!=null)
            {
                Bitmap bitmap = drawable.getBitmap();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_avatar);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
                byte[] byte_arr = stream.toByteArray();
                image_str = Base64.encode(byte_arr);
            }

            UserController userController = new UserController(this, "UpdateSuggestion");
            userController.sendSuggestion(mFunctionsNames.get(spFunctions.getSelectedItemPosition()).Id,
                    etSuggestion.getText().toString(), image_str==null?"":image_str,
                    "upload.jpg");
        } else {
            Global.showToast("Please write suggestion");
        }
    }

    public void UpdateSuggestion(Object object) {
        Global.showToast("Suggestion sent successfully.");
        finish();
    }

}
