package temple.edu.webbroswerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.ChooseInterface,
        PageViewerFragment.PageViewerInterface,
        PagerFragment.PagerFragmentInterface,
        BrowserControlFragment.BrowserControlInterface,
        PageListFragment.PageListInterface {

    PageControlFragment pageControlFragment;
    PagerFragment pagerFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;


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
            if( (tempFragment = fm.findFragmentById(R.id.page_list)) instanceof PageListFragment ){
                pageListFragment = (PageListFragment) tempFragment;
            }else{
                pageListFragment = PageListFragment.newInstance();
                fm.beginTransaction()
                        .add(R.id.page_list, pageListFragment)
                        .commit();
            }
        }
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

        if(findViewById(R.id.page_list) != null){
            pageListFragment.notifyChange();
        }
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
    public ArrayList<PageViewerFragment> getPagerFragment() {
        if(pagerFragment.fragments != null) {
            return pagerFragment.fragments;
        }else{
            return null;
        }
    }

    @Override
    public void selectPage(int i) {
        pagerFragment.vPager.setCurrentItem(i, true);
    }
}