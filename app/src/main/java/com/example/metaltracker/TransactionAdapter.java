package com.example.metaltracker;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Models.Buy;
import Models.Sell;

public class TransactionAdapter extends RecyclerView.Adapter {
    private static final int TYPE_BUY = 1;
    private static final int TYPE_SELL = 2;
    private List<Object> transactionList;

    public static class BuyViewHolder extends RecyclerView.ViewHolder {
        public TextView dateView;
        public TextView priceView;
        public TextView weightView;
        public TextView disView;

        public BuyViewHolder(View v) {
            super(v);
            dateView = v.findViewById(R.id.dateView);
            priceView = v.findViewById(R.id.priceView);
            weightView = v.findViewById(R.id.weightView);
            disView = v.findViewById(R.id.disView);

        }
    }
    public static class SellViewHolder extends RecyclerView.ViewHolder {
        public TextView dateView;
        public TextView priceView;
        public TextView weightView;
        public TextView disView;

        public SellViewHolder(View v) {
            super(v);
            dateView = v.findViewById(R.id.dateView);
            priceView = v.findViewById(R.id.priceView);
            weightView = v.findViewById(R.id.weightView);
            disView = v.findViewById(R.id.disView);

        }
    }

    public TransactionAdapter(List<Object> transactionList) {
        this.transactionList=transactionList;
    }

    @Override
    public int getItemViewType(int position) {
        if (transactionList.get(position) instanceof Buy) {
            return TYPE_BUY;
        } else {
            return TYPE_SELL;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BUY) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.buy_holder, parent, false);
            return new BuyViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.buy_holder, parent, false);
            return new SellViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_BUY) {
            Buy buy = (Buy) transactionList.get(position);
            ((BuyViewHolder) holder).dateView.setText(buy.getDate());
            ((BuyViewHolder) holder).priceView.setText(String.valueOf(buy.getPrice()));
            ((BuyViewHolder) holder).weightView.setText(String.valueOf(buy.getWeight()));
            ((BuyViewHolder) holder).disView.setText(String.valueOf(buy.getDescription()));
            ((BuyViewHolder) holder).priceView.setTextColor(Color.GREEN);
        } else {
            Sell sell = (Sell) transactionList.get(position);
            ((SellViewHolder) holder).dateView.setText(sell.getDate());
            ((SellViewHolder) holder).priceView.setText(String.valueOf(sell.getPrice()));
            ((SellViewHolder) holder).weightView.setText(String.valueOf(sell.getWeight()));
            ((SellViewHolder) holder).disView.setText(sell.getDescription());
            ((SellViewHolder) holder).priceView.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

}
