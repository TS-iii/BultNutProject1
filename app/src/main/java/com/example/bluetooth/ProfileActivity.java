package com.example.bluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {


    // 수정
    EditText editText;
    EditText editText2;
    TextView textView;

    // 조회
    TextView textView2;
    TextView textView3;
    TextView textView4;

    LinearLayout linearLayout;
    LinearLayout linearLayout1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // 수정
      editText=findViewById(R.id.profileEditTextName);
        editText2=findViewById(R.id.profileEditTextContents);
        textView=findViewById(R.id.profileTextViewDevice1);

        // 조회
         textView2=findViewById(R.id.profileTextViewName);
         textView3=findViewById(R.id.profileTextViewDevice);
        textView4=findViewById(R.id.prfileTextViewContents);

         linearLayout=findViewById(R.id.showView);
       linearLayout1=findViewById(R.id.updateView);

        Intent intent=getIntent();

        Integer device=intent.getIntExtra("device",0);
        String temp=device.toString();
        textView2.setText(intent.getStringExtra("name"));
        textView3.setText(temp);
        textView4.setText(intent.getStringExtra("contents"));

        editText.setText(intent.getStringExtra("name"));
        editText2.setText(intent.getStringExtra("contents"));
        textView.setText(temp);

        Button button=findViewById(R.id.updateButton);
        Button button1=findViewById(R.id.deleteButton);
        Button button2=findViewById(R.id.profileUpdateButton);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                linearLayout1.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);

            }
        });

        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                String device=textView3.getText().toString();

                Intent intent=new Intent();
                intent.putExtra("device",device);
                intent.putExtra("flag","0"); //delete

                setResult(RESULT_OK, intent);
                finish();

            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String name=editText.getText().toString();
                String device=textView.getText().toString();
                String contents=editText2.getText().toString();

                Intent intent=new Intent();
                intent.putExtra("name",name);
                intent.putExtra("device",device);
                intent.putExtra("contents",contents);
                intent.putExtra("flag","1");

                setResult(RESULT_OK,intent);
                finish();

            }
        });

    }
}