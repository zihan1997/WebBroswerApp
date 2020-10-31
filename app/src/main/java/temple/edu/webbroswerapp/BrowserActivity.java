package temple.edu.webbroswerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.ChooseInterface,
        PageViewerFragment.PageViewerInterface,
        BrowserControlFragment.AddNewPageInterface {

    PageControlFragment pageControlFragment;
    PageViewerFragment pageViewerFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;
    PagerFragment pagerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);


        // If fragments already added
        final FragmentManager fm = getSupportFragmentManager();
        Fragment tempFragment;

        if((tempFragment = fm.findFragmentById(R.id.page_control)) instanceof  PageControlFragment){
            pageControlFragment = (PageControlFragment)tempFragment;
        }else{
            fm.beginTransaction()
                    .add(R.id.page_control, PageControlFragment.newInstance())
                    .commit();

        }

        if((tempFragment = fm.findFragmentById(R.id.page_viwer)) instanceof PageViewerFragment){
            pageViewerFragment = (PageViewerFragment)tempFragment;
        }else {
            fm.beginTransaction()
                    .add(R.id.page_viwer, PageViewerFragment.newInstance())
                    .commit();
        }

        // Set newInstance
        pageListFragment = PageListFragment.newInstance();
        pagerFragment = PagerFragment.newInstance();



        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.page_control, pageControlFragment)
                .add(R.id.page_viwer, pageViewerFragment)
                .add(R.id.browser_control, browserControlFragment)
                .commit();
    }

    @Override
    public void chooseDirection(View view) {
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
    public void addNewPage() {

    }

    @Override
    public void updateUrl(String url) {
        pageControlFragment.setInputViewUrl(url);
    }
}