package com.sherbimet.user.Adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sherbimet.user.Model.Area;
import com.sherbimet.user.R;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {

    private Context mContext;
    ArrayList<Area> listArea;
    ClickListener clicklistener;

    public interface ClickListener {
        void onItemClick(View v,int position);
    }

    public void setClickListener(ClickListener clicklistener) {
        this.clicklistener = clicklistener;
    }


    public AreaAdapter(Context context,ArrayList<Area> listArea) {
        this.listArea = listArea;
    }

    @Override
    public AreaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View mView = LayoutInflater.from(mContext).inflate(R.layout.area_item_list_row_item_layout, parent, false);
        return new AreaAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final AreaAdapter.ViewHolder holder, final int position) {
        final Area area = listArea.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvAreaNameInitials.setText(Html.fromHtml(area.getAreaNameInitials(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvAreaNameInitials.setText(Html.fromHtml(area.getAreaNameInitials()));
        }

        holder.tvAreaName.setSelected(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvAreaName.setText(Html.fromHtml(area.getAreaName(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvAreaName.setText(Html.fromHtml(area.getAreaName()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clicklistener != null) {
                    clicklistener.onItemClick(view,position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return listArea.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAreaNameInitials, tvAreaName;


        public ViewHolder(View itemView) {
            super(itemView);

            tvAreaNameInitials = (TextView) itemView.findViewById(R.id.tvAreaNameInitials);
            tvAreaName = (TextView) itemView.findViewById(R.id.tvAreaName);

        }
    }
}

