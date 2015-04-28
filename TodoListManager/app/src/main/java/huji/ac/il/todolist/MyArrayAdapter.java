package huji.ac.il.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Yuli on 16/03/2015.
 */
public class MyArrayAdapter extends ArrayAdapter<Task>{

    public MyArrayAdapter(Context context,ArrayList<Task> tasks) {
        super(context,0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Task task = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
        TextView taskTitle= (TextView)convertView.findViewById(R.id.txtTodoTitle);
        taskTitle.setText(task.getTitle());

        TextView taskDate= (TextView)convertView.findViewById(R.id.txtTodoDueDate);
        if (task.getDate() == null){
            taskDate.setText("No due date");
        }
        else {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            taskDate.setText(df.format(task.getDate()));

            Date todayDate = new Date();

            if (todayDate.after(task.getDate())) {
                taskDate.setTextColor(Color.RED);
                taskTitle.setTextColor(Color.RED);
            }
            else{
                taskDate.setTextColor(Color.BLACK);
                taskTitle.setTextColor(Color.BLACK);
            }

        }
        return convertView;
    }
}
