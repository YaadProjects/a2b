package com.cmput301f16t11.a2b;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Extends ArrayAdapter and modifies view so every even numbered entry is lightly shaded in
 * This work, "ShadedListAdapter," contains a derivative of an answer to
 * "Android Alternate row Colors in ListView" by "Suraj Bajaj," a user on Stack Overflow.
 * It is used under CC-BY-SA by CMPUT301F16T11.
 * It is available here:
 * http://stackoverflow.com/questions/13109840/android-alternate-row-colors-in-listview
 */

// for reference:
//adapter = new ArrayAdapter<UserRequest>(this, android.R.layout.simple_list_item_1,
//        android.R.id.text1, this.requests);
public class ShadedListAdapter extends ArrayAdapter<UserRequest> {
    private ArrayList<UserRequest> requests;
    private Context context;

    public ShadedListAdapter(Context cntxt, ArrayList<UserRequest> objs) {
        super(cntxt, 0, objs);
        requests = objs;
        context = cntxt;
    }

    @Override
    public View getView(int pos, View v, ViewGroup vGroup) {
        // Get the data item for this position
        UserRequest request = requests.get(pos);

        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.request_list_item, vGroup, false);
        }

        if (pos % 2 == 0) {
            // if even, shade the background
            v.setBackgroundColor(Color.rgb(224, 224, 224));
        }
        else{
            v.setBackgroundColor(Color.WHITE);
        }

        return setViews(v, request);
    }

    private View setViews(View view, UserRequest request){

        final TextView riderEntry = (TextView) view.findViewById(R.id.rider_entry);
        final TextView fareEntry  = (TextView) view.findViewById(R.id.fare_entry);
        final TextView dateEntry = (TextView) view.findViewById(R.id.date_created_entry);
        String riderName;


        if(!isNetworkAvailable(context)) {
            saveLoad_Controller saveLoadController = new saveLoad_Controller(context);
            HashMap<String,String> map = saveLoadController.loadFromFileMap("map.sav");
            riderName = map.get(request.getRiderID());
        } else {
            User rider = UserController.getUserFromId(request.getRiderID());
            riderName = rider.getName();
        }

        try {
            riderEntry.setText(riderName);
            fareEntry.setText(request.getFare().toString());
            dateEntry.setText(request.getDateString());
        } catch (NullPointerException e) {
            Log.e("Shaded ERROR:", "No user found in request");
        }

        return view;
    }

    // http://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
