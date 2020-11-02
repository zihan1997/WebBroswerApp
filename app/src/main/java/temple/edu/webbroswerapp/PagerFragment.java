package temple.edu.webbroswerapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PagerFragment extends Fragment {

    protected ViewPager viewPager;
    protected PagerViewInterface parentAct;

    public PagerFragment() {
        // Required empty public constructor
    }

    public static PagerFragment newInstance() {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof PagerViewInterface){
            parentAct = (PagerViewInterface) context;
        }else{
            throw new RuntimeException("Please implement setAdapterInterface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l = inflater.inflate(R.layout.fragment_pager, container, false);

        // find PagerView, set listener
        viewPager = l.findViewById(R.id.pagerView);

        // set adapter
        parentAct.setViewAdapter();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("========", String.valueOf(position));
                parentAct.onPagerListener(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return l;
    }

    interface PagerViewInterface {
        void setViewAdapter();
        void onPagerListener(int position);
    }
}