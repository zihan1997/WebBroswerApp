package temple.edu.webbroswerapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrowserControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowserControlFragment extends Fragment {

    protected AddNewPageInterface parentAct;
    protected ImageButton addPageButton;

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
        if(context instanceof AddNewPageInterface){
            parentAct = (AddNewPageInterface) context;
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l =  inflater.inflate(R.layout.fragment_browser_control, container, false);

        addPageButton = l.findViewById(R.id.addPageButton);
        // Listener
        addPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentAct.addNewPage();
            }
        });

        return l;
    }

    interface AddNewPageInterface{
        void addNewPage();
    }
}