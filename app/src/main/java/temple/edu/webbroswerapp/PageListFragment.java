package temple.edu.webbroswerapp;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageListFragment extends Fragment {

    protected PageListAdapter adapter;
    protected ArrayList<PageViewerFragment> fragments;
    protected PageListInterface parentAct;
    protected ListView listView;

    public PageListFragment() {
        // Required empty public constructor
    }


    public static PageListFragment newInstance() {
        PageListFragment fragment = new PageListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof PageListInterface ){
            parentAct = (PageListInterface) context;
        }else{
            throw  new RuntimeException(("You must implement the required interface"));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }


//        adapter = new ListAdapter() {
//            @Override
//            public boolean areAllItemsEnabled() {
//                return false;
//            }
//
//            @Override
//            public boolean isEnabled(int i) {
//                return false;
//            }
//
//            @Override
//            public void registerDataSetObserver(DataSetObserver dataSetObserver) {
//
//            }
//
//            @Override
//            public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
//
//            }
//
//            @Override
//            public int getCount() {
//                return fragments.size();
//            }
//
//            @Override
//            public Object getItem(int i) {
//                return fragments.get(i);
//            }
//
//            @Override
//            public long getItemId(int i) {
//                return 0;
//            }
//
//            @Override
//            public boolean hasStableIds() {
//                return false;
//            }
//
//            @Override
//            public View getView(int i, View view, ViewGroup viewGroup) {
//                TextView textView;
//
//                if(view == null){
//                    textView = new TextView(getContext());
//                    textView.setPadding(5,5,5,5);
//                    textView.setTextSize(20);
//                    textView.setText(fragments.get(i).webView.getTitle());
//                }else {
//                    textView = (TextView) view;
//                }
//
//                return textView;
//            }
//
//            @Override
//            public int getItemViewType(int i) {
//                return 0;
//            }
//
//            @Override
//            public int getViewTypeCount() {
//                return 1;
//            }
//
//            @Override
//            public boolean isEmpty() {
//                return false;
//            }
//        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l = inflater.inflate(R.layout.fragment_page_list, container, false);

        listView = l.findViewById(R.id.viewList);

        if(parentAct.getPagerFragment() !=null){
            fragments = parentAct.getPagerFragment();
        }
        adapter = new PageListAdapter(fragments, getActivity());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                parentAct.selectPage(i);
            }
        });

        return l;
    }

    public void notifyChange(){
        adapter = new PageListAdapter(fragments, getActivity());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    interface PageListInterface{
        ArrayList<PageViewerFragment> getPagerFragment();
        void selectPage(int i);
    }

    protected static class PageListAdapter extends BaseAdapter {

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
}