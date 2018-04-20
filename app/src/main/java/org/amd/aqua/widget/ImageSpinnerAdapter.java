package org.amd.aqua.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.amd.aqua.R;
import org.amd.aqua.model.ImageSpinnerData;

import java.util.List;

/**
 * Created by Akira on 2018/04/18.
 */

public class ImageSpinnerAdapter extends ArrayAdapter<ImageSpinnerData> {

    Context context;
    List<ImageSpinnerData> list;
    LayoutInflater inflater;

    public ImageSpinnerAdapter(Context context, List<ImageSpinnerData> list) {
        super(context, R.layout.image_spinner, list);
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.image_spinner, parent, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource(list.get(position).getIconResourceId());
        TextView textView = (TextView) itemView.findViewById(R.id.textView);
        textView.setText(list.get(position).getText());
        return itemView;
    }

    public void setList( List<ImageSpinnerData> list){
        this.list = list;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}