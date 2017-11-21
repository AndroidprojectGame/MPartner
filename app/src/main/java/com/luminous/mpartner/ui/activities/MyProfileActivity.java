package com.luminous.mpartner.ui.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.luminous.mpartner.Global;
import com.luminous.mpartner.R;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.models.Profile;
import com.luminous.mpartner.ui.custom.CircleImageView;
import com.luminous.mpartner.utils.AppSharedPrefrences;
import com.luminous.mpartner.utils.AppUtility;
import com.luminous.mpartner.utils.FontProvider;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.kobjects.base64.Base64;

import java.io.ByteArrayOutputStream;

public class MyProfileActivity extends AppCompatActivity {

    private EditText etEmail, etPhoneNumber, etAddress;
    Profile mProfileDetail;
    private CircleImageView ivImage;
    private final int REQUEST_CAMERA = 2001;
    private final int REQUEST_GALLERY = 2002;
    private Uri imageUri;
    TextView tvName, tvEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_info);

        prepareViews();
    }

    private void prepareViews() {

        mProfileDetail = (Profile) getIntent().getSerializableExtra("Profile");

        ivImage = (CircleImageView) findViewById(R.id.iv_my_info);
        tvName = (TextView) findViewById(R.id.tv_name);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPhoneNumber = (EditText) findViewById(R.id.et_phone_num);
        etAddress = (EditText) findViewById(R.id.et_address);

        //Setting typefaces
        Typeface tfRegular = FontProvider.getInstance().tfRegular;
        tvName.setTypeface(tfRegular);
        etEmail.setTypeface(tfRegular);
        etPhoneNumber.setTypeface(tfRegular);
        etAddress.setTypeface(tfRegular);

        etEmail.setEnabled(false);
        etPhoneNumber.setEnabled(false);
        etAddress.setEnabled(false);
        ivImage.setEnabled(false);

        prepareToolbar();

        if (mProfileDetail != null) {
            ImageLoader imageLoader = ImageLoader.getInstance();
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.icon_avatar) // resource or drawable
                    .showImageForEmptyUri(R.drawable.icon_avatar) // resource or drawable
                    .showImageOnFail(R.drawable.icon_avatar) // resource or drawable
                    .build();
            imageLoader.displayImage(mProfileDetail.UserImage, ivImage, options);

            tvName.setText("" + mProfileDetail.Name);
            etEmail.setText("" + mProfileDetail.Email);
            etPhoneNumber.setText("" + mProfileDetail.ContactNo);
            etAddress.setText("" + mProfileDetail.address1);
        }
    }

    private void prepareToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvName = (TextView) toolbar.findViewById(R.id.tv_title);
        tvEdit = (TextView) toolbar.findViewById(R.id.toolbar_report_tv_edit);

        tvName.setTypeface(FontProvider.getInstance().tfBold);
        tvEdit.setTypeface(FontProvider.getInstance().tfSemibold);

        tvEdit.setVisibility(View.VISIBLE);
    }

    public void goBack(View v) {
        finish();
    }

    public void updateUser(View v) {
        String mode = tvEdit.getText().toString();
        if(mode.equalsIgnoreCase("Save")) {
            BitmapDrawable drawable = (BitmapDrawable) ivImage.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_avatar);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
            byte[] byte_arr = stream.toByteArray();
            String image_str = Base64.encode(byte_arr);

            UserController userController = new UserController(this, "UpdateUserStatus");
            userController.updateProfile(etPhoneNumber.getText().toString(),
                    etEmail.getText().toString(), etAddress.getText().toString(), image_str,
                    "upload.jpg");
        }
        else {
            tvEdit.setText("Save");
            etAddress.setEnabled(true);
            etPhoneNumber.setEnabled(true);
            etEmail.setEnabled(true);
            etEmail.requestFocus();
            etEmail.setFocusableInTouchMode(true);
            ivImage.setEnabled(true);
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etEmail, InputMethodManager.SHOW_FORCED);
        }
    }

    public void UpdateUserStatus(Object object) {
        Global.showToast("Information updated successfully.");
        finish();
        if (DashboardActivity.getInstance() != null) {
            DashboardActivity.getInstance().getProfile();
        }
    }

    public void selectImageDialog(View v) {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload Photo!");
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

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
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
}
