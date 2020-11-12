package temple.edu.webbroswerapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebBackForwardList;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.Serializable;


public class PageViewerFragment extends Fragment {

    protected PageViewerInterface parentAct;
    protected WebView webView;
    private String KEY_WebView = "WebView";

    public PageViewerFragment() {
        // Required empty public constructor
    }

    public static PageViewerFragment newInstance() {

        Bundle args = new Bundle();
        PageViewerFragment fragment = new PageViewerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        webView.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof PageViewerInterface){
            parentAct = (BrowserActivity) context;
        }else{
            throw new RuntimeException("PageViewerFragment is not implemented");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        webView = l.findViewById(R.id.webView);


        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                view.loadUrl(request.getUrl().toString());
//                return true;
//            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                ((BrowserActivity)getActivity()).pageControlFragment.setInputViewUrl(url);
                parentAct.updateUrl(url);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                parentAct.updateTitle(view.getTitle());
                parentAct.updateViewList();
            }
        });


        if(savedInstanceState != null){
            webView.restoreState(savedInstanceState);
            webView.reload();
        }

        return l;
    }

    public String getCurrentURL(){
        return webView.getUrl();
    }

    public String getTitle(){
        return webView.getTitle();
    }


    @SuppressLint("SetJavaScriptEnabled")
    public void loadWeb(String url){
        webView.loadUrl(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void goBack(){
        // Check if the key event was the Back button and if there's history
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void goNext(){
        // Check if the key event was the next button and if there's history
        if (webView.canGoForward()) {
            webView.goForward();
        }
    }

    interface PageViewerInterface{
        void updateUrl(String url);
        void updateTitle(String title);
        void updateViewList();
    }
}