package com.sherbimet.user.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sherbimet.user.Activity.SubServiceActivity;
import com.sherbimet.user.Model.Services;
import com.sherbimet.user.R;
import com.sherbimet.user.Utils.AtClass;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private Context mContext;
    ArrayList<Services> listServices;
    AtClass atClass;

    public ServicesAdapter(ArrayList<Services> listServices) {
        this.listServices = listServices;
    }

    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View mView = LayoutInflater.from(mContext).inflate(R.layout.services_list_row_item_layout, parent, false);
        atClass = new AtClass();
        return new ServicesAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ServicesAdapter.ViewHolder holder, final int position) {
        final Services services = listServices.get(position);

        holder.tvServiceName.setSelected(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvServiceName.setText(Html.fromHtml(services.getServiceName(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvServiceName.setText(Html.fromHtml(services.getServiceName()));
        }

        Glide.with(mContext).load(services.getServiceImage()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).error(R.drawable.app_icon_transparent).into(holder.iVServiceIcon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atClass.isNetworkAvailable(mContext)) {
                    Intent i = new Intent(mContext, SubServiceActivity.class);
                    i.putExtra("ServiceID", services.getServiceID());
                    i.putExtra("ServiceName", services.getServiceName());
                    mContext.startActivity(i);
                } else {
                    Toast.makeText(mContext, mContext.getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return listServices.size();
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
        TextView tvServiceName;
        ImageView iVServiceIcon;


        public ViewHolder(View itemView) {
            super(itemView);

            tvServiceName = (TextView) itemView.findViewById(R.id.tvServiceName);
            iVServiceIcon = (ImageView) itemView.findViewById(R.id.iVServiceIcon);

        }
    }
}

