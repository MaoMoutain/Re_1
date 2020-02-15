package com.example.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class activity_answeractivity extends AppCompatActivity {
    /*-------------------------------------------------------------------------------------------------------*/
    private  final int COMMON =0;
    private final int ERROR =1;
    private final int COLLECT =2;
    private  final int COMMON_GETQUESTION =3;
    private  final int POINT =4;
    private  final int POINT_GETQUESTION =5;
    private  int years[]={2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016};
    private  int year;
    private  int tid;
    private  int i;
    /*-----------------------------------------------------------------------------------------------*/

    private List<Question> que;
    private int FLAG;
    /*--------------------------------------------------------------------------------------------------*/
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
    private RadioButton cb1;
    private RadioButton cb2;
    private RadioButton cb3;
    private RadioButton cb4;
    private Question title_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answeractivity);
        ActivityCollector.addActivity(this);
        Intent getFlag=getIntent();
        FLAG=getFlag.getIntExtra("FLAG",-1);
        i=getFlag.getIntExtra("I",-1);
        Intent getdate=getIntent();
        title_data=(Question) getdate.getSerializableExtra("Question");
        if (FLAG==-1)
        {
            Toast.makeText(activity_answeractivity.this,"拉取FLAG失败！",Toast.LENGTH_SHORT).show();
        }
        if (FLAG==COMMON_GETQUESTION)
        {
            Intent get_year=getIntent();
            year=get_year.getIntExtra("YEAR",-1);
            DBService dbService = new DBService(this);
            dbService.verifyStoragePermissions(this);
            que=dbService.get_year_Question(year);
            dbService.closeDatabase();
            if (title_data.collect==1)
            {
                final Button collect_btn=findViewById(R.id.btn_collect);
                collect_btn.setText("已收藏");
                collect_btn.setEnabled(false);
            }
        }
        if (FLAG==POINT_GETQUESTION)
        {
            Intent get_year=getIntent();
            tid=get_year.getIntExtra("TID",-1);
            DBService dbService = new DBService(this);
            dbService.verifyStoragePermissions(this);
            que=dbService.get_tid_Question(tid);
            dbService.closeDatabase();
            if (title_data.collect==1)
            {
                final Button collect_btn=findViewById(R.id.btn_collect);
                collect_btn.setText("已收藏");
                collect_btn.setEnabled(false);
            }
        }
        if (FLAG==ERROR)
        {
            DBService dbService = new DBService(this);
            dbService.verifyStoragePermissions(this);
            que=dbService.get_Error_Question();
            dbService.closeDatabase();
            final Button home_btn=findViewById(R.id.btn_home);
            home_btn.setText("删除");
        }
        if (FLAG==COLLECT)
        {
            DBService dbService = new DBService(this);
            dbService.verifyStoragePermissions(this);
            que=dbService.get_Collect_Question();
            dbService.closeDatabase();
            final Button collect_btn=findViewById(R.id.btn_collect);
            collect_btn.setText("删除");
        }
        //实现返回主界面的功能
        /*----------------------------------------------------------------------------------------------*/
        Button button_return_home=(Button)findViewById(R.id.btn_home);
        button_return_home.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (FLAG==ERROR) {
                    DBService dbService = new DBService(activity_answeractivity.this);
                    dbService.verifyStoragePermissions(activity_answeractivity.this);
                    dbService.Delete_Error(que.get(i).ID);
                    dbService.closeDatabase();
                    final Button collect_btn=findViewById(R.id.btn_home);
                    collect_btn.setText("已删除");
                    collect_btn.setEnabled(false);
                }
                else {
                    Intent intent_return_home = new Intent(activity_answeractivity.this, activity_main.class);
                    startActivity(intent_return_home);
                }
            }
        });
        /*----------------------------------------------------------------------------------------*/


        //数据加载
        /*------------------------------------------------------------------------------------------*/
        TextView tvQue = findViewById(R.id.tv_title);
        tvQue.setText(title_data.question);
        cb1 = findViewById(R.id.A);
        cb2 = findViewById(R.id.B);
        cb3 = findViewById(R.id.C);
        cb4 = findViewById(R.id.D);
        cb1.setText("A."+title_data.answerA);
        cb2.setText("B."+title_data.answerB);
        cb3.setText("C."+title_data.answerC);
        cb4.setText("D."+title_data.answerD);
        final TextView tvAns = findViewById(R.id.tv_result);
        final TextView tvAnalysis = findViewById(R.id.text_analysis);
        tvAns.setText("答案:"+title_data.answer);
        tvAnalysis.setText("解析:"+title_data.explaination+"\n翻译:"+title_data.Translation);
        /*------------------------------------------------------------------------------------------*/



        //逻辑判断
        /*-------------------------------------------------------------------------------*/
        Button button_ifanswer =findViewById(R.id.btn_result);
        final RadioGroup rg=findViewById(R.id.mRadioGroup);
        button_ifanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //每次点击时刷新RadioButtonGround
                RadioButton select_btn_0=findViewById(R.id.A);
                select_btn_0.setBackgroundColor(Color.parseColor("#FFFFFF"));
                RadioButton select_btn_1=findViewById(R.id.B);
                select_btn_1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                RadioButton select_btn_2=findViewById(R.id.C);
                select_btn_2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                RadioButton select_btn_3=findViewById(R.id.D);
                select_btn_3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                if (rg.getCheckedRadioButtonId()==R.id.A)
                {
                    title_data.selectedAnswer="A";
                }
                else if (rg.getCheckedRadioButtonId()==R.id.B)
                {
                    title_data.selectedAnswer="B";
                } else if (rg.getCheckedRadioButtonId()==R.id.C)
                {
                    title_data.selectedAnswer="C";
                } else if (rg.getCheckedRadioButtonId()==R.id.D)
                {
                    title_data.selectedAnswer="D";
                }
                if (title_data.selectedAnswer.equals(title_data.answer))
                {
                    RadioButton select_btn=findViewById(rg.getCheckedRadioButtonId());
                    //Toast.makeText(activity_answeractivity.this,"答对了！！！",Toast.LENGTH_SHORT).show();
                    select_btn.setBackgroundColor(Color.parseColor("#7CFC00"));
                    tvAns.setVisibility(View.VISIBLE);
                    tvAnalysis.setVisibility(View.VISIBLE);
                }
                else if (title_data.selectedAnswer.equals("NULL")) { }
                else
                {
                    RadioButton select_btn=findViewById(rg.getCheckedRadioButtonId());
                    //Toast.makeText(activity_answeractivity.this,"答错了！！！",Toast.LENGTH_SHORT).show();
                    select_btn.setBackgroundColor(Color.parseColor("#DC143C"));
                    tvAns.setVisibility(View.VISIBLE);
                    tvAnalysis.setVisibility(View.VISIBLE);
                    if (FLAG!=ERROR) {
                        DBService dbService = new DBService(activity_answeractivity.this);
                        dbService.verifyStoragePermissions(activity_answeractivity.this);
                        dbService.Insert_Error(que.get(i).ID);
                        dbService.closeDatabase();
                        Toast.makeText(activity_answeractivity.this, "已加入错题库！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        /*------------------------------------------------------------------------------------------*/
        //上一题，下一题按钮逻辑
        Button SkipTitle_btn_up=findViewById(R.id.btn_up);
        SkipTitle_btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title_data.ID==que.get(0).ID)
                {
                    Toast.makeText(activity_answeractivity.this,"已经是第一题了！",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    i=i-1;
                    Question question =que.get(i);
                    Intent skipanswerquestion=new Intent(activity_answeractivity.this,activity_answeractivity.class);
                    skipanswerquestion.putExtra("Question",question);
                    skipanswerquestion.putExtra("YEAR",year);
                    skipanswerquestion.putExtra("TID",tid);
                    skipanswerquestion.putExtra("I",i);
                    skipanswerquestion.putExtra("FLAG",FLAG);
                    startActivity(skipanswerquestion);
                }
            }
        });
        Button SkipTitle_btn_done=findViewById(R.id.btn_down);
        SkipTitle_btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title_data.ID==que.get(que.size()-1).ID)
                {
                    Toast.makeText(activity_answeractivity.this,"已经是最后一题了！",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    i=i+1;
                    Question question =que.get(i);
                    Intent skipanswerquestion=new Intent(activity_answeractivity.this,activity_answeractivity.class);
                    skipanswerquestion.putExtra("Question",question);
                    skipanswerquestion.putExtra("YEAR",year);
                    skipanswerquestion.putExtra("I",i);
                    skipanswerquestion.putExtra("TID",tid);
                    skipanswerquestion.putExtra("FLAG",FLAG);
                    startActivity(skipanswerquestion);
//
                }
            }
        });
        /*------------------------------------------------------------------------------------------*/
        final Button collect_btn=findViewById(R.id.btn_collect);
        collect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FLAG==COLLECT)
                {
                    DBService dbService = new DBService(activity_answeractivity.this);
                    dbService.verifyStoragePermissions(activity_answeractivity.this);
                    dbService.Delete_Collect(que.get(i).ID);
                    dbService.closeDatabase();
                    final Button collect_btn=findViewById(R.id.btn_collect);
                    collect_btn.setText("已删除");
                    collect_btn.setEnabled(false);
                }
                else
                {
                    DBService dbService = new DBService(activity_answeractivity.this);
                    dbService.verifyStoragePermissions(activity_answeractivity.this);
                    dbService.Insert_Collect(que.get(i).ID);
                    dbService.closeDatabase();
                    final Button collect_btn=findViewById(R.id.btn_collect);
                    collect_btn.setText("已收藏");
                    collect_btn.setEnabled(false);
                }
            }
        });
    }

}
