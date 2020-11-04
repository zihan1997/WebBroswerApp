package temple.edu.webbroswerapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PagerFragment extends Fragment {

    protected ViewPager viewPager;
    protected PagerViewInterface parentAct;
    protected ArrayList<PageViewerFragment> fragments;
    private String PagerKey = "PagerKey";
    ViewPagerAdapter adapter;

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(PagerKey, fragments);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l = inflater.inflate(R.layout.fragment_pager, container, false);

        // find PagerView, set listener
        viewPager = l.findViewById(R.id.pagerView);

        if(savedInstanceState != null){
            fragments = (ArrayList<PageViewerFragment>) savedInstanceState.getSerializable(PagerKey);
            Log.d("fragments", String.valueOf(fragments.size()));
        }else{
            fragments = new ArrayList<>();
            fragments.add(PageViewerFragment.newInstance());
        }

        // set adapter
        adapter = new ViewPagerAdapter(getChildFragmentManager(), fragments);

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
//                fragments.get(position).getCurrentURL()
                parentAct.onPagerListener(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
        });


        return l;
    }

    interface PagerViewInterface {
        void onPagerListener(int position);
    }

    protected class ViewPagerAdapter extends FragmentStatePagerAdapter{

        ArrayList<PageViewerFragment> fragments;
        public ViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<PageViewerFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        public void addInstance(){
            fragments.add(PageViewerFragment.newInstance());
            notifyDataSetChanged();
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @NonNull
        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}