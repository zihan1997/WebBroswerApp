package temple.edu.webbroswerapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PagerFragment extends Fragment {

    protected ViewPager viewPager;
    protected PagerViewInterface parentAct;
    private static String PagerKey = "PagerKey";
    private static String PagerVIEWKey = "PagerVIEWKey";
    private static String PagerViewAdapterKey = "PagerViewAdapterKey";
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
        outState.putParcelable(PagerVIEWKey, viewPager.onSaveInstanceState());
//        outState.putParcelable(PagerViewAdapterKey, adapter.saveState());
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
            viewPager.onRestoreInstanceState(savedInstanceState.getParcelable(PagerVIEWKey));
//            adapter.restoreState(savedInstanceState.getParcelable(PagerViewAdapterKey), getClass().getClassLoader());
        }

        // set adapter
        adapter = new ViewPagerAdapter(getChildFragmentManager(), parentAct.getFragments());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                parentAct.onPageSwitchUpdateDisplay(position);
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
        void onPageSwitchUpdateDisplay(int position);
        ArrayList<PageViewerFragment> getFragments();
    }
}