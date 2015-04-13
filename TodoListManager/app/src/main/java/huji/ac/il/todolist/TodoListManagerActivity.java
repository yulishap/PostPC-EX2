package huji.ac.il.todolist;

import android.app.Dialog;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Date;


public class TodoListManagerActivity extends ActionBarActivity {

    ArrayList<Task> tasks = new ArrayList<Task>();
    MyArrayAdapter todoListAdp;
    ArrayAdapter<Task> todoListAdp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);

        ListView todoList = (ListView) findViewById(R.id.lstTodoItems);
        todoListAdp = new MyArrayAdapter(this,tasks );


        ListView.OnItemLongClickListener itemClickListener = new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?>adapter,View v, final int pos,long id){
                infalteDelDialog(pos);
                return true;
            }
        };
        todoList.setOnItemLongClickListener(itemClickListener);
        todoList.setAdapter(todoListAdp);
    }


    public void infalteDelDialog(final int pos){
        final Dialog delDlg = new Dialog(this);
        LayoutInflater inflater = this.getLayoutInflater();

        View bodyView = inflater.inflate(R.layout.delete_dialog, null);
        delDlg.setTitle(tasks.get(pos).getTitle());
        Button delButton = (Button) bodyView.findViewById(R.id.menuItemDelete);
        delButton.setOnClickListener(new Button.OnClickListener(){
                 @Override
                 public void onClick(View v) {
                     tasks.remove(pos);
                     todoListAdp.notifyDataSetChanged();
                     delDlg.hide();
                 }
             }

        );

        if (tasks.get(pos).getTitle().length()>5 && tasks.get(pos).getTitle().substring(0, 5).equals("call ")){
            final String telNum = tasks.get(pos).getTitle().substring(5);

            Button callBtn = (Button) bodyView.findViewById(R.id.menuItemCall);
            callBtn.setText(tasks.get(pos).getTitle());
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
                tasks.add(new Task(resultTitle,resultDate ));
                todoListAdp.notifyDataSetChanged();
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
          /*  EditText newItem = (EditText)findViewById(R.id.edtNewItem);

            tasks.add(newItem.getText().toString());
            todoListAdp.notifyDataSetChanged();

            newItem.setText("");*/

            Intent intent = new Intent(this, AddNewTodoItemActivity.class);
            startActivityForResult(intent,1);
        }

        return super.onOptionsItemSelected(item);
    }
}
