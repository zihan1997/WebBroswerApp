package temple.edu.webbroswerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class BookmarksActivity extends AppCompatActivity implements BookMarkInterface{

    protected ArrayList<String> bookmarksList;
    private String urlName = "url";
    protected BookMarkAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        // create the arraylist
        bookmarksList = new ArrayList<>();

        // Adapter
        adapter = new BookMarkAdapter(bookmarksList, this,this );

        // get view
        RecyclerView recyclerView = findViewById(R.id.listView);
        recyclerView.setAdapter(adapter);




    }

    @Override
    public void bookmarkClicked(int position) {
        Intent urlIntent = new Intent(BookmarksActivity.this, BrowserActivity.class);
        urlIntent.putExtra(urlName, bookmarksList.get(position));
        startActivity(urlIntent);
    }

    @Override
    public void bookmarkDeleted(int position) {
        // TODO dialog

        // delete from list
        bookmarksList.remove(position);
        adapter.notifyDataSetChanged();
    }
}