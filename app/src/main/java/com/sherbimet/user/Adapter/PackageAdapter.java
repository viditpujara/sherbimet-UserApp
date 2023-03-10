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
import com.sherbimet.user.Activity.PackageDetailsActivity;
import com.sherbimet.user.Model.Package;
import com.sherbimet.user.Model.SubService;
import com.sherbimet.user.R;
import com.sherbimet.user.Utils.AtClass;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {
    private Context mContext;
    ArrayList<Package> listPackage;
    AtClass atClass;

    public PackageAdapter(ArrayList<Package> listPackage) {
        this.listPackage = listPackage;
    }

    @Override
    public PackageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View mView = LayoutInflater.from(mContext).inflate(R.layout.packages_list_row_item_layout, parent, false);
        atClass = new AtClass();
        return new PackageAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final PackageAdapter.ViewHolder holder, final int position) {
        final Package mPackage = listPackage.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvPackageName.setText(Html.fromHtml(mPackage.getPackageName(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvPackageName.setText(Html.fromHtml(mPackage.getPackageName()));
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvPackageAmount.setText(Html.fromHtml(mPackage.getPackageAmount(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvPackageAmount.setText(Html.fromHtml(mPackage.getPackageAmount()));
        }

        Glide.with(mContext).load(mPackage.getPackageImage()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).error(R.drawable.app_icon_transparent).into(holder.ciVPackageImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atClass.isNetworkAvailable(mContext)) {
                    Intent i = new Intent(mContext, PackageDetailsActivity.class);
                    i.putExtra("PackageID", mPackage.getPackageID());
                    i.putExtra("PackageName", mPackage.getPackageName());
                    mContext.startActivity(i);
                } else {
                    Toast.makeText(mContext, mContext.getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPackage.size();
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
        TextView tvPackageName,tvPackageAmount;
        CircleImageView ciVPackageImage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPackageName = (TextView) itemView.findViewById(R.id.tvPackageName);
            tvPackageAmount = (TextView) itemView.findViewById(R.id.tvPackageAmount);
            ciVPackageImage = (CircleImageView) itemView.findViewById(R.id.ciVPackageImage);
        }
    }
}

