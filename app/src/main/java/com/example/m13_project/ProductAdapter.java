package com.example.m13_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ProductViewHolder> {

    private final Context context;
    private final OnProductClickListener onProductClickListener;
    private static final String PLACEHOLDER_URL = "https://via.placeholder.com/250";

    public ProductAdapter(Context context, OnProductClickListener listener) {
        super(DIFF_CALLBACK);
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        this.context = context;
        this.onProductClickListener = listener;
    }

    private static final DiffUtil.ItemCallback<Product> DIFF_CALLBACK = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getProductId() == newItem.getProductId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = getItem(position);

        if (product == null) {
            Log.e("ProductAdapter", "Product is null at position: " + position);
            return;
        }

        holder.textViewDescription.setText(product.getDescription());

        String imageUrl = product.getImageUrl();
        Log.d("ProductAdapter", "Image URL at position " + position + ": " + imageUrl);

        // First load the placeholder image
        Picasso.get()
                .load(PLACEHOLDER_URL)
                .into(holder.imageViewProduct, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        // Then load the actual product image
                        Picasso.get()
                                .load(imageUrl != null ? imageUrl : "https://via.placeholder.com/250")
                                .into(holder.imageViewProduct, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.d("ProductAdapter", "Image loaded successfully: " + imageUrl);
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.e("ProductAdapter", "Error loading image: " + e.getMessage());
                                    }
                                });
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("ProductAdapter", "Error loading placeholder: " + e.getMessage());
                    }
                });

        holder.itemView.setOnClickListener(v -> {
            if (onProductClickListener != null) {
                onProductClickListener.onProductClick(product);
            }
        });
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDescription;
        ImageView imageViewProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
        }
    }
}