package temple.edu.webbroswerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.ChooseInterface{

    PageControlFragment pageControlFragment;
    PageViewerFragment pageViewerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        // Set newInstance
        pageControlFragment = PageControlFragment.newInstance();
        pageViewerFragment = PageViewerFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.page_control, pageControlFragment)
                .add(R.id.page_viwer, pageViewerFragment)
                .commit();
    }

    @Override
    public void chooseDirection(View view) {
        String url = "";
        switch (view.getId()){
            case R.id.goButton:// Go feature
                url = pageViewerFragment.loadWeb(pageControlFragment.getUserInputUrl());
                break;
            case R.id.backButton:// back
                url = pageViewerFragment.goBack();
                break;
            case R.id.nextButton:// next
                url = pageViewerFragment.goNext();
                break;
        }
        // change the corresponding url
        if(url != null){
            pageControlFragment.setInputViewUrl(pageViewerFragment.getCurrentURL());
        }
    }
}