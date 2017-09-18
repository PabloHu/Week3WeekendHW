package com.example.admin.week3weekendhw;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.admin.week3weekendhw.modelrepo.ModelResponse;

/**
 * Created by Admin on 9/12/2017.
 */

public class CustomListAdaper extends ArrayAdapter<ModelResponse> {

    private static final String TAG = "CustomTAG";

    public CustomListAdaper(@NonNull Context context,
                            @LayoutRes int resource,
                            @NonNull List<ModelResponse> modelResponseList) {
        super(context, resource, modelResponseList);
    }

    public class ViewHolder {


        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvLanguage;
        private TextView tvCreatedDate;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View view = convertView;

        ModelResponse modelResponse = getItem(position);
        if(modelResponse != null){
            view = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.custom_listview_item, null);
            //bind
            ViewHolder holder = new ViewHolder();
            holder.tvTitle = view.findViewById(R.id.tvTitle);
            holder.tvDescription = view.findViewById(R.id.tvDescription);
            holder.tvLanguage = view.findViewById(R.id.tvLanguage);
            holder.tvCreatedDate = view.findViewById(R.id.tvCreatedDate);

            holder.tvTitle.setText(modelResponse.getName());
            holder.tvDescription.setText(modelResponse.getDescription());
            holder.tvLanguage.setText(modelResponse.getLanguage());
            holder.tvCreatedDate.setText(modelResponse.getCreatedAt());

            view.setTag(holder);
        }
        return view;
    }
}
