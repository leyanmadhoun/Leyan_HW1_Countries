package com.example.leyan_hw1_countries;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leyan_hw1_countries.DetailsActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class CountryAdapter extends ListAdapter<Country, CountryAdapter.VH> {

    private final Context context;

    public CountryAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<Country> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Country>() {
                @Override
                public boolean areItemsTheSame(@NonNull Country oldItem, @NonNull Country newItem) {
                    return oldItem.id == newItem.id;
                }

                @Override
                public boolean areContentsTheSame(@NonNull Country oldItem, @NonNull Country newItem) {
                    return oldItem.name.equals(newItem.name)
                            && oldItem.capital.equals(newItem.capital)
                            && oldItem.description.equals(newItem.description)
                            && oldItem.flagResId == newItem.flagResId;
                }
            };

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Country c = getItem(position);
        h.tvName.setText(c.name);
        h.tvCapital.setText(c.capital);
        h.imgFlag.setImageResource(c.flagResId);

        h.imgFlag.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "You clicked " + c.name, Toast.LENGTH_SHORT).show();

            // أو رسالة باللوج (تظهر في Logcat)
            Log.d("CountryAdapter", "Clicked: " + c.name);
            Intent i = new Intent(context, DetailsActivity.class);
            i.putExtra("id", c.id);
            i.putExtra("name", c.name);
            i.putExtra("capital", c.capital);
            i.putExtra("description", c.description);
            i.putExtra("flag", c.flagResId);
            context.startActivity(i);
        });
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView imgFlag; TextView tvName, tvCapital;
        VH(@NonNull View itemView) {
            super(itemView);
            imgFlag = itemView.findViewById(R.id.imgFlag);
            tvName = itemView.findViewById(R.id.tvName);
            tvCapital = itemView.findViewById(R.id.tvCapital);
        }
    }
}
