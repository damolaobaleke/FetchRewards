package com.fetchrewards.hiring.views.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fetchrewards.hiring.R;
import com.fetchrewards.hiring.models.Item;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapterItems extends RecyclerView.Adapter<RecyclerViewAdapterItems.ItemsViewHolder> {
    List<Item> mItems;
    OnItemClickListener mListener;

    public RecyclerViewAdapterItems(List<Item> items){
        mItems = items;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Supplying the parent View lets the inflater know what layout params to use.
        // Supplying the false parameter tells inflater to not attach layout params to the parent just yet. That is what the RecyclerView will do for you.

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_view, parent, false);
        return new ItemsViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        Item item = mItems.get(position);

        holder.id.setText(String.valueOf(item.getId()));
        holder.listId.setText(String.valueOf(item.getListId()));
        holder.name.setText(String.valueOf(item.getName()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**Click events and listeners*/
    interface OnItemClickListener{
        void onItemClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    /**Click events and listeners*/

    static class ItemsViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView id;
        TextView listId;

        public ItemsViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.identity);
            listId = itemView.findViewById(R.id.list_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        if(getAdapterPosition() != RecyclerView.NO_POSITION){
                            listener.onItemClicked(getAdapterPosition());
                        }
                    }
                }
            });
        }
    }
}
