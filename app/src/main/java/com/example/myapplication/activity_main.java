package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import java.io.File;
import java.util.List;

public class activity_main extends AppCompatActivity {

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
    //这是三个按钮传入写题的标志
    final private int COMMON =0;
    final private int ERROR =1;
    final private int COLLECT =2;
    final private int POINT =4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCollector.addActivity(this);
        //隐藏自带标题栏
        ActionBar actionbar=getSupportActionBar();
        if (actionbar!=null){
            actionbar.hide();
        }

        //应用启动时，判断数据库是否存在，不存在则将提前打包好的数据库文件复制到数据库目录下
        //数据库目录不存在时，创建数据库目录
        //创建三个按钮对象，实现开始答题，进入我的错题本，我的收藏的功能
        Button button_start=(Button)findViewById(R.id.start);
        Button button_my_fault_test=(Button)findViewById(R.id.my_fault_test);
        Button button_my_stock=(Button)findViewById(R.id.my_stock);
        Button button_my_tidques=(Button)findViewById(R.id.point);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent0=new Intent(activity_main.this,activity_listprj.class);
                intent0.putExtra("FLAG",COMMON);
                startActivity(intent0);
            }
        });
        button_my_fault_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(activity_main.this,activity_listprj.class);
                intent1.putExtra("FLAG",ERROR);
                startActivity(intent1);
            }
        });
        button_my_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(activity_main.this,activity_listprj.class);
                intent2.putExtra("FLAG",COLLECT);
                startActivity(intent2);
            }
        });
        button_my_tidques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(activity_main.this,activity_listprj.class);
                intent2.putExtra("FLAG",POINT);
                startActivity(intent2);
            }
        });

    }
}
