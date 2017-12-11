package com.example.tatsuki.itacrch2017;


import android.os.Bundle;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;

public class MainActivity extends Activity {
    private EditText editText;
    private TextView textView;
    private CalculatorService mService;
    private  Intent tempIntent = new Intent(CalculatorService.class.getName());

    String text="0";
    int sum =0;
    /*
        private ServiceConnection mServiceConnection = new ServiceConnection() {
            // Called when the connection with the service is established
            public void onServiceConnected(ComponentName className, IBinder service) {
                // Following the example above for an AIDL interface,
                // this gets an instance of the IRemoteInterface, which we can use to call on the service
                mService = CalculatorService.Stub.asInterface(service);


                try {
                    sum = mService.calservice(text);

                }catch(RemoteException e){
                    Log.d("error","error");
                }
            }
        // Called when the connection with the service disconnects unexpectedly
            public void onServiceDisconnected(ComponentName className) {
                Log.d("service", "Service has unexpectedly disconnected");
                mService = null;
            }
        };
    */
    private ServiceConnection mServiceConnection =
            new ServiceConnection() { //【2】

                @Override
                public void onServiceDisconnected(ComponentName name) {

                    mService = null;
                }

                @Override
                public void onServiceConnected(
                        ComponentName name, IBinder service) { //【3】
                    mService = CalculatorService.Stub.asInterface(service); //【4】
                    try {
                        sum = mService.calservice(text);

                    }catch (RemoteException e){
                        Log.d("hoge","hoge");
                    }
                }
            };

    public void cal(String a)
            throws NumberFormatException, RemoteException {
        sum = mService.calservice(a); //【8】
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tempIntent.setPackage("com.example.tatsuki.itacrch2017");
        bindService(tempIntent, //【5】
                mServiceConnection, BIND_AUTO_CREATE); //【6】


        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.edit_Text);
        //  editText = findViewById(R.id.edit_text);

        textView = (TextView)findViewById(R.id.text_view);

        //  textView = findViewById(R.id.text_view);


        Button button = (Button)findViewById(R.id.button);
        // Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // エディットテキストのテキストを取得

                text = editText.getText().toString();
                try {
                    cal(text);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                textView.setText(String.valueOf(sum)+"文字");

            }
        });
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mService != null) {
            unbindService(mServiceConnection); //【7】
        }
    }

}

