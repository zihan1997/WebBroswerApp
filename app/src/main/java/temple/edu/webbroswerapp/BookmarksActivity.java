package temple.edu.webbroswerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class BookmarksActivity extends AppCompatActivity implements BookMarkInterface{

    protected ArrayList<CustomBookmarks> bookmarksList;
    private final String FILE_NAME = "bookmarkList";
    private String url_KEY = "url";
    private String title_KEY = "title";
    protected BookMarkAdapter adapter;
    protected LinearLayoutManager manager;
    private final int REQUEST_CODE = 1111;

    StringBuilder stringBuilder;
    JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        // create the array list
        bookmarksList = new ArrayList<>();
        setTitle("Bookmark");

        try {
            Log.d("load bookmark", "load bookmark");
            loadBookmarkFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        bookmarksList.add(new CustomBookmarks("Google", "www.google.com"));
//        bookmarksList.add(new CustomBookmarks("Amazon", "www.amazon.com"));


        // Adapter
        adapter = new BookMarkAdapter(bookmarksList, this,this );

        // get view
        final RecyclerView recyclerView = findViewById(R.id.listView);

        // linear layout manager
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BookmarksActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.closebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void loadBookmarkFile() throws IOException {

        // read file
        File file = new File(getFilesDir(), FILE_NAME);
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while(line != null){

            // To Json
            try{
                jsonObject = new JSONObject(line);

                String oneTitle = jsonObject.getString(title_KEY);
                String oneUrl = jsonObject.getString(url_KEY);
                Log.d("------", "1. "+oneTitle+" "+oneUrl);

                // To CustomBookmark
                CustomBookmarks oneBookmark = new CustomBookmarks(oneTitle, oneUrl);
                bookmarksList.add(oneBookmark);

            }catch (JSONException e){
                e.printStackTrace();
            }

            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        Log.d("---", stringBuilder.toString());

    }

    @Override
    public void bookmarkClicked(int position) {
//        Intent urlIntent = new Intent(BookmarksActivity.this, BrowserActivity.class);
        Intent resultIntent = new Intent();
        resultIntent.putExtra(title_KEY, bookmarksList.get(position).getTitle());
        resultIntent.putExtra(url_KEY, bookmarksList.get(position).getUrl());
        setResult(RESULT_OK, resultIntent);
//        startActivity(urlIntent);
        finish();
    }

    @Override
    public void bookmarkDeleted(final int position) {
        // TODO dialog
        // pop out confirmation
        Dialog confirmDialog = new AlertDialog.Builder(this)
                .setMessage("Delete "+ bookmarksList.get(position).getTitle()+" ?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // delete from list
                        Toast.makeText(BookmarksActivity.this,
                                bookmarksList.get(position).getTitle()+" is deleted",
                                Toast.LENGTH_LONG).show();
                        bookmarksList.remove(position);
                        adapter.notifyDataSetChanged();
//                        reWriteFile();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(BookmarksActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    public void reWriteFile(){
        try {
            File file = new File(getFilesDir(), FILE_NAME);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            JSONArray jsonArray = new JSONArray(bookmarksList);


            bufferedWriter.write(jsonArray.toString());
            Log.d("json array", jsonArray.toString());
            bufferedWriter.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}