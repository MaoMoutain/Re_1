package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

//连接数据库，并从数据库中获取所需数据
public class DBService  {
    /*---------------------------------------------------------------------------------------*/
    public static final String DB_NAME = "Question.db"; //保存的数据库文件名
    private SQLiteDatabase database;
    private Context context;
    //查询是否拥有读写权限
    /*---------------------------------------------------------------------------------------------*/
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*------------------------------------------------------------------------------------------*/
    DBService(Context context) {
        this.context = context;
    }
    private void openDatabase() {
        File dir;
            try {
                dir = new File(context.getFilesDir() + "test_2.db");
                if(dir.createNewFile())
                {
                    try {
                        InputStream is = this.context.getResources().getAssets().open(DB_NAME);//欲导入的数据库
                        OutputStream os = new FileOutputStream(context.getFilesDir() + "test_2.db");
                        byte[] buffer = new byte[1024];
                        int count = 0;
                        while ((count = is.read(buffer)) > 0) {
                            os.write(buffer, 0, count);
                        }
                        os.flush();
                        os.close();
                        is.close();
                    } catch (FileNotFoundException e) {
                        Log.e("Database", "File not found");
                        e.printStackTrace();
                    } catch (IOException e) {
                        Log.e("Database", "IO exception");
                        e.printStackTrace();
                    }
                    Log.i("test", "创建成功");
                }else
                {
                    Log.i("test", "创建失败");
                }
            }catch (IOException e){
                e.printStackTrace();
        }
            database = SQLiteDatabase.openOrCreateDatabase(context.getFilesDir() + "test_2.db", null);
    }
    //do something else here
    public void closeDatabase(){
        this.database.close();
    }
    /*---------------------------------------------------------------------------------------*/
    //在构造函数中打开指定数据库，并把它的引用指向db
    //获取数据库中的问题
    public List<Question> getAllQuestion() {
        this.openDatabase();
        List<Question> list = new ArrayList<Question>();
        /*
               Cursor是结果集游标，用于对结果集进行随机访问,其实Cursor与JDBC中的ResultSet作用很相似。
             rawQuery()方法的第一个参数为select语句；第二个参数为select语句中占位符参数的值，如果select语句没有使用占位符，该参数可以设置为null。*/
        Cursor cursor = database.rawQuery("select * from question", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();//将cursor移动到第一个光标上
            int count = cursor.getCount();
            //将cursor中的每一条记录生成一个question对象，并将该question对象添加到list中
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                Question question = new Question();
                question.ID = cursor.getInt(cursor.getColumnIndex("qid"));
                question.tid=cursor.getInt(cursor.getColumnIndex("tid"));
                question.question = cursor.getString(cursor.getColumnIndex("question"));
                question.answerA = cursor.getString(cursor.getColumnIndex("choiceA"));
                question.answerB = cursor.getString(cursor.getColumnIndex("choiceB"));
                question.answerC = cursor.getString(cursor.getColumnIndex("choiceC"));
                question.answerD = cursor.getString(cursor.getColumnIndex("choiceD"));
                question.answer = cursor.getString(cursor.getColumnIndex("answer"));
                question.Translation = cursor.getString(cursor.getColumnIndex("translation"));
                question.year = cursor.getInt(cursor.getColumnIndex("year"));
                question.done = cursor.getInt(cursor.getColumnIndex("done"));
                question.wrong = cursor.getInt(cursor.getColumnIndex("wrong"));
                question.collect=cursor.getInt(cursor.getColumnIndex("collect"));
                question.explaination = cursor.getString(cursor.getColumnIndex("analysis"));
                //表示没有选择任何选项
                question.selectedAnswer = "NULL";
                list.add(question);
            }
        }
        cursor.close();
        return list;
    }

    public List<Question> get_year_Question(int year) {
        this.openDatabase();
        List<Question> list = new ArrayList<Question>();
        /*
               Cursor是结果集游标，用于对结果集进行随机访问,其实Cursor与JDBC中的ResultSet作用很相似。
             rawQuery()方法的第一个参数为select语句；第二个参数为select语句中占位符参数的值，如果select语句没有使用占位符，该参数可以设置为null。*/
        String years[]={String.valueOf(year)};
        Cursor cursor = database.rawQuery("select * from question where year=?",years);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();//将cursor移动到第一个光标上
            int count = cursor.getCount();
            //将cursor中的每一条记录生成一个question对象，并将该question对象添加到list中
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                Question question = new Question();
                question.ID = cursor.getInt(cursor.getColumnIndex("qid"));
                question.tid=cursor.getInt(cursor.getColumnIndex("tid"));
                question.question = cursor.getString(cursor.getColumnIndex("question"));
                question.answerA = cursor.getString(cursor.getColumnIndex("choiceA"));
                question.answerB = cursor.getString(cursor.getColumnIndex("choiceB"));
                question.answerC = cursor.getString(cursor.getColumnIndex("choiceC"));
                question.answerD = cursor.getString(cursor.getColumnIndex("choiceD"));
                question.answer = cursor.getString(cursor.getColumnIndex("answer"));
                question.Translation = cursor.getString(cursor.getColumnIndex("translation"));
                question.year = cursor.getInt(cursor.getColumnIndex("year"));
                question.done = cursor.getInt(cursor.getColumnIndex("done"));
                question.wrong = cursor.getInt(cursor.getColumnIndex("wrong"));
                question.collect=cursor.getInt(cursor.getColumnIndex("collect"));
                question.explaination = cursor.getString(cursor.getColumnIndex("analysis"));
                //表示没有选择任何选项
                question.selectedAnswer = "NULL";
                list.add(question);
            }
        }
        cursor.close();
        return list;
    }
    public List<Question> get_tid_Question(int tid) {
        this.openDatabase();
        List<Question> list = new ArrayList<Question>();
        /*
               Cursor是结果集游标，用于对结果集进行随机访问,其实Cursor与JDBC中的ResultSet作用很相似。
             rawQuery()方法的第一个参数为select语句；第二个参数为select语句中占位符参数的值，如果select语句没有使用占位符，该参数可以设置为null。*/
        String tids[]={String.valueOf(tid)};
        Cursor cursor = database.rawQuery("select * from question where tid=?",tids);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();//将cursor移动到第一个光标上
            int count = cursor.getCount();
            //将cursor中的每一条记录生成一个question对象，并将该question对象添加到list中
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                Question question = new Question();
                question.ID = cursor.getInt(cursor.getColumnIndex("qid"));
                question.tid=cursor.getInt(cursor.getColumnIndex("tid"));
                question.question = cursor.getString(cursor.getColumnIndex("question"));
                question.answerA = cursor.getString(cursor.getColumnIndex("choiceA"));
                question.answerB = cursor.getString(cursor.getColumnIndex("choiceB"));
                question.answerC = cursor.getString(cursor.getColumnIndex("choiceC"));
                question.answerD = cursor.getString(cursor.getColumnIndex("choiceD"));
                question.answer = cursor.getString(cursor.getColumnIndex("answer"));
                question.Translation = cursor.getString(cursor.getColumnIndex("translation"));
                question.year = cursor.getInt(cursor.getColumnIndex("year"));
                question.done = cursor.getInt(cursor.getColumnIndex("done"));
                question.wrong = cursor.getInt(cursor.getColumnIndex("wrong"));
                question.collect=cursor.getInt(cursor.getColumnIndex("collect"));
                question.explaination = cursor.getString(cursor.getColumnIndex("analysis"));
                //表示没有选择任何选项
                question.selectedAnswer = "NULL";
                list.add(question);
            }
        }
        cursor.close();
        return list;
    }
    public List<Question> get_Error_Question() {
        this.openDatabase();
        List<Question> list = new ArrayList<Question>();
        /*
               Cursor是结果集游标，用于对结果集进行随机访问,其实Cursor与JDBC中的ResultSet作用很相似。
             rawQuery()方法的第一个参数为select语句；第二个参数为select语句中占位符参数的值，如果select语句没有使用占位符，该参数可以设置为null。*/
        Cursor cursor = database.rawQuery("select * from question where wrong = 1",null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();//将cursor移动到第一个光标上
            int count = cursor.getCount();
            //将cursor中的每一条记录生成一个question对象，并将该question对象添加到list中
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                Question question = new Question();
                question.ID = cursor.getInt(cursor.getColumnIndex("qid"));
                question.tid=cursor.getInt(cursor.getColumnIndex("tid"));
                question.question = cursor.getString(cursor.getColumnIndex("question"));
                question.answerA = cursor.getString(cursor.getColumnIndex("choiceA"));
                question.answerB = cursor.getString(cursor.getColumnIndex("choiceB"));
                question.answerC = cursor.getString(cursor.getColumnIndex("choiceC"));
                question.answerD = cursor.getString(cursor.getColumnIndex("choiceD"));
                question.answer = cursor.getString(cursor.getColumnIndex("answer"));
                question.Translation = cursor.getString(cursor.getColumnIndex("translation"));
                question.year = cursor.getInt(cursor.getColumnIndex("year"));
                question.done = cursor.getInt(cursor.getColumnIndex("done"));
                question.collect=cursor.getInt(cursor.getColumnIndex("collect"));
                question.wrong = cursor.getInt(cursor.getColumnIndex("wrong"));
                question.explaination = cursor.getString(cursor.getColumnIndex("analysis"));
                //表示没有选择任何选项
                question.selectedAnswer = "NULL";
                list.add(question);
            }
        }
        cursor.close();
        return list;
    }

    public List<Question> get_Collect_Question() {
        this.openDatabase();
        List<Question> list = new ArrayList<Question>();
        /*
               Cursor是结果集游标，用于对结果集进行随机访问,其实Cursor与JDBC中的ResultSet作用很相似。
             rawQuery()方法的第一个参数为select语句；第二个参数为select语句中占位符参数的值，如果select语句没有使用占位符，该参数可以设置为null。*/
        Cursor cursor = database.rawQuery("select * from question where collect = 1",null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();//将cursor移动到第一个光标上
            int count = cursor.getCount();
            //将cursor中的每一条记录生成一个question对象，并将该question对象添加到list中
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                Question question = new Question();
                question.ID = cursor.getInt(cursor.getColumnIndex("qid"));
                question.tid=cursor.getInt(cursor.getColumnIndex("tid"));
                question.question = cursor.getString(cursor.getColumnIndex("question"));
                question.answerA = cursor.getString(cursor.getColumnIndex("choiceA"));
                question.answerB = cursor.getString(cursor.getColumnIndex("choiceB"));
                question.answerC = cursor.getString(cursor.getColumnIndex("choiceC"));
                question.answerD = cursor.getString(cursor.getColumnIndex("choiceD"));
                question.answer = cursor.getString(cursor.getColumnIndex("answer"));
                question.Translation = cursor.getString(cursor.getColumnIndex("translation"));
                question.year = cursor.getInt(cursor.getColumnIndex("year"));
                question.done = cursor.getInt(cursor.getColumnIndex("done"));
                question.wrong = cursor.getInt(cursor.getColumnIndex("wrong"));
                question.explaination = cursor.getString(cursor.getColumnIndex("analysis"));
                question.collect=cursor.getInt(cursor.getColumnIndex("collect"));
                //表示没有选择任何选项
                question.selectedAnswer = "NULL";
                list.add(question);
            }
        }
        cursor.close();
        return list;
    }
    public List<Integer> get_year() {
        this.openDatabase();
        List<Integer> list = new ArrayList<Integer>();
        /*
               Cursor是结果集游标，用于对结果集进行随机访问,其实Cursor与JDBC中的ResultSet作用很相似。
             rawQuery()方法的第一个参数为select语句；第二个参数为select语句中占位符参数的值，如果select语句没有使用占位符，该参数可以设置为null。*/
        Cursor cursor = database.rawQuery("select year from question group by year",null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();//将cursor移动到第一个光标上
            int count = cursor.getCount();
            //将cursor中的每一条记录生成一个question对象，并将该question对象添加到list中
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);

                int year = cursor.getInt(cursor.getColumnIndex("year"));
                list.add(year);
            }
        }
        cursor.close();
        return list;
    }
    public List<Integer> get_tid() {
        this.openDatabase();
        List<Integer> list = new ArrayList<Integer>();
        /*
               Cursor是结果集游标，用于对结果集进行随机访问,其实Cursor与JDBC中的ResultSet作用很相似。
             rawQuery()方法的第一个参数为select语句；第二个参数为select语句中占位符参数的值，如果select语句没有使用占位符，该参数可以设置为null。*/
        Cursor cursor = database.rawQuery("select tid from question group by tid",null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();//将cursor移动到第一个光标上
            int count = cursor.getCount();
            //将cursor中的每一条记录生成一个question对象，并将该question对象添加到list中
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);

                int tid = cursor.getInt(cursor.getColumnIndex("tid"));
                list.add(tid);
            }
        }
        cursor.close();
        return list;
    }
    public void Delete_Error(int qid)
    {
        this.openDatabase();
        String[] str_qid={String.valueOf(qid)};
        database.beginTransaction();
        database.execSQL("update question set wrong = 0 where qid = ?",str_qid);
        database.setTransactionSuccessful();
        database.endTransaction();
    }
    public void Insert_Error(int qid)
    {
        this.openDatabase();
        String[] str_qid={String.valueOf(qid)};
        database.beginTransaction();
        database.execSQL("update question set wrong = 1 where qid = ?",str_qid);
        database.setTransactionSuccessful();
        database.endTransaction();
    }
    public void Insert_Collect(int qid)
    {
        this.openDatabase();
        String[] str_qid={String.valueOf(qid)};
        database.beginTransaction();
        database.execSQL("update question set collect = 1 where qid = ?",str_qid);
        database.setTransactionSuccessful();
        database.endTransaction();

    }
    public void Delete_Collect(int qid)
    {
        this.openDatabase();
        String[] str_qid={String.valueOf(qid)};
        database.beginTransaction();
        database.execSQL("update question set collect = 0 where qid = ?",str_qid);
        database.setTransactionSuccessful();
        database.endTransaction();
    }
}
