package kimnabal.com.homeproject;

import android.app.Activity;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by puren_000 on 2015-12-01.
 */
public class TcpNetService {
    private static final String TAG = "TcpNetService";
    private static final int onConn = 1;
    private static final int onClose = -1;
    private Activity mActivity;
    private Handler mHandler;
    private Socket socket;
    private BufferedReader tcpReader;
    private BufferedWriter tcpWrite;
    private int port = 15000;
    private String ip = "115.68.29.149";


    public TcpNetService(Activity ac, Handler h) {
        mActivity = ac;
        mHandler = h;


    }

    public void connectServer() throws IOException {
        try {
            settingConn(ip, port);

        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }


    }

    private void settingConn(String ip, int port) throws IOException {
        try {
            socket = new Socket(ip, port);
            tcpWrite = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            tcpReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();

        }

    }

    public void sendData(String s) throws IOException {
        if (socket != null) {

            tcpWrite.write(s, 0, s.length());
            tcpWrite.flush();
        }
    }

    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }

    }


}
