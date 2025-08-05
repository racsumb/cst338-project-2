package com.example.classroomannouncement.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.classroomannouncement.R;
import com.example.classroomannouncement.Database.Entities.Announcement;
import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder> {
    private List<Announcement> announcements;
    private boolean isAdmin;
    private OnItemClickListener listener;

    // Interface for click events
    public interface OnItemClickListener {
        void onItemClick(Announcement announcement);
        void onDeleteClick(Announcement announcement);
    }

    // Constructor
    public AnnouncementAdapter(List<Announcement> announcements, boolean isAdmin) {
        this.announcements = announcements;
        this.isAdmin = isAdmin;
    }

    // Set click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_announcement, parent, false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        Announcement announcement = announcements.get(position);

        // Set announcement data
        holder.titleTextView.setText(announcement.getTitle());
        holder.contentTextView.setText(announcement.getContent());
        holder.dateTextView.setText(announcement.getFormattedDate());

        // Show delete button only for admin
        if (holder.deleteButton != null) {
            holder.deleteButton.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
            holder.deleteButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClick(announcement);
                }
            });
        }

        // Set click listeners
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(announcement);
            }
        });
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    // Update announcements data
    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
        notifyDataSetChanged();
    }

    // ViewHolder class
    static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;
        TextView dateTextView;
        Button deleteButton;

        AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.announcementTitle);
            contentTextView = itemView.findViewById(R.id.announcementContent);
            dateTextView = itemView.findViewById(R.id.announcementDate);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}