package com.example.nicnamegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.kimkevin.hangulparser.HangulParser;
import com.github.kimkevin.hangulparser.HangulParserException;

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
                generateNicname();
            }
        });

        resultTv = (TextView)findViewById(R.id.resultTv);
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
}