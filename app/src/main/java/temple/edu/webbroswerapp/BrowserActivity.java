package temple.edu.webbroswerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.ChooseInterface,
        PageViewerFragment.PageViewerInterface,
        BrowserControlFragment.AddNewPageInterface,
        PagerFragment.PagerViewInterface,
        PageListFragment.PagerListInterface{

    protected PageControlFragment pageControlFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;
    PagerFragment pagerFragment;
    PageListAdapter listAdapter;

    protected ArrayList<PageViewerFragment> fragments;
    protected static String PageView_KEY = "pageViewFragmentKEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);


        if(savedInstanceState != null){
            fragments = (ArrayList<PageViewerFragment>) savedInstanceState.getSerializable(PageView_KEY);
        }else{
            fragments = new ArrayList<>();
            fragments.add(PageViewerFragment.newInstance());
        }

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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PageView_KEY, fragments);
    }

    @Override
    public void chooseDirection(View view) {
        int position = pagerFragment.viewPager.getCurrentItem();
        Log.d("control", String.valueOf(position)+" size: "+fragments.size());
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
    public void updateViewList(WebView view) {
        if(findViewById(R.id.page_list) != null){
            listAdapter = new PageListAdapter(fragments, this);
            pageListFragment.listView.setAdapter(listAdapter);
            Log.d("update", "update the view list");
            listAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void addNewPage() {
        // Add new page
//        pagerFragment.adapter.addInstance();
        fragments.add(PageViewerFragment.newInstance());

        // notify data change
        pagerFragment.viewPager.getAdapter().notifyDataSetChanged();
        if(findViewById(R.id.page_list) != null) {
            listAdapter.notifyDataSetChanged();
        }

        // go the new page
        int position = fragments.size()-1;
        this.switchDisplay(position);
    }

    @Override
    public void onPageSwitchUpdateDisplay(int position) {
        String url = fragments.get(position).webView.getUrl();
        // update the url
        pageControlFragment.setInputViewUrl(url);
        // update the title
        setTitle(fragments.get(position).webView.getTitle());
    }

    @Override
    public ArrayList<PageViewerFragment> getFragments() {
        return fragments;
    }

    // PageListFragment
    @Override
    public void setListViewAdapter(ListView view) {
        listAdapter = new PageListAdapter(fragments, this);
        view.setAdapter(listAdapter);
    }

    @Override
    public void switchDisplay(int i) {
        pagerFragment.viewPager.setCurrentItem(i, true);
        Log.d("updateDisplay", "clicked");
    }
}