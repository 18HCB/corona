package com.zub.covid_19.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zub.covid_19.R;
import com.zub.covid_19.api.ProvincesVietNam.DataProvinceVN;
import com.zub.covid_19.api.provData.ProvData;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProvVNAdapter extends RecyclerView.Adapter<ProvVNAdapter.ViewHolder> {

    private DataProvinceVN provListData = new DataProvinceVN();

    private ListClickedListener listClickedListener;

    public ProvVNAdapter(DataProvinceVN provListData, ListClickedListener listClickedListener) {
        this.provListData = provListData;
        this.listClickedListener = listClickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listClickedListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mProvName.setText(provListData.getProvinces().get(position).getProvinceName().toString());

        holder.mProvCase.setText(numberSeparator(Integer.parseInt(provListData.getProvinces().get(position).getConfirmed())));
        holder.mProvDeath.setText(numberSeparator(Integer.parseInt(provListData.getProvinces().get(position).getDeaths())));
        holder.mProvHealed.setText(numberSeparator(Integer.parseInt(provListData.getProvinces().get(position).getRecovered())));
        holder.mProvTreated.setText("0");

        holder.mProvListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mListClickedListener.onListClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {

        return provListData.getProvinces().size();

    }

    public void filterList(DataProvinceVN filteredList) {

        provListData = filteredList;
        notifyDataSetChanged();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mProvListLayout;

        TextView mProvName, mProvCase, mProvDeath, mProvHealed, mProvTreated;

        ListClickedListener mListClickedListener;

        public ViewHolder(@NonNull View itemView, ListClickedListener listClickedListener) {
            super(itemView);

            mProvListLayout = itemView.findViewById(R.id.prov_list_layout);

            mProvName = itemView.findViewById(R.id.prov_name);
            mProvCase = itemView.findViewById(R.id.prov_case);
            mProvDeath = itemView.findViewById(R.id.prov_death);
            mProvHealed = itemView.findViewById(R.id.prov_cured);
            mProvTreated = itemView.findViewById(R.id.prov_treated);

            mListClickedListener = listClickedListener;

        }
    }

    public interface ListClickedListener {
        void onListClicked(int position);
    }

    private String numberSeparator(int value) {
        return String.valueOf(NumberFormat.getNumberInstance(Locale.ITALY).format(value));
    }

}
