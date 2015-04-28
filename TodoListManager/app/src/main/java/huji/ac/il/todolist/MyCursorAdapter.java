package huji.ac.il.todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yuli on 25/04/2015.
 */
public class MyCursorAdapter extends CursorAdapter{
    public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //Task task = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            //view = LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false);
        }
        TextView taskTitle= (TextView)view.findViewById(R.id.txtTodoTitle);
        String title= cursor.getString(cursor.getColumnIndexOrThrow("title"));
        taskTitle.setText(title);

        TextView taskDate= (TextView)view.findViewById(R.id.txtTodoDueDate);
        Long due= cursor.getLong(cursor.getColumnIndexOrThrow("due"));
        if (due == null){
            taskDate.setText("No due date");
        }
        else {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");



            taskDate.setText(df.format(new Date(due)));
            //taskDate.setText(df.format(task.getDate()));

            Date todayDate = new Date();

            if (todayDate.after(new Date(due))) {
                taskDate.setTextColor(Color.RED);
                taskTitle.setTextColor(Color.RED);
            }
            else{
                taskDate.setTextColor(Color.BLACK);
                taskTitle.setTextColor(Color.BLACK);
            }

        }
    }
}
