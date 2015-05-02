package com.yousuf.shawon.ListViewDemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import junit.framework.Test;

import java.util.Random;

public class MyActivity extends Activity {


    ListView listView;
    ArrayAdapter<String> adapter;
    String TAG = getClass().getSimpleName();

    private int count = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        iniUI();
    }

    private void iniUI() {
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.linear_layout_1);
        TextView textView1 = (TextView) findViewById(R.id.text_view_1);
        listView = (ListView) findViewById(R.id.listView);

        final LinearLayout linearScroll = (LinearLayout) findViewById(R.id.linear_Layout_scroll);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);



        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter.add("Item " + count);
                adapter.notifyDataSetChanged();

                LinearLayout customLinearLayout = new LinearLayout(MyActivity.this);
                customLinearLayout.setOrientation(LinearLayout.HORIZONTAL);


                TextView temp = new TextView(MyActivity.this);
                temp.setText("item " + count);

                Button editButton = new Button(MyActivity.this);
                editButton.setText("EDIT");
                editButton.setOnClickListener(editButtonClickListener);
                customLinearLayout.addView(temp);
                customLinearLayout.addView(editButton);

                linearScroll.addView(customLinearLayout);



                count++;

                setListViewHeightBasedOnChildren(listView);
            }
        });

     findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             ViewGroup row = (ViewGroup) view.getParent();
             for (int itemPos = 0; itemPos < row.getChildCount(); itemPos++) {
                 View child = row.getChildAt(itemPos);
                 if (child instanceof TextView) {
                    TextView textView = (TextView) child; //Found it!

                     Log.d(TAG, textView.getText().toString());
                 }
             }
         }
     });


    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    View.OnClickListener editButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ViewGroup row =  (ViewGroup) view.getParent();

            for(int itemPos = 0; itemPos<row.getChildCount(); itemPos++){

                View child = row.getChildAt(itemPos);

                if( child.getClass() == TextView.class){
                    TextView tmpTextView = (TextView) child;
                    Log.d(TAG, "position " + itemPos);
                    Log.d( TAG, tmpTextView.getText().toString() );

                    tmpTextView.setText("changed " + count);
                }
            }
        }
    };

   // comment
}
