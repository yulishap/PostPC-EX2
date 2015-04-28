package huji.ac.il.todolist;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;


public class TodoListManagerActivity extends ActionBarActivity {

    ArrayList<Task> tasks = new ArrayList<Task>();
    MyArrayAdapter todoListAdp;
    MyCursorAdapter todoCursorAdp;
    ArrayAdapter<Task> todoListAdp1;
    ArrayList<Integer> IDtoPOS = new ArrayList<Integer>();
    SQLiteDatabase todos_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);

        ListView todoList = (ListView) findViewById(R.id.lstTodoItems);
        todoListAdp = new MyArrayAdapter(this,tasks );

        TodoDBHelper helper = new TodoDBHelper(this);


        // Adding objects
        todos_db = helper.getWritableDatabase();
        Cursor cursor = todos_db.query("TODOs", new String[]{"_id","title", "due"}, null,null,null,null,null);
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                int id= cursor.getInt(cursor.getColumnIndexOrThrow("_id")); //...and so on
                IDtoPOS.add( id);
            } while (cursor.moveToNext());
        }

        String title;

        // End
        ListView.OnItemLongClickListener itemClickListener = new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?>adapter,View v, final int pos,long id){
                String title = ((TextView)v.findViewById(R.id.txtTodoTitle)).getText().toString();

                infalteDelDialog(title, pos);
                return true;
            }
        };
        todoList.setOnItemLongClickListener(itemClickListener);

        // db adapter
        todoCursorAdp = new MyCursorAdapter(this,cursor,false);
        todoList.setAdapter(todoCursorAdp);
    }


    public void infalteDelDialog(final String title, final int pos){
        final Dialog delDlg = new Dialog(this);
        LayoutInflater inflater = this.getLayoutInflater();

        View bodyView = inflater.inflate(R.layout.delete_dialog, null);
        delDlg.setTitle(title);

        Button delButton = (Button) bodyView.findViewById(R.id.menuItemDelete);
        delButton.setOnClickListener(new Button.OnClickListener(){
                 @Override
                 public void onClick(View v) {
                     todos_db.delete("TODOs","_id=?" ,new String[] {Integer.toString(IDtoPOS.get(pos))});
                     IDtoPOS.remove(pos);
                     Cursor cursor = todos_db.query("TODOs", new String[]{"_id","title", "due"}, null,null,null,null,null);
                     todoCursorAdp.changeCursor(cursor);
                     delDlg.hide();
                 }
             }

        );

        if (title.length()>5 && title.substring(0, 5).equals("call ")){
            final String telNum = title.substring(5);

            Button callBtn = (Button) bodyView.findViewById(R.id.menuItemCall);
            callBtn.setText(title);
            callBtn.setVisibility(View.VISIBLE);
            callBtn.setOnClickListener(new Button.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telNum));
                                               startActivity(dial);

                                           }
                                       }

            );
        }

        delDlg.setContentView(bodyView);
        delDlg.show();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list_manager, menu);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String resultTitle=data.getStringExtra("title");
                Date  resultDate = (Date)data.getSerializableExtra("dueDate");

                ContentValues values = new ContentValues();
                values.put("title", resultTitle);
                values.put("due", resultDate.getTime());
                long id = todos_db.insert("TODOs", null, values);
                IDtoPOS.add((int) id);
                Cursor cursor = todos_db.query("TODOs", new String[]{"_id","title", "due"}, null,null,null,null,null);
                todoCursorAdp.changeCursor(cursor);


            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuItemAdd) {

            Intent intent = new Intent(this, AddNewTodoItemActivity.class);
            startActivityForResult(intent,1);
        }

        return super.onOptionsItemSelected(item);
    }
}
