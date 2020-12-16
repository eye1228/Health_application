package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MapsActivity extends AppCompatActivity implements LocationListener{
    private DBHelper db;
    private LocationManager locationManager;
    private Location mLastlocation = null;
    public TextView Speed;
    private double speed = 100;
    double lat;
    double loc;
    double vcc,V = 0;
    double timer = 0;
    double hour = 0, min = 0, sec = 0, TMI;
    double start;
    double firsttime = 0, lasttime = 0;
    double MET;
    String Vd = "";
    String LS = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        db = new DBHelper(this);
        Speed  = (TextView)findViewById(R.id.Speed);
        start = System.currentTimeMillis(); //시작 시간.
        firsttime = System.currentTimeMillis(); //???
        Button end = (Button) findViewById(R.id.end);
        //권한 체크
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100,0, this);
        end.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                double end = System.currentTimeMillis();//끝!
                setContentView(R.layout.activity_main4);
                TMI = (end - start) / 1000;
                hour = TMI / 3600; //end 랑 start 단위를 바꿔버려서?
                min = (TMI % 3600) / 60;
                sec = (TMI % 3600) % 60;
                timer = hour + min / 60 + sec / 3600; // 시간단위
                Vd = String.format("%.3f", V); //거리

                //평균 속도
                double Last_SPEED = V / timer;
                LS = String.format("%.1f", Last_SPEED);//평균 속도

                //kcal 계산식 대충 해보자 ㅎㅎ 일단 임시

                if(Last_SPEED < 2){
                    MET = 1.5;
                }
                else if(2 <= Last_SPEED ||  Last_SPEED < 2.7){
                    MET = 2.3;
                }
                else if(2 <= Last_SPEED ||  Last_SPEED < 2.7){
                    MET = 2.3;
                }
                else if(2.7 <= Last_SPEED ||  Last_SPEED < 4){
                    MET = 2.6;
                }
                else if(4 <= Last_SPEED ||  Last_SPEED < 4.8){
                    MET = 3.1;
                }
                else if(4.8 <= Last_SPEED ||  Last_SPEED < 5.1){
                    MET = 3.5;
                }
                else if(5.1 <= Last_SPEED ||  Last_SPEED < 5.5){
                    MET = 3.5;
                }
                else if(5.5 <= Last_SPEED ||  Last_SPEED < 5.64){
                    MET = 3.8;
                }
                else if(5.64 <= Last_SPEED ||  Last_SPEED < 6){
                    MET = 4;
                }
                else if(6 <= Last_SPEED ||  Last_SPEED < 6.42){
                    MET = 4.8;
                }
                else if(6.42 <= Last_SPEED ||  Last_SPEED < 7){
                    MET = 5.8;
                }
                else if(7 <= Last_SPEED ||  Last_SPEED < 7.5){
                    MET = 7;
                }
                else if(7.5 <= Last_SPEED ||  Last_SPEED < 8){
                    MET = 8;
                }
                else if(8 <= Last_SPEED ||  Last_SPEED < 8.5){
                    MET = 8.4;
                }
                else if(8.5 <= Last_SPEED ||  Last_SPEED < 9){
                    MET = 9;
                }
                else if(9 <= Last_SPEED ||  Last_SPEED < 10){
                    MET = 10;
                }
                else if(10 <= Last_SPEED ||  Last_SPEED < 11){
                    MET = 11;
                }
                else if(11 <= Last_SPEED ||  Last_SPEED < 12){
                    MET = 12;
                }
                else if(12 <= Last_SPEED){
                    MET = 13;
                }

                Cursor data = db.getData("id");
                String w = data.getString(4);
                double kg = Double.parseDouble(w);

                TextView distance = (TextView)findViewById(R.id.distance);
                TextView kcal = (TextView)findViewById(R.id.kcal);
                TextView LSPEED = (TextView)findViewById(R.id.Last_Speed);
                TextView TIME = (TextView) findViewById(R.id.time);

                String dis = Vd + "km";
                String KCAL = Kalori(MET, timer / 60, kg); //몸무게 불러와야하는데...귀찮은데...
                String LSP = LS + "km/h";
                String TM = (int)hour + "시간" + (int)min + "분" + (int)sec + "초";

                distance.setText(dis);
                kcal.setText(KCAL);
                LSPEED.setText(LSP);
                TIME.setText(TM);
            }
        });
    }
    @Override
    public void onLocationChanged(Location location) {
        // 위치 변경이 두번째로 변경된 경우 계산에 의해 속도 계산
        lasttime = System.currentTimeMillis(); //시간 시작.
        double a = (lasttime - firsttime) / 1000;
        if(mLastlocation != null) {
            //시간 간격
            lat = location.getLatitude();
            loc = location.getLongitude();
            vcc = (location.distanceTo(mLastlocation) / 1000);//m단위 이니라 그래서 1000으로 나눴으니까 km 단위다.
            V = V + vcc;
            speed = vcc * 3600 / a; //km/h
            String sp = String.format("%.1f", speed);
            // 속도 계산
            Speed.setText(sp + "km/h");  //Last Time
        }
        // 현재위치를 지난 위치로 변경
        firsttime = lasttime; //제한.
        mLastlocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        //권한 체크
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // 위치정보 업데이트
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100,0, this);
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        //권한 체크
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // 위치정보 업데이트
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100,0, this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        // 위치정보 가져오기 제거
        locationManager.removeUpdates(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //권한이 없을 경우 최초 권한 요청 또는 사용자에 의한 재요청 확인
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // 권한 재요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            }
        }
    }
    public String Kalori(double MET, double time, double kg){
        double kcal;
        kcal = 5 * MET * (3.5 * kg * time) * 0.001;

        String kcal2 = String.format("%.1f", kcal);

        return kcal2;
    }
}
