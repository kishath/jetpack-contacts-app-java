package com.kishathfernando.contactsappjava.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kishathfernando.contactsappjava.R;
import com.kishathfernando.contactsappjava.domain.models.People;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PeoplesListAdapter extends RecyclerView.Adapter<PeoplesListAdapter.ViewHolder>{

    private List<People> items = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public PeoplesListAdapter(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position), mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(List<People> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView metAt;
        private Button contact;
        private ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            metAt = itemView.findViewById(R.id.textViewMet);
            contact = itemView.findViewById(R.id.buttonContact);
        }

        private void bind(final People people, final OnItemClickListener listener) {
            name.setText(people.name);
            metAt.setText(people.metAt);
            contact.setText(people.contact);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + people.contact));
                    itemView.getContext().startActivity(intent);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(people, view);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(People people, View itemView);
    }
}
