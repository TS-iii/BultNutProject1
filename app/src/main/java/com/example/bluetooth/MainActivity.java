package com.example.bluetooth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final int REQUEST_CODE_MENU=101;
    PersonAdapter adapter;

    PersonDatabase database;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE_MENU){
            Toast.makeText(getApplicationContext(),"onActivityResult 결과 메서드 호출됨",Toast.LENGTH_LONG).show();


            if(resultCode==RESULT_OK){
                String name=data.getStringExtra("name");
                String contents=data.getStringExtra("contents");
                String device=data.getStringExtra("device");
                int i=0;
                for( i=0;i<device.length();i++){

                    if(device.charAt(i)=='\0') {
                        break;
                    }

                }

                device=device.substring(0,i);
                Integer temp=Integer.parseInt(device);
                Toast.makeText(getApplicationContext(),"name:"+name+", device:"+device+"contents:"+contents,Toast.LENGTH_LONG).show();

                adapter.addItem(new PersonInfo(name,temp,contents));
                adapter.notifyDataSetChanged();

                database.insertRecord(name,temp,contents);
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=findViewById(R.id.button);

       RecyclerView recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);


    adapter=new PersonAdapter();

    if(database!=null){
        database.close();
        database=null;
    }

    database=PersonDatabase.getInstance(this);
    boolean isOpen=database.open();
    if(isOpen){
        Log.d(TAG,"Book database is open");

    } else { Log.d(TAG,"Book db is not open"); }

        AutoPermissions.Companion.loadAllPermissions(this,101);

    ArrayList<PersonInfo> result=database.selectAll();
   adapter.setItems(result);

    recyclerView.setAdapter(adapter);

    button.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){

    Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
    startActivityForResult(intent,REQUEST_CODE_MENU);

        }
    });



    }
}