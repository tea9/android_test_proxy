package com.demo.testproxy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.testproxy.util.RootCmd;

import java.util.Map;
import java.util.Properties;

/**
 * created by tea9 at 2019-08-09
 *
 * 要root的。。。。
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG="FFFFFFFFF";//按f键进入坦克^ - ^!
    private TextView text;
    private Button btn1,btn2,btn3;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        edit = findViewById(R.id.edit);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        refresh();
    }

    private void setProxy() {
        String proxyStr = edit.getText().toString().trim();
//        Log.e(TAG,ShCmd.execRootCmd("settings put global http_proxy 192.168.50.65:7777"));
//        RootCmd.execRootCmdSilent("settings put global http_proxy 192.168.50.65:9999");
        RootCmd.execRootCmdSilent("settings put global http_proxy "+proxyStr);
        Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
    }
    //移除代理需要重启手机方可生效，设置可直接多次覆盖，不需要移除
    private void clearProxy() {
//        Log.e(TAG,RootCmd.execRootCmd("settings delete global http_proxy"));
//        Log.e(TAG,RootCmd.execRootCmd("settings delete global global_http_proxy_host"));
//        Log.e(TAG,RootCmd.execRootCmd("settings delete global global_http_proxy_port"));
        RootCmd.execRootCmdSilent("settings delete global http_proxy");
        RootCmd.execRootCmdSilent("settings delete global global_http_proxy_host");
        RootCmd.execRootCmdSilent("settings delete global global_http_proxy_port");
    }

    private void refresh() {
        StringBuilder sb=new StringBuilder();
        Properties properties = System.getProperties();
        for (Map.Entry<Object, Object> property : properties.entrySet()) {
            Log.d(TAG, property.getKey() + "=" + property.getValue());
            sb.append(property.getKey() + "=" + property.getValue()+"\n");
        }

        // 仅读取代理host和post
        String x=System.getProperty("http.proxyHost"); // https.proxyHost
        String y=System.getProperty("http.proxyPort"); // https.proxyPort
        Log.e(TAG,x);
        Log.e(TAG,y);
        sb.append("http.proxyHost" + "=" + System.getProperty("http.proxyHost")+"\n");
        sb.append("http.proxyPort" + "=" + System.getProperty("http.proxyPort")+"\n");
        text.setText(sb.toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                setProxy();
                break;
            case R.id.btn2:
                clearProxy();
                break;
            case R.id.btn3:
                refresh();
                break;
        }
    }
}
