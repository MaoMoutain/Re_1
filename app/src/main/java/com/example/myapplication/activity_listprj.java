package com.example.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.style.QuoteSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class activity_listprj extends AppCompatActivity {

    /*------------------------------------------------------------------------*/
    private int is_year;

    private int is_tid;
    //标志变量，判断进入模式
    private  final int COMMON =0;
    private  final int COMMON_GETQUESTION =3;
    private final int ERROR =1;
    private final int COLLECT =2;
    private final int POINT =4;
    private final int POINT_GETQUESTION =5;
    private  final String[] tid_string={"词汇辨析","词汇支配","代词","动词","复合句","国情","简单句","名词","前置句","数词","形容词(副词)","言语礼节"};
    /*------------------------------------------------------------------------*/
    private List<String> list;
    private List<Question> que;
    private List<Integer> years;
    private List<Integer> tids;
    int  FLAG;
    /*------------------------------------------------------------------------*/
    //创建菜单，第一个是有道翻译，第二个退出
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.translation:
                Intent intent_translation=new Intent(Intent.ACTION_VIEW);
                intent_translation.setData(Uri.parse("http://fanyi.youdao.com/"));
                startActivity(intent_translation);
                break;
            case R.id.finish:
                ActivityCollector.finishAll();
                break;
            default:
        }
        return  true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listprj);
        ActivityCollector.addActivity(this);
        //获取标志，进入不同的模式
        Intent getFlag=getIntent();
        FLAG=getFlag.getIntExtra("FLAG",-1);
        if (FLAG==-1)
        {
            Toast.makeText(activity_listprj.this,"拉取FLAG失败！",Toast.LENGTH_SHORT).show();
        }
        if (FLAG==COMMON)
        {
            DBService dbService = new DBService(this);
            dbService.verifyStoragePermissions(this);
            years =dbService.get_year();
            dbService.closeDatabase();
            list=new ArrayList<>();
            for (int i=0;i<years.size();i++)
                list.add(String.valueOf(years.get(i)));

            ArrayAdapter<String> alph_adapter = new ArrayAdapter<String>(activity_listprj.this, android.R.layout.simple_list_item_1, list);
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(alph_adapter);
        }
        if (FLAG==POINT)
        {
            DBService dbService = new DBService(this);
            dbService.verifyStoragePermissions(this);
            tids =dbService.get_tid();
            dbService.closeDatabase();
            list=new ArrayList<>();
            for (int i=0;i<tids.size();i++)
                list.add(tid_string[tids.get(i)-1]);

            ArrayAdapter<String> alph_adapter = new ArrayAdapter<String>(activity_listprj.this, android.R.layout.simple_list_item_1, list);
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(alph_adapter);
        }
        if (FLAG==ERROR)
        {
            DBService dbService = new DBService(this);
            dbService.verifyStoragePermissions(this);
            que =dbService.get_Error_Question();
            dbService.closeDatabase();
            list=new ArrayList<>();
            for (int i=0;i<que.size();i++)
                list.add(que.get(i).question);
            ArrayAdapter<String> alph_adapter = new ArrayAdapter<String>(activity_listprj.this, android.R.layout.simple_list_item_1, list);
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(alph_adapter);
        }
        if (FLAG==COLLECT)
        {
            DBService dbService = new DBService(this);
            dbService.verifyStoragePermissions(this);
            que =dbService.get_Collect_Question();
            dbService.closeDatabase();
            list=new ArrayList<>();
            for (int i=0;i<que.size();i++)
                list.add(que.get(i).question);
            ArrayAdapter<String> alph_adapter = new ArrayAdapter<String>(activity_listprj.this, android.R.layout.simple_list_item_1, list);
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(alph_adapter);

        }
        if (FLAG==COMMON_GETQUESTION)
        {
            Intent get_year=getIntent();
            int year=get_year.getIntExtra("YEAR",-1);
            is_year=year;
            DBService dbService = new DBService(this);
            dbService.verifyStoragePermissions(this);
            que =dbService.get_year_Question(year);
            dbService.closeDatabase();
            list=new ArrayList<>();
            for (int i=0;i<que.size();i++)
                list.add(que.get(i).question);
            ArrayAdapter<String> alph_adapter = new ArrayAdapter<String>(activity_listprj.this, android.R.layout.simple_list_item_1, list);
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(alph_adapter);
        }
        if (FLAG==POINT_GETQUESTION)
        {
            Intent get_year=getIntent();
            int tid=get_year.getIntExtra("TID",-1);
            is_tid=tid;
            DBService dbService = new DBService(this);
            dbService.verifyStoragePermissions(this);
            que =dbService.get_tid_Question(tid);
            dbService.closeDatabase();
            list=new ArrayList<>();
            for (int i=0;i<que.size();i++)
                list.add(que.get(i).question);
            ArrayAdapter<String> alph_adapter = new ArrayAdapter<String>(activity_listprj.this, android.R.layout.simple_list_item_1, list);
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(alph_adapter);
        }

        final ListView listView = (ListView) findViewById(R.id.list_view);
        /*-----------------------------------------------------------------------*/

        /*-----------------------------------------------------------------------*/
        //点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (FLAG==COMMON)
                {
                    FLAG=COMMON_GETQUESTION;
                    int year =years.get(i);
                    Intent startanswerquestion=new Intent(activity_listprj.this,activity_listprj.class);
                    startanswerquestion.putExtra("YEAR",year);
                    startanswerquestion.putExtra("FLAG",FLAG);
                    startActivity(startanswerquestion);
                    FLAG=COMMON;
                }
                if (FLAG==POINT)
                {
                    FLAG=POINT_GETQUESTION;
                    int tid =tids.get(i);
                    Intent startanswerquestion=new Intent(activity_listprj.this,activity_listprj.class);
                    startanswerquestion.putExtra("TID",tid);
                    startanswerquestion.putExtra("FLAG",FLAG);
                    startActivity(startanswerquestion);
                    FLAG=POINT;
                }
                else
                {
                    //Toast.makeText(activity_listprj.this,String.valueOf(is_year),Toast.LENGTH_SHORT).show();
                    Question question =que.get(i);
                    Intent startanswerquestion=new Intent(activity_listprj.this,activity_answeractivity.class);
                    startanswerquestion.putExtra("Question",question);
                    startanswerquestion.putExtra("TID",is_tid);
                    startanswerquestion.putExtra("YEAR",is_year);
                    startanswerquestion.putExtra("FLAG",FLAG);
                    startanswerquestion.putExtra("I",i);
                    startActivity(startanswerquestion);
                }
            }
        });
        /*-----------------------------------------------------------------------*/
    }
}
