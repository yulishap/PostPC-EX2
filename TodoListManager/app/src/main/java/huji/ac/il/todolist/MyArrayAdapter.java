package huji.ac.il.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Yuli on 16/03/2015.
 */
public class MyArrayAdapter extends ArrayAdapter<String>{

    public MyArrayAdapter(Context context,ArrayList<String> tasks) {
        super(context,0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String cont = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
        TextView task= (TextView)convertView.findViewById(R.id.textView);
        task.setText(cont);
        if (position%2 == 0)
            task.setTextColor(Color.RED);
        else
            task.setTextColor(Color.BLUE);
        return convertView;
    }
}
