package temple.edu.webbroswerapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.Serializable;

public class PageControlFragment extends Fragment implements Serializable{

    protected transient EditText userInput;

    ChooseInterface parentAct;

    public PageControlFragment() {
        // Required empty public constructor
    }

    public static PageControlFragment newInstance() {

        Bundle bundle = new Bundle();

        PageControlFragment fragment = new PageControlFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Get parent activity
        if(context instanceof ChooseInterface){
            parentAct = (ChooseInterface) context;
        }else{
            throw new RuntimeException("ChooseInterface is missing");
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
        View l = inflater.inflate(R.layout.fragment_page_control, container, false);

        // ID for editView
        userInput = l.findViewById(R.id.inputView);

        // Find IDs
        ImageButton goButton = l.findViewById(R.id.goButton);
        ImageButton backButton = l.findViewById(R.id.backButton);
        ImageButton nextButton = l.findViewById(R.id.nextButton);

        // Set listeners
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentAct.direction(view);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentAct.direction(view);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentAct.direction(view);
            }
        });

        return l;
    }

    /**
     * will correct url
     * @return corrected Url
     */
    public String getUserInputUrl(){
        String url ;
        if(userInput.getText() != null){
            String partialUrl = String.valueOf(userInput.getText());
            if(!partialUrl.startsWith("https://")){
                url = "https://" + partialUrl;
                return url;
            }else{
                return partialUrl;
            }
        }else{
            throw new RuntimeException("Please enter an URL!");
        }
    }

    public void setInputViewUrl(String url){
        userInput.setText(url);
    }


    interface ChooseInterface{
        void direction(View view);
    }
}