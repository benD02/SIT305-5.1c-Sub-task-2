package com.example.subtask2;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    List<String> playlist;
    private ItemDeleteListener deleteListener;

    private LayoutInflater inflater;


    public PlaylistAdapter(List<String> playlist, ItemDeleteListener deleteListener) {
        this.playlist = playlist;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.playlist_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = playlist.get(position);
        holder.urlTextView.setText(url);
        holder.deleteButton.setOnClickListener(v -> deleteListener.onItemDelete(position));
        holder.playButton.setOnClickListener(v -> playVideo(url, holder.itemView.getContext()));
    }

    @Override
    public int getItemCount() {
        return playlist != null ? playlist.size() : 0;
    }

    private void playVideo(String url, Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Play Video");

        WebView webView = new WebView(context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        alert.setView(webView);
        alert.setNegativeButton("Close", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView urlTextView;
        Button deleteButton, playButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            urlTextView = itemView.findViewById(R.id.urlTextView);
            deleteButton = itemView.findViewById(R.id.del_btn);
            playButton = itemView.findViewById(R.id.play_btn);

        }
    }

    public interface ItemDeleteListener {
        void onItemDelete(int position);
    }
}
