package com.example.subtask2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    List<String> playlist;
    private ItemDeleteListener deleteListener;

    public PlaylistAdapter(List<String> playlist, ItemDeleteListener deleteListener) {
        this.playlist = playlist;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.urlTextView.setText(playlist.get(position));
        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onItemDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlist != null ? playlist.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView urlTextView;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            urlTextView = itemView.findViewById(R.id.urlTextView);
            deleteButton = itemView.findViewById(R.id.del_btn);
        }
    }

    public interface ItemDeleteListener {
        void onItemDelete(int position);
    }
}
