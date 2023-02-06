package com.example.nicnamegenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kimkevin.hangulparser.HangulParser;
import com.github.kimkevin.hangulparser.HangulParserException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final static String[] CHO = {"ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"}; // 19개
    final static String[] JOONG = {"ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ", "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅘ", "ㅙ", "ㅚ", "ㅛ", "ㅜ", "ㅝ", "ㅞ", "ㅟ", "ㅠ", "ㅡ", "ㅢ", "ㅣ"}; // 21개
    final static String[] JONG = {"", "ㄱ", "", "ㄲ", "", "ㄳ", "", "ㄴ", "", "ㄵ", "", "ㄶ", "", "ㄷ", "", "ㄹ", "", "ㄺ", "", "ㄻ", "", "ㄼ", "", "ㄽ",
            "", "ㄾ", "", "ㄿ", "", "ㅀ", "", "ㅁ", "", "ㅂ", "", "ㅄ", "", "ㅅ", "", "ㅆ", "", "ㅇ", "", "ㅈ", "", "ㅊ", "", "ㅋ", "", "ㅌ", "", "ㅍ", "", "ㅎ"}; // 54개(절반은 공백)

    Random choRandom = new Random();
    Random joongRandom = new Random();
    Random jongRandom = new Random();

    Random choRandom1 = new Random();
    Random joongRandom1 = new Random();
    Random jongRandom1 = new Random();

    List<Nicname> nicnameList;
    NicnameAdapter adapter;
    TextView resultTv;
    RecyclerView recyclerView;

    boolean isSelected = false;

    static Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nicnameList = new ArrayList<>();

        initView();

        loadData();
    }

    public void initView() {
        Button saveBtn = (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resultTv.getText().toString().equals("닉네임")) {
                   showToast(getApplicationContext(), "새 닉네임을 생성하세요. ");
                } else {
                    saveNicname();
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1); // recyclerView 마지막 아이템 위치로 포커스 이동
                }
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new NicnameAdapter(nicnameList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL)); // 구분선 설정
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView); // ItemTouchHelper가 RecyclerView와 ItemTouchHelper.Callback을 구조적으로 연결함

        resultTv = (TextView)findViewById(R.id.resultTv);
        resultTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    generateNicname();
                }
                return true;
            }
        });
    }

    public static void showToast(Context context, String msg) {
        if(toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else { // 기존에 토스트 객체가 있다면 추가 생성하지 않음
            toast.setText(msg);
        }
        toast.show();
    }

    public void generateNicname() {
        resultTv.setText(null);
        assembleRandomJamo();
    }

    public void assembleRandomJamo() {
        try {
            List<String> jamoList = HangulParser.disassemble("분리");
            jamoList.clear();

            String cho = CHO[choRandom.nextInt(19)];
            String joong = JOONG[joongRandom.nextInt(21)];
            String jong = JONG[jongRandom.nextInt(54)];

            String cho1 = CHO[choRandom1.nextInt(19)];
            String joong1 = JOONG[joongRandom1.nextInt(21)];
            String jong1 = JONG[jongRandom1.nextInt(54)];

            jamoList.add(cho);
            jamoList.add(joong);
            jamoList.add(jong);

            jamoList.add(cho1);
            jamoList.add(joong1);
            jamoList.add(jong1);

            String hangul = HangulParser.assemble(jamoList);
            resultTv.setText(hangul);

        } catch (HangulParserException e) {
            e.printStackTrace();
        }
    }

    public void saveNicname() {
        String str = resultTv.getText().toString();
        adapter.addItem(new Nicname(str, isSelected));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveData();
    }

    public void saveData() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadData();
    }

    public void loadData() {

    }

    // ItemTouchHelper : RecyclerView에서 삭제를 위한 스와이프 및 드래그 앤 드롭을 지원하는 유틸리티 클래스
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            nicnameList.remove(position);
            adapter.notifyItemRemoved(position);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAll:
                DeleteAll();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void DeleteAll() {
        nicnameList.clear();
        adapter.notifyDataSetChanged();
    }
}