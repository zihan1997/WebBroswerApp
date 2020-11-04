package temple.edu.webbroswerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.ChooseInterface,
        PageViewerFragment.PageViewerInterface,
        BrowserControlFragment.AddNewPageInterface,
        PagerFragment.PagerViewInterface,
        PageListFragment.PagerListInterface{

    protected PageControlFragment pageControlFragment;
    PageViewerFragment pageViewerFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;
    PagerFragment pagerFragment;
    PageListAdapter listAdapter;



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
            pageControlFragment = PageControlFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
                    .commit();

        }


        if( (tempFragment = fm.findFragmentById(R.id.page_display) )instanceof PagerFragment){
            pagerFragment = (PagerFragment) tempFragment;
        }else{
            pagerFragment = PagerFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.page_display, pagerFragment)
                    .commit();
        }

        if( (tempFragment = fm.findFragmentById(R.id.browser_control) )instanceof BrowserControlFragment){
            browserControlFragment = (BrowserControlFragment) tempFragment;
        }else{
            browserControlFragment = BrowserControlFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.browser_control, browserControlFragment)
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
    public void chooseDirection(View view) {
        int position = pagerFragment.viewPager.getCurrentItem();
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
    public void updateTitle(WebView view) {
        setTitle(view.getTitle());
    }

    @Override
    public void updateList() {
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void addNewPage() {
        pagerFragment.adapter.addInstance();
        pagerFragment.viewPager.getAdapter().notifyDataSetChanged();
        if(findViewById(R.id.page_list) != null) {
            Log.d("pagelist", "new page added");
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPagerListener(int position) {
        String url = pagerFragment.fragments.get(position).getCurrentURL();
        // update the url
        pageControlFragment.setInputViewUrl(url);
        // update the title
        setTitle(pagerFragment.fragments.get(position).webView.getTitle());
    }

    // PageListFragment
    @Override
    public void setListViewAdapter(ListView view) {
        listAdapter = new PageListAdapter( pagerFragment.fragments, this);
        view.setAdapter(listAdapter);
    }

    @Override
    public void updateDisplay(int i) {
        pagerFragment.viewPager.setCurrentItem(i);
        Log.d("updateDisplay", "clicked");
    }
}