package temple.edu.webbroswerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity
        implements Serializable,
        PageControlFragment.ChooseInterface,
        PageViewerFragment.PageViewerInterface,
        PagerFragment.PagerFragmentInterface,
        BrowserControlFragment.BrowserControlInterface,
        PageListFragment.PageListInterface {

    PageControlFragment pageControlFragment;
    transient PagerFragment pagerFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;

    private final String title_KEY = "title";
    private final String url_KEY = "url";
    private final String FILE_NAME = "bookmarkList";
    private final int REQUEST_CODE = 1111;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        final FragmentManager fm = getSupportFragmentManager();
        Fragment tempFragment;

        if( (tempFragment = fm.findFragmentById(R.id.browser_control)) instanceof BrowserControlFragment){
            browserControlFragment = (BrowserControlFragment) tempFragment;
        }else{
            browserControlFragment = BrowserControlFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.browser_control, browserControlFragment)
                    .commit();
        }

        if( (tempFragment = fm.findFragmentById(R.id.page_control)) instanceof PageControlFragment ){
            pageControlFragment = (PageControlFragment) tempFragment;
        }else {
            pageControlFragment = PageControlFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
                    .commit();
        }


        if((tempFragment = fm.findFragmentById(R.id.page_display)) instanceof PagerFragment){
            pagerFragment = (PagerFragment) tempFragment;
        }else{
            pagerFragment = PagerFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.page_display, pagerFragment)
                    .commit();
        }

        if(findViewById(R.id.page_list) != null){
            Log.d("-----", "there is page List");
            if( (tempFragment = fm.findFragmentById(R.id.page_list)) instanceof PageListFragment ){
                pageListFragment = (PageListFragment) tempFragment;
            }else{
                pageListFragment = PageListFragment.newInstance();
                fm.beginTransaction()
                        .add(R.id.page_list, pageListFragment)
                        .commit();
            }
        }

//        if(getIntent() != null){
//            Uri uri = getIntent().getData();
//            if (uri != null && pagerFragment.vPager != null) {
//                String uri_string = "URI: " + uri.toString();
//                Log.d("uri", uri_string);
//                int index = pagerFragment.vPager.getCurrentItem();
//                pagerFragment.fragments.get(index).loadWeb(uri_string);
//            }
//        }

    }

    @Override
    public void direction(View view) {
        int position = pagerFragment.vPager.getCurrentItem();

        switch (view.getId()){
            case R.id.goButton:// Go feature
                pagerFragment.fragments.get(position).loadWeb(pageControlFragment.getUserInputUrl());
                break;
            case R.id.backButton:// back
                pagerFragment.fragments.get(position).goBack();
                break;
            case R.id.nextButton:// next
                pagerFragment.fragments.get(position).goNext();
                break;
        }
    }

    @Override
    public void updateUrl(String url) {
        pageControlFragment.setInputViewUrl(url);
    }

    @Override
    public void updateTitle(String title) {
        setTitle(title);
    }

    @Override
    public void updateViewList() {
        if(findViewById(R.id.page_list) != null){
            pageListFragment.notifyChange();
        }
    }

    @Override
    public void currentPageChanged(int position) {
        // update title and url in page control
        String url = pagerFragment.fragments.get(position).getCurrentURL();
        pageControlFragment.setInputViewUrl(url);

        String title = pagerFragment.fragments.get(position).getTitle();
        setTitle(title);

//        if(findViewById(R.id.page_list) != null){
//            pageListFragment.notifyChange();
//        }
    }

    @Override
    public void addPage() {
        pagerFragment.addPage();

        int position = pagerFragment.fragments.size()-1;

        pagerFragment.vPager.setCurrentItem(position, true);
        pageControlFragment.setInputViewUrl("");
        setTitle("");

        if(findViewById(R.id.page_list) != null){
            pageListFragment.notifyChange();
        }
    }

    @Override
    public void saveCurrentToBookmarkPage() throws IOException {
        // 1. get current page from pager
        int position = pagerFragment.vPager.getCurrentItem();

        // 2. add to bookmark arrayList
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(title_KEY, getPagerFragment().get(position).getTitle());
            jsonObject.put(url_KEY, getPagerFragment().get(position).getCurrentURL());
        }catch (JSONException e){
            e.printStackTrace();
        }
//        Toast.makeText(this, jsonObject.toString(), Toast.LENGTH_SHORT).show();

        // 3. write to the same file
        File file = new File(getFilesDir(),FILE_NAME);
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("\n"+jsonObject.toString());
        bufferedWriter.close();

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void openBookmarkPage() {
        Intent intent = new Intent(this, BookmarksActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        assert data != null;
        if(resultCode == RESULT_OK) {
            String title = data.getStringExtra(title_KEY);
            String url = data.getStringExtra(url_KEY);
            Log.d("getIntent--", title + " " + url);

            // open new tap for the bookmark
            addPage();
            int size = getPagerFragment().size();
            pagerFragment.vPager.setCurrentItem(size - 1);
            getPagerFragment().get(size - 1).loadWeb(url);
        }
    }

    @Override
    public ArrayList<PageViewerFragment> getPagerFragment() {
        return pagerFragment.fragments;
    }

    @Override
    public void selectPage(int i) {
        pagerFragment.vPager.setCurrentItem(i, true);
    }



    /*
     * Option Menu
     * */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            // get current page
            int position = -1;
            position = pagerFragment.vPager.getCurrentItem();

            String title = getPagerFragment().get(position).getTitle();
            String url = getPagerFragment().get(position).getCurrentURL();


            // send implicit intent
            if( title != null && url != null) {
                // Create the text message with a string
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                String textMessage = title + " " + url;
                sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);
                sendIntent.setType("text/plain");
                // Verify that the intent will resolve to an activity
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(sendIntent);
                }
            }else {
                Toast.makeText(this, "open an url first before sharing", Toast.LENGTH_LONG).show();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}