package kimnabal.com.homeproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "BluetoothService";
    private static final int B_ACTIVITY = 0;
    private static final int REQUEST_ENABLE_BT = 2;
    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }

    };
    private BluetoothService btService = null;
    private TcpNetService tcpNetService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnComOn;
        Button btnComOff;
        Button btnBlueStart;
        Button btnNetConnStart;


        if (btService == null) {
            btService = new BluetoothService(this, mHandler);
        }
        if (tcpNetService == null) {
            tcpNetService = new TcpNetService(this, mHandler);
        }

        btnNetConnStart = (Button) findViewById(R.id.btnNetConnStart);
        btnNetConnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "네트원서버접속시작", Toast.LENGTH_LONG).show();

                try {
                    new Thread() {
                        public void run() {
                            try {
                                tcpNetService.connectServer();

                                tcpNetService.sendData("ok");
                                tcpNetService.close();
                            } catch (IOException e) {
                                e.printStackTrace();

                            }


                        }

                    }.start();
                } catch (IllegalThreadStateException e) {
                    e.printStackTrace();
                }

            }
        });


        btnBlueStart = (Button) findViewById(R.id.btnBlueStart);
        btnBlueStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "블루투스시작", Toast.LENGTH_LONG).show();
                if (btService.getDeviceState()) {
                    // 블루투스가 지원 가능한 기기일 때
                    btService.enableBluetooth();
                } else {
                    finish();
                }
            }
        });

        btnComOn = (Button) findViewById(R.id.btnComOn);
        btnComOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "컴퓨터 오픈", Toast.LENGTH_LONG).show();
            }
        });

        btnComOff = (Button) findViewById(R.id.btnComOff);
        btnComOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "컴퓨터 오프", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // 확인 눌렀을 때
                    //Next Step
                } else {
                    // 취소 눌렀을 때
                    Log.d(TAG, "Bluetooth is not enabled");
                }
                break;
        }
    }


}
