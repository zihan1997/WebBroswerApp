package temple.edu.webbroswerapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.ViewHolder>{

    private ArrayList<String> bookmarksList;
    private Context context;
    private BookMarkInterface bookMarkInterface;

    public BookMarkAdapter(ArrayList<String> bookmarks, Context context, BookMarkInterface bookMarkInterface){
        this.bookmarksList = bookmarks;
        this.context = context;
        this.bookMarkInterface = bookMarkInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView text1;
        private final ImageButton deleteButton;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("bookmark", "Element " + getAdapterPosition() + " clicked.");
                }
            });
            text1 = view.findViewById(R.id.text1);
            deleteButton = view.findViewById(R.id.deleteButton);
        }

        public TextView getTextView() {
            return text1;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.bookmark_adapter_layout,parent,false));
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookmark_adapter_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookMarkAdapter.ViewHolder holder, final int position) {
        holder.getTextView().setText(bookmarksList.get(position));
        // url click listener
        holder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookMarkInterface.bookmarkClicked(position);
            }
        });

        // delete listener
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookMarkInterface.bookmarkDeleted(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookmarksList.size();
    }
}
