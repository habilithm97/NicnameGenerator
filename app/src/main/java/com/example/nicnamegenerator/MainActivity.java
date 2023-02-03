package com.example.nicnamegenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
    int count = 0;

    final String SP = "sharedPreference";
    final String LIST = "nicname list";

    /*
    Gson gson = new GsonBuilder().create();
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    List<Nicname> list;
     */

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
                    //Toast.makeText(getApplicationContext(), "새 닉네임을 생성하세요. ", Toast.LENGTH_SHORT).show();
                   showToast(getApplicationContext(), "새 닉네임을 생성하세요. ");
                } else {
                    saveNicname();
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1); // recyclerView 마지막 아이템 위치로 포커스 이동
                }
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new NicnameAdapter(nicnameList);
        //adapter = new NicnameAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL)); // 구분선 설정
        recyclerView.setAdapter(adapter);

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
        count++;
        String str = resultTv.getText().toString();
        adapter.addItem(new Nicname(count, str, isSelected));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveData();
    }

    public void saveData() {
        SharedPreferences sp = getSharedPreferences(SP, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(nicnameList);
        editor.putString(LIST, json);
        editor.apply();

        /*
        SharedPreferences sp = getSharedPreferences("sp", 0);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        editor.remove("list").apply(); // 기존에 있던 정보를 지움(초기화)
        editor.putString("list", gson.toJson(nicnameList)).apply(); // ArrayList를 SP에 저장함 */
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadData();
    }

    public void loadData() {
        SharedPreferences sp = getSharedPreferences(SP, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString(LIST, null);
        // json으로 SP에 저장하는 순간 데이터가 가지고 있는 타입 정보가 모두 사라지기 때문에
        // 데이터를 복원하면서 타입 정보를 다시 부여해야 하기 위해 타입 토큰을 사용함
        Type type = new TypeToken<List<Nicname>>() {}.getType();
        nicnameList = gson.fromJson(json, type);

        if(nicnameList == null) {
            nicnameList = new ArrayList<>();
        }

        /*
        SharedPreferences sp = getSharedPreferences("sp", 0);
        Gson gson = new Gson();
        String value = sp.getString("list", null);
        if(value != null) {
            // SP에서 데이터를 가져와서 ArrayList로 변환하기
            list = gson.fromJson(value, new TypeToken<List<Nicname>>() {
            }.getType());
            adapter = new NicnameAdapter(list);
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "list is null", Toast.LENGTH_SHORT).show();
        } */
    }
}