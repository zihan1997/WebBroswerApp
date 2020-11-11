package temple.edu.webbroswerapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class PageControlFragment extends Fragment {

    protected EditText userInput;
    private ImageButton goButton;
    private ImageButton backButton;
    private ImageButton nextButton;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l = inflater.inflate(R.layout.fragment_page_control, container, false);

        // ID for editView
        userInput = l.findViewById(R.id.inputView);

        // Find IDs
        goButton = l.findViewById(R.id.goButton);
        backButton = l.findViewById(R.id.backButton);
        nextButton = l.findViewById(R.id.nextButton);

        // Set listeners
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentAct.chooseDirection(view);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentAct.chooseDirection(view);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentAct.chooseDirection(view);
            }
        });

        return l;
    }

    /**
     * will correct url
     * @return corrected Url
     */
    public String getUserInputUrl(){
        String url = "";
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
        void chooseDirection(View view);
    }
}