package com.bignerdranch.android.twohealtest;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private ListView listView;
    private SimpleAdapter adapter;
    private DBUtil dbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn_all);
        btn2 = (Button) findViewById(R.id.btn_add);
        btn3 = (Button) findViewById(R.id.btn_delete);
        listView = (ListView) findViewById(R.id.listView);
        dbUtil = new DBUtil();

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setListView();
                hideButton(true);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setAddDialog();
                hideButton(true);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setDeleteDialog();
                hideButton(true);
            }
        });
    }

    private void setDeleteDialog() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_delete);
        dialog.setTitle("是否确定取消发布？");
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);

        final EditText cNoEditText = (EditText) dialog.findViewById(R.id.editText1);
        Button btnConfirm = (Button) dialog.findViewById(R.id.button1);
        Button btnCancel = (Button) dialog.findViewById(R.id.button2);

        btnConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dbUtil.deleteCompetitonInfo(cNoEditText.getText().toString());
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "成功取消发布", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "在耐心等等吧...", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
    private void setAddDialog() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_add);
        dialog.setTitle("输入添加的竞赛信息");
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);


        final EditText cTitleEditText = (EditText) dialog.findViewById(R.id.editText1);
        final EditText cStatusEditText = (EditText) dialog.findViewById(R.id.editText2);
        final EditText cHostEditText = (EditText) dialog.findViewById(R.id.editText3);
        final EditText cLevelEditText = (EditText) dialog.findViewById(R.id.editText4);
        final EditText cTimeEditText = (EditText) dialog.findViewById(R.id.editText5);
        final EditText cTimingEditText = (EditText) dialog.findViewById(R.id.editText6);
        Button btnConfirm = (Button) dialog.findViewById(R.id.button1);
        Button btnCancel = (Button) dialog.findViewById(R.id.button2);

        btnConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dbUtil.insertCompetitionInfo(cTitleEditText.getText().toString(), cStatusEditText.getText().toString(), cHostEditText.getText().toString(), cLevelEditText.getText().toString(), cTimeEditText.getText().toString(), cTimingEditText.getText().toString());
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "成功添加数据", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void setListView() {

        listView.setVisibility(View.VISIBLE);

        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        list = dbUtil.getAllInfo();

        adapter = new SimpleAdapter(
                MainActivity.this,
                list,
                R.layout.list_item_competition,
                new String[] { "Cno", "CTitle", "CStatus", "CHost", "CLevel", "CTime", "CTimng" },
                new int[] { R.id.sdas, R.id.competiton_title, R.id.competiton_sttus, R.id.competition_campus, R.id.competition_leveltwo, R.id.competiton_timetwo, R.id.competiton_timingtwo });

        listView.setAdapter(adapter);

    }
    /**
     * 设置button的可见性
     */
    private void hideButton(boolean result) {
        if (result) {
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
        } else {
            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
        }

    }

}
