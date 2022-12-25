package com.sherbimet.user.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sherbimet.user.Activity.PackageActivity;
import com.sherbimet.user.Model.SubService;
import com.sherbimet.user.R;
import com.sherbimet.user.Utils.AtClass;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SubServiceAdapter extends RecyclerView.Adapter<SubServiceAdapter.ViewHolder> {

    private Context mContext;
    ArrayList<SubService> listSubService;
    AtClass atClass;

    public SubServiceAdapter(ArrayList<SubService> listSubService) {
        this.listSubService = listSubService;
    }

    @Override
    public SubServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View mView = LayoutInflater.from(mContext).inflate(R.layout.sub_service_list_row_item_layout, parent, false);
        atClass = new AtClass();
        return new SubServiceAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final SubServiceAdapter.ViewHolder holder, final int position) {
        final SubService subService = listSubService.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvSubServiceName.setText(Html.fromHtml(subService.getSubServiceName(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvSubServiceName.setText(Html.fromHtml(subService.getSubServiceName()));
        }

        Glide.with(mContext).load(subService.getSubServiceImage()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).error(R.drawable.app_icon_transparent).into(holder.ciVSubServiceImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atClass.isNetworkAvailable(mContext)) {
                    Intent i1 = new Intent(mContext, PackageActivity.class);
                    i1.putExtra("SubServiceID", subService.getSubServiceID());
                    i1.putExtra("SubServiceName", subService.getSubServiceName());
                    mContext.startActivity(i1);
                } else {
                    Toast.makeText(mContext, mContext.getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return listSubService.size();
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
        TextView tvSubServiceName;
        CircleImageView ciVSubServiceImage;


        public ViewHolder(View itemView) {
            super(itemView);

            tvSubServiceName = (TextView) itemView.findViewById(R.id.tvSubServiceName);
            ciVSubServiceImage = (CircleImageView) itemView.findViewById(R.id.ciVSubServiceImage);

        }
    }
}

