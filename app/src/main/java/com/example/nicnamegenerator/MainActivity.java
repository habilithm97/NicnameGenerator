package com.example.nicnamegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final static String[] CHO = {"ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"};
    final static String[] JOONG = {"ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ", "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅘ", "ㅙ", "ㅚ", "ㅛ", "ㅜ", "ㅝ", "ㅞ", "ㅟ", "ㅠ", "ㅡ", "ㅢ", "ㅣ"};
    final static String[] JONG = {"", "ㄱ", "ㄲ", "ㄳ", "ㄴ", "ㄵ", "ㄶ", "ㄷ", "ㄹ", "ㄺ", "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ", "ㅀ", "ㅁ", "ㅂ", "ㅄ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"};

    TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        Button generatorBtn = (Button)findViewById(R.id.generatorBtn);
        generatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultTv.setText(null);
                generateNicname();
            }
        });

        resultTv = (TextView)findViewById(R.id.resultTv);
    }

    public void generateNicname() {
        // 테스트_한글 자모 분리 -> 조합은 반대로
        String str = "하빌리즘";

        for(int i = 0; i < str.length(); i++) {
            char uniVal = str.charAt(i); // 자바는 하나의 유니코드값을 저장하기 위해 2byte 크기인 char 타입을 제공함

            // 한글일 경우에만 시작해야 하기 때문에 0xAC00 부터 아래의 로직을 실행함
            if(uniVal >= 0xAC00) {
                resultTv.append(uniVal + " => ");
                uniVal = (char)(uniVal - 0xAC00); // 현재의 한글 유니코드값에 한글 시작점을 뺌

                char cho = (char)(uniVal / 28 / 21);
                char joong = (char)((uniVal) / 28 % 21);
                char jong = (char)(uniVal % 28);

                resultTv.append(CHO[cho] + JOONG[joong] + JONG[jong] + "\n");
            } else {
                resultTv.append(uniVal + " => " + uniVal);
            }
        }
    }
}