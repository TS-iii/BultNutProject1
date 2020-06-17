package com.example.bluetooth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    TextView textview;  // 기기번호
    EditText editText;  // 이름
    EditText editText2;  // 참고사항
    ArrayList<PersonInfo> result;
    String mac;
    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;

    Handler mBluetoothHandler;
    ConnectedBluetoothThread mThreadConnectedBluetooth;
    BluetoothDevice mBluetoothDevice;
    BluetoothSocket mBluetoothSocket;

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        textview = findViewById(R.id.textView);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mBluetoothHandler=new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what==BT_MESSAGE_READ){
                    String readMessage=null;
                    try{
                        readMessage=new String((byte[])msg.obj , "UTF-8");
                    }catch(UnsupportedEncodingException e){ e.printStackTrace();}

                    textview.setText(readMessage);
                }
            }
        };


        result=new ArrayList<PersonInfo>();
        result=getIntent().getParcelableArrayListExtra("key");


        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listPairedDevices();


            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp=textview.getText().toString();
                int device=Integer.parseInt(temp);


                for(PersonInfo d : result){

                    if(d.device==device){


                            // 만약 이미 기기가 등록되어있다면.
                    }


                }




                Intent intent = new Intent();
                intent.putExtra("name", editText.getText().toString());
                intent.putExtra("device", textview.getText().toString());
                intent.putExtra("contents", editText2.getText().toString());
                intent.putExtra("mac",mac);



                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }



    void listPairedDevices() {

        if (mBluetoothAdapter.isEnabled()) {
            mPairedDevices = mBluetoothAdapter.getBondedDevices();

            if (mPairedDevices.size() > 0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("장치 선택");

                mListPairedDevices = new ArrayList<String>();

                for (BluetoothDevice device : mPairedDevices) {
                    mListPairedDevices.add(device.getName());
                }

                final CharSequence[] items = mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        connectSelectedDevice(items[item].toString());

                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            } else {
                Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다", Toast.LENGTH_LONG).show();
            }
        } else {

            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되어 있습니다.", Toast.LENGTH_LONG).show();
        }


    }


    void connectSelectedDevice(String selectedDeviceName) {
        for (BluetoothDevice tempDevice : mPairedDevices) {
            if (selectedDeviceName.equals(tempDevice.getName())) {
                mBluetoothDevice = tempDevice;
                mac= mBluetoothDevice.getAddress();
                break;
            }
        }

        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket.connect();
            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
            mThreadConnectedBluetooth.start();

            mThreadConnectedBluetooth.write("0");

            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류 발생", Toast.LENGTH_LONG).show();

        }


    }


    private class ConnectedBluetoothThread extends Thread{

        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedBluetoothThread(BluetoothSocket socket){

            mmSocket=socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try{
                tmpIn=socket.getInputStream();
                tmpOut=socket.getOutputStream();

            } catch(IOException e){
                Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }

            mmInStream=tmpIn;
            mmOutStream=tmpOut;

        }

        public void run(){
            byte[] buffer=new byte[1024];
            int bytes;

            while(true){

                try{
                    bytes=mmInStream.available();
                    if(bytes!=0){
                        SystemClock.sleep(100);
                        bytes=mmInStream.available();
                        bytes=mmInStream.read(buffer,0,bytes);
                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ,bytes,-1,buffer).sendToTarget();
                    }
                } catch(IOException e){ break; }
            }
        }

    public void write(String str){
            byte[] bytes=str.getBytes();
            try{
                mmOutStream.write(bytes);
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();

            }
    }

    public void cancel(){
            try{
                mmSocket.close();
            } catch(IOException e){
                Toast.makeText(getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();

            }
    }


    }

}