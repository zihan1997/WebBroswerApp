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

    ArrayList<PageViewerFragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        // set up pager
        fragments = new ArrayList<>();
        PageViewerFragment homePage = new PageViewerFragment();
//        homePage.loadWeb("www.google.com");
        fragments.add(homePage);


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

        if((tempFragment = fm.findFragmentById(R.id.pagerView)) instanceof PageViewerFragment){
            pageViewerFragment = (PageViewerFragment)tempFragment;
        }else {
            pageViewerFragment = PageViewerFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.page_display, pageViewerFragment)
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

        if( (tempFragment = fm.findFragmentById(R.id.page_list)) instanceof PageListFragment ){
            pageListFragment = (PageListFragment) tempFragment;
        }else{
            pageListFragment = PageListFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.page_list, pageListFragment)
                    .commit();
        }

    }

    @Override
    public void chooseDirection(View view) {
        int position = pagerFragment.viewPager.getCurrentItem();
        switch (view.getId()){
            case R.id.goButton:// Go feature
                fragments.get(position).loadWeb(pageControlFragment.getUserInputUrl());
                break;
            case R.id.backButton:// back
                fragments.get(position).goBack();
                break;
            case R.id.nextButton:// next
                fragments.get(position).goNext();
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
    public void addNewPage() {
        fragments.add(new PageViewerFragment());
        Log.d("fragments", String.valueOf(fragments.size()) );
        pagerFragment.viewPager.getAdapter().notifyDataSetChanged();
//        pageListFragment.listView.getAdapter().

    }

    @Override
    public void setViewAdapter() {

        pagerFragment.viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {

                return fragments.get(position);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
//                setTitle(super.getPageTitle(position));
                return super.getPageTitle(position);
            }

        });
    }

    @Override
    public void onPagerListener(int position) {
        String url = fragments.get(position).getCurrentURL();
        pageControlFragment.setInputViewUrl(url);
        setTitle(fragments.get(position).webView.getTitle());
    }

    @Override
    public void setListViewAdapter(ListView view) {
        PageListAdapter listAdapter = new PageListAdapter( fragments, this);
        view.setAdapter(listAdapter);
    }

    @Override
    public void updateDisplay(int i) {
        int position = pagerFragment.viewPager.getCurrentItem();
        pagerFragment.viewPager.setCurrentItem(position);
    }
}