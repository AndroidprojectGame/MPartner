package com.luminous.mpartner.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.luminous.mpartner.R;
import com.luminous.mpartner.adapters.ContactUsAdapter;
import com.luminous.mpartner.adapters.ProductsAdapter;
import com.luminous.mpartner.controllers.UserController;
import com.luminous.mpartner.models.ContactUs;
import com.luminous.mpartner.models.Product;
import com.luminous.mpartner.utils.FontProvider;
import com.luminous.mpartner.utils.Navigator;

import java.util.ArrayList;

public class ContactUsActivity extends AppCompatActivity {

    ArrayList<ContactUs> mContactUs;
    private ListView lvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        prepareViews();

    }

    private void prepareViews() {
        lvContacts = (ListView) findViewById(R.id.activity_contact_us_lv_contacts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.tv_title);
        title.setText("Contact Us");
        title.setTypeface(FontProvider.getInstance().tfBold);

        getContactUs();

    }

    private void getContactUs() {
        UserController userController = new UserController(this, "updateContactUs");
        userController.getContactUs();
    }

    public void updateContactUs(Object object) {
        mContactUs = ((UserController) object).mContactUs;
        if (mContactUs != null) {

            lvContacts.setAdapter(new ContactUsAdapter(this, mContactUs));
        }
    }

    public void goBack(View v) {
        finish();
    }

    public void openSuggestions(View v) {
        Navigator.getInstance().navigateToActivity(this, SuggestionsActivity.class);
    }

    public void openFb(View v) {
        openLink(getString(R.string.url_fb));
    }

    public void openTwitter(View v) {
        openLink(getString(R.string.url_twitter));
    }

//    public void openBlog(View v) {
//        openLink(getString(R.string.url_blog));
//    }

    public void openYoutube(View v) {
        openLink(getString(R.string.url_youtube));
    }

//    public void openGPlus(View v) {
//        openLink(getString(R.string.url_gplus));
//    }

    public void openWebpage(View v) {
        openLink(getString(R.string.url_web));
    }

    private void openLink(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

}
