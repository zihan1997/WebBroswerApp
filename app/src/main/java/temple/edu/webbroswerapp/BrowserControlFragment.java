package temple.edu.webbroswerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrowserControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowserControlFragment extends Fragment implements Serializable{

    BrowserControlInterface parentAct;

    public BrowserControlFragment() {
        // Required empty public constructor
    }


    public static BrowserControlFragment newInstance() {
        BrowserControlFragment fragment = new BrowserControlFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof BrowserControlInterface){
            parentAct = (BrowserControlInterface) context;
        }else{
            throw new RuntimeException("Please implement BrowserControlInterface");
        }
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l = inflater.inflate(R.layout.fragment_browser_control, container, false);

        l.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentAct.addPage();
            }
        });

        l.findViewById(R.id.displayBookmarkButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentAct.openBookmarkPage();
            }
        });

        l.findViewById(R.id.saveBookMarksButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    parentAct.saveCurrentToBookmarkPage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        return l;
    }

    interface BrowserControlInterface{
        void addPage();
        void saveCurrentToBookmarkPage() throws IOException;
        void openBookmarkPage();
    }

}