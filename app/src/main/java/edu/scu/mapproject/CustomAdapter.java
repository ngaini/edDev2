package edu.scu.mapproject;

/**
 * Created by Nathan on 3/19/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nathan on 3/8/2016.
 */
public class CustomAdapter extends BaseAdapter {
    private Context context;
    String[] navigation_menu_item;
    int[] navigation_menu_image = {R.drawable.ic_home_black_48dp, R.drawable.ic_account_box_black_48dp, R.drawable.ic_explore_black_48dp,R.drawable.ic_contact_phone_black_48dp, R.drawable.ic_map_black_48dp, R.drawable.ic_change_password_black_48dp, R.drawable.ic_exit_to_app_black_48dp};

    public CustomAdapter(Context context) {
        this.context= context;
        this.navigation_menu_item = context.getResources().getStringArray(R.array.navOptions    );
    }

    @Override
    public int getCount() {
        return navigation_menu_item.length;
    }

    @Override
    public Object getItem(int position) {
        return navigation_menu_item[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        //if convert view is null we are creating the row for the first time
        if(convertView==null)
        {

            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row =inflater.inflate(R.layout.drawer_custom_row,parent, false);
        }
        else
        {
            row = convertView;
        }
        ((TextView)row.findViewById(R.id.drawer_item_textView)).setText(navigation_menu_item[position]);
        ((ImageView)row.findViewById(R.id.drawer_item_imageView)).setImageResource(navigation_menu_image[position]);
        return row;
    }
}
