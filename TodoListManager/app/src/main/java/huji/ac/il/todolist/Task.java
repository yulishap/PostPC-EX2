package huji.ac.il.todolist;

import java.util.Date;

/**
 * Created by Yuli on 10/04/2015.
 */
public class Task {

    private String title;
    private Date date;

    public Task(String title, Date date){
        this.title = title;
        this.date = date;

    }

    public String getTitle(){
        return this.title;
    }
    public Date getDate(){
        return this.date;
    }
}
