package com.example.spede;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PelaajaAdapter extends ArrayAdapter<Pelaaja> {

    private Context mContext;
    private List<Pelaaja> pelaajaList = new ArrayList<>();

    public PelaajaAdapter(@NonNull Context context, @LayoutRes ArrayList<Pelaaja> list) {
        super(context,0,list);
        mContext = context;
        pelaajaList = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        Pelaaja current = pelaajaList.get(position);





            TextView type = (TextView) listItem.findViewById(R.id.textView_name);
            type.setText(current.getNimi());

            TextView duration = (TextView) listItem.findViewById(R.id.textView_release);
            duration.setText("" + current.getPisteet());

        return listItem;
    }

}
