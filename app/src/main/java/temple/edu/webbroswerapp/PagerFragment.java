package temple.edu.webbroswerapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  PagerFragment extends Fragment implements Serializable{

    protected ArrayList<PageViewerFragment> fragments;
    protected FragmentPagerAdapter adapter;
    protected ViewPager vPager;

    private static String Fragment_KEY = "Fragment_KEY";
    private static String vPager_KEY = "vPager_KEY";
    private PagerFragmentInterface parentAct;


    public PagerFragment() {
        // Required empty public constructor
    }

    public static PagerFragment newInstance() {
//        PagerFragment fragment = new PagerFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return new PagerFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof PagerFragmentInterface){
            parentAct = (PagerFragmentInterface) context;
        }else{
            throw new RuntimeException("Please implement PagerFragmentInterface");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Fragment_KEY, fragments);
        outState.putParcelable(vPager_KEY, vPager.onSaveInstanceState());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if(savedInstanceState == null){
            fragments = new ArrayList<>();
            fragments.add(PageViewerFragment.newInstance());
        }else{
            Log.d("-----", "fragment restores");
            fragments = (ArrayList<PageViewerFragment>) savedInstanceState.getSerializable(Fragment_KEY);

        }

        adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                if(fragments.get(position) == null){
                    fragments.add(PageViewerFragment.newInstance());
                }
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                Object ret = super.instantiateItem(container, position);
                fragments.set(position, (PageViewerFragment) ret);
                return ret;
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                if (fragments.contains(object))
                    return fragments.indexOf(object);
                else
                    return POSITION_NONE;
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l = inflater.inflate(R.layout.fragment_pager, container, false);

        vPager = l.findViewById(R.id.viewPager);

        if(savedInstanceState != null){
            Log.d("-----", "VPager restores");
            vPager.onRestoreInstanceState(savedInstanceState.getParcelable(vPager_KEY));
        }
        vPager.setOffscreenPageLimit(4);
        vPager.setAdapter(adapter);
        vPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                parentAct.currentPageChanged(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return l;
    }

    public void addPage(){
        fragments.add(PageViewerFragment.newInstance());
        adapter.notifyDataSetChanged();
    }

    interface PagerFragmentInterface{
        void currentPageChanged(int position);
    }
}