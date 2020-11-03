package temple.edu.webbroswerapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PageListAdapter extends BaseAdapter {

    private ArrayList<PageViewerFragment> fragments;
    private Context context;

    public PageListAdapter(ArrayList<PageViewerFragment> fragments, Context context){
        this.fragments = fragments;
        this.context = context;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Object getItem(int i) {
        return fragments.get(i).webView.getTitle();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView;

        if(view == null){
            textView = new TextView(context);
            textView.setPadding(5,5,5,5);
            textView.setTextSize(20);
                textView.setText(fragments.get(i).webView.getTitle());
        }else {
            textView = (TextView) view;
        }


        return textView;
    }
}
