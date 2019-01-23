package com.example.m4hdyar.gym;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
/**
 * This Holds data of Meal Diet program and makes it ready to show in a recycler view.
 * https://www.youtube.com/watch?v=a4o9zFfyIM4
 * https://www.simplifiedcoding.net/android-recyclerview-cardview-tutorial/
 */
public class SubscriptionListAdapter  extends RecyclerView.Adapter<SubscriptionListAdapter.SubscriptionViewHolder> {

    //We want context to get layout from it !! :/
    private Context mCtx;

    private List<Subscription> subscriptionList;

    public SubscriptionListAdapter(Context mCtx, List<Subscription> subscriptionList) {
        this.mCtx = mCtx;
        this.subscriptionList = subscriptionList;
    }


    //Let's override create view holder
    @NonNull
    @Override
    public SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating layout from context
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //TODO:maybe changing null to parent?! https://stackoverflow.com/questions/30691150/match-parent-width-does-not-work-in-recyclerview
        View view = inflater.inflate(R.layout.subscription_history_list_layout, parent,false);
        //Finally creating view holder

        /**
         * For perfomance we combine these lines.
         * MealDietListViewHolder vholder = new MealDietListViewHolder(view);
         * return vholder;
         */
        return new SubscriptionListAdapter.SubscriptionViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionListAdapter.SubscriptionViewHolder holder, int position) {
        //Now we are setting each row element data
        Subscription subscription = subscriptionList.get(position);

        holder.txtSubscriptionName.setText(subscription.getName());
        holder.txtSubscriptionDate.setText(subscription.getSubmitDate());
        holder.txtSubsPaidAmount.setText(subscription.getPaid_Amount());
    }

    @Override
    public int getItemCount() {
        return subscriptionList.size();
    }

    class SubscriptionViewHolder extends RecyclerView.ViewHolder{

        TextView txtSubscriptionName,txtSubscriptionDate,txtSubsPaidAmount;

        public SubscriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            //Finding list elements
            //TODO: ID
            txtSubscriptionName = itemView.findViewById(R.id.txtSubscriptionName);
            txtSubscriptionDate = itemView.findViewById(R.id.txtSubscriptionDate);
            txtSubsPaidAmount = itemView.findViewById(R.id.txtSubsPaidAmount);
        }
    }
}
