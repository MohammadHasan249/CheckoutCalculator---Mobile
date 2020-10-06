package com.example.checkoutpricecalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    ArrayList<MainItem> mainItems;
    Context context;

    public MainAdapter(Context context, ArrayList<MainItem> mainItems) {
        this.context = context;
        this.mainItems = mainItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_item, parent, false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(mainItems.get(position).getItemPic());
        holder.textView.setText(mainItems.get(position).getItemName());
        holder.textPrice.setText(mainItems.get(position).getItemPrice());
        holder.textQuantity.setText(String.format("Quantity: %s", mainItems.get(position).getItemQuantity()));
    }

    @Override
    public int getItemCount() {
        return mainItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        TextView textPrice;
        TextView textQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view);
            textPrice = itemView.findViewById(R.id.text_price);
            textQuantity = itemView.findViewById(R.id.text_quantity);
        }
    }
}
