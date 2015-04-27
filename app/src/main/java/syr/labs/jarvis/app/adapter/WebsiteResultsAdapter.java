package syr.labs.jarvis.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import syr.labs.jarvis.app.R;
import syr.labs.jarvis.app.bean.WebsiteResultItem;


public class WebsiteResultsAdapter extends ArrayAdapter<WebsiteResultItem> {

    Context mContext;
    int layoutResourceId;
    WebsiteResultItem data[] = null;

    public WebsiteResultsAdapter(Context mContext, int layoutResourceId, WebsiteResultItem[] data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*
         * The convertView argument is essentially a "ScrapView" as described is Lucas post 
         * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
         * It will have a non-null value when ListView is asking you recycle the row layout. 
         * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
         */
        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        WebsiteResultItem objectItem = data[position];

        /*TextView websiteResultTime = (TextView) convertView.findViewById(R.id.websiteResultTime);
        websiteResultTime.setText(objectItem.time);*/
        TextView websiteResultURL = (TextView) convertView.findViewById(R.id.websiteResultURL);
        websiteResultURL.setText(objectItem.websiteUrl);
        return convertView;

    }

}