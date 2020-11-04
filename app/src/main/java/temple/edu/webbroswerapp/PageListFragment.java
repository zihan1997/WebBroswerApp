package temple.edu.webbroswerapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageListFragment extends Fragment {

    protected ListView listView;
    protected PagerListInterface parentAct;

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
        if(context instanceof PagerListInterface ){
            parentAct = (PagerListInterface) context;
        }else{
            throw  new RuntimeException(("You must implement the required interface"));
        }
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l = inflater.inflate(R.layout.fragment_page_list, container, false);

        listView = l.findViewById(R.id.urlTitleList);

        parentAct.setListViewAdapter(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                parentAct.updateDisplay(i);
                Log.d("updateDisplay", "clicked");
            }
        });

        return l;
    }

    interface PagerListInterface{
        void setListViewAdapter(ListView view);
        void updateDisplay(int i);
    }
}