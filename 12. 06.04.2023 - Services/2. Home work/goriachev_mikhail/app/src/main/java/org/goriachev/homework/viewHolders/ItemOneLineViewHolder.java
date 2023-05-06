package org.goriachev.homework.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.goriachev.homework.R;

public class ItemOneLineViewHolder extends RecyclerView.ViewHolder {

    public final MaterialCardView layCard;
    public final TextView txvFirstLine;


    public ItemOneLineViewHolder(View view) {
        super(view);

        layCard = view.findViewById(R.id.lay_card);

        txvFirstLine = view.findViewById(R.id.txv_first_line);
    }
}
