package com.example.datepickerdialog2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView dateText = findViewById(R.id.editTest);
        Button dateButton = findViewById(R.id.dateButton);


        dateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                final Dialog dialog = ProgressDialog.show(MainActivity.this, "讀取中", "請稍後...",true);

                //創建一個多執行緒執行ProgressDialog
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(1500);  //設定等待秒數(毫秒)
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                        finally{
                            dialog.dismiss();
                        }
                    }
                }).start();

                //監聽Progress dismiss 後執行DatePickerDialog
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        //DatePickerDialog 是一個 Android 寫好的類別,
                        //它可以提供使用者簡單操作的設定日期介面,
                        //呼叫它的方式就是直接 new DatePickerDialog 並且傳入對應的參數

                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                String format = String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day);
                                dateText.setText(format);
                            }

                        }, mYear,mMonth, mDay).show();
                    }
                });
            }
        });
    }

}

