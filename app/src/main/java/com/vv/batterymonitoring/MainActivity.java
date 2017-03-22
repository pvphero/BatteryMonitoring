package com.vv.batterymonitoring;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vv.batterymonitoring.view.BatteryView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.tv_battery)
    TextView tvBattery;
    @BindView(R.id.battery)
    BatteryView battery;
    @BindView(R.id.rl_device_battery)
    RelativeLayout rlDeviceBattery;
    @BindView(R.id.tv_voltage)
    TextView tvVoltage;
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.tv_electricity)
    TextView tvLevel;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_health)
    TextView tvHealth;
    @BindView(R.id.tv_technology)
    TextView tvTechnology;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        initReceiver();
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        //蓝牙相关广播
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        mContext.registerReceiver(mReceiver, intentFilter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            int voltage = arg1.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            tvVoltage.setText("电压：" + voltage / 1000 + "." + voltage % 1000 + "V");

            int temperature = arg1.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            tvTemperature.setText("温度：" + temperature / 10 + "." + temperature % 10 + "℃");
            if (temperature >= 300) {
                tvTemperature.setTextColor(Color.RED);
            } else {
                tvTemperature.setTextColor(Color.BLUE);
            }

            int level = arg1.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = arg1.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            int levelPercent = (int) (((float) level / scale) * 100);
            tvLevel.setText("电量：" + levelPercent + "%");
            if (level <= 10) {
                tvLevel.setTextColor(Color.RED);
            } else {
                tvLevel.setTextColor(Color.BLUE);
            }

            battery.setBattery(levelPercent);
            int status = arg1.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
            String strStatus = "未知状态";
            ;
            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    strStatus = "充电中……";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    strStatus = "放电中……";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    strStatus = "未充电";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    strStatus = "充电完成";
                    break;
            }
            tvStatus.setText("状态：" + strStatus);

            int health = arg1.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN);
            String strHealth = "未知 :(";
            ;
            switch (status) {
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    strHealth = "好 :)";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    strHealth = "过热！";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD: // 未充电时就会显示此状态，这是什么鬼？
                    strHealth = "良好";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    strHealth = "电压过高！";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    strHealth = "未知 :(";
                    break;
                case BatteryManager.BATTERY_HEALTH_COLD:
                    strHealth = "过冷！";
                    break;
            }
            tvHealth.setText("健康状况：" + strHealth);

            String technology = arg1.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            tvTechnology.setText("电池技术：" + technology);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destoryReceiver();
    }

    private void destoryReceiver() {
        if (mContext != null) {
            mContext.unregisterReceiver(mReceiver);
        }
    }
}
