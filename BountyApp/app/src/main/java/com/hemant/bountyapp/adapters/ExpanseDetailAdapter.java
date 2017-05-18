package com.hemant.bountyapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.hemant.bountyapp.R;
import com.hemant.bountyapp.models.ExpenditureModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hemant on 5/15/2017.
 */

public class ExpanseDetailAdapter extends RecyclerView.Adapter<ExpanseDetailAdapter.ViewHolder> {

    Context context;
    List<ExpenditureModel> expanseList;
    LayoutInflater inflater;

    public ExpanseDetailAdapter(Context context, List<ExpenditureModel> expanseList) {
        this.context = context;
        this.expanseList = expanseList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.expense_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color1 = generator.getRandomColor();
          //  Log.wtf("ReceivedDate", "---------" + expanseList.get(position).getDate());
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            Date date = sdf.parse(expanseList.get(position).getDate());
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String newDate = df.format(date);

            holder.expanseDetailText.setText(expanseList.get(position).getRemark().toUpperCase());
            // TextDrawable drawable  = TextDrawable.builder().beginConfig().withBorder(4).endConfig().buildRoundRect("A",color1,10);
            // holder.imageView.setImageDrawable(drawable);
            holder.amountText.setText(context.getResources().getString(R.string.Rs) + ":" + expanseList.get(position).getAmount());
            holder.dateText.setText(newDate);
            holder.imageView.setBackgroundColor(color1);

            String category = expanseList.get(position).getCategory();
            if (category.equalsIgnoreCase("travel")) {
                holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.travel));
            } else if (category.equalsIgnoreCase("food")) {
                holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.food));
               // holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.food));
            } else if (category.equalsIgnoreCase("health")) {
                holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.heart));
                // holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.heart));
            } else if (category.equalsIgnoreCase("shopping")) {
                holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.shopping));
                // holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.shopping));
            } else if (category.equalsIgnoreCase("grocery")) {
                holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.grocery));
                //holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.grocery));
            } else if (category.equalsIgnoreCase("entertainment")) {
                    holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.record));
                //holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.record));
            } else if (category.equalsIgnoreCase("rent")) {
                holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.rent));
                // holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.rent));
            } else if (category.equalsIgnoreCase("bills")) {
                holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.copy));
                //holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.copy));
            } else if (category.equalsIgnoreCase("other")) {
                holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.other));
                //holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.other));
            } else {
                holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.other));
                //holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.other));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return expanseList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView expanseDetailText;
        TextView amountText;
        TextView dateText;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.card_image_view);
            expanseDetailText = (TextView) itemView.findViewById(R.id.expance_card_detail_text);
            amountText = (TextView) itemView.findViewById(R.id.expanse_card_amount_text);
            dateText = (TextView) itemView.findViewById(R.id.expance_card_date_text);
            setTypeFace();
        }

        public void setTypeFace() {
            Typeface typeBold = Typeface.createFromAsset(context.getAssets(), "GothamRoundedBold_21016.ttf");
            Typeface type = Typeface.createFromAsset(context.getAssets(), "GothamBook.ttf");
            expanseDetailText.setTypeface(typeBold);
            dateText.setTypeface(type);
            amountText.setTypeface(typeBold);

        }


    }

    public void swap(List<ExpenditureModel> updatedExpenseList){

        expanseList.clear();
        expanseList.addAll(updatedExpenseList);
        notifyDataSetChanged();
    }
}
