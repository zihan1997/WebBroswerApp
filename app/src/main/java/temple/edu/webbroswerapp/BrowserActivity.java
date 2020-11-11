package temple.edu.webbroswerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.ChooseInterface,
        PageViewerFragment.PageViewerInterface {

    PageControlFragment pageControlFragment;
    PageViewerFragment pageViewerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        // Set newInstance
        pageControlFragment = PageControlFragment.newInstance();
        pageViewerFragment = PageViewerFragment.newInstance();

        final FragmentManager fm = getSupportFragmentManager();
        Fragment tempFragment;

        if( (tempFragment = fm.findFragmentById(R.id.page_control)) instanceof PageControlFragment ){
            pageControlFragment = (PageControlFragment) tempFragment;
        }else {
            pageControlFragment = PageControlFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
                    .commit();
        }


        if((tempFragment = fm.findFragmentById(R.id.page_viwer)) instanceof PageViewerFragment){
            pageViewerFragment = (PageViewerFragment) tempFragment;
        }else{
            pageViewerFragment = PageViewerFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.page_viwer, pageViewerFragment)
                    .commit();
        }

    }

    @Override
    public void direction(View view) {
        switch (view.getId()){
            case R.id.goButton:// Go feature
                pageViewerFragment.loadWeb(pageControlFragment.getUserInputUrl());
                break;
            case R.id.backButton:// back
                pageViewerFragment.goBack();
                break;
            case R.id.nextButton:// next
                pageViewerFragment.goNext();
                break;
        }
    }

    @Override
    public void updateUrl(String url) {
        pageControlFragment.setInputViewUrl(url);
    }
}