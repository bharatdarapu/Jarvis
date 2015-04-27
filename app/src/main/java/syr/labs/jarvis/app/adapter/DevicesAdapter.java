package syr.labs.jarvis.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import syr.labs.jarvis.app.R;
import syr.labs.jarvis.app.bean.RowItem;

/**
 * Created by Bharat on 7/31/2014.
 */


public class DevicesAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<RowItem> rowItems;

    public DevicesAdapter(LayoutInflater inflater, List<RowItem> rowItems) {
        this.inflater = inflater;
        this.rowItems = rowItems;
    }

    public int getCount() {
        return rowItems.size();
    }

    public Object getItem(int position) {
        return rowItems.get(position);
    }

    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.devices_rows, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.phoneName);
            holder.imageView = (ImageView) convertView.findViewById(R.id.phoneImage);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        RowItem rowItem = (RowItem) getItem(position);

        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());

        return convertView;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }

}
