package com.example.kostka;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLiczbakostek;
    private Button buttonLosuj;
    private GridLayout gridKostki;
    private TextView textViewWyniki;
    private Button buttonRosnaco;
    private Button buttonMalejaco;

    private ArrayList<Integer> wyniki = new ArrayList<>();
    private boolean wylosowano = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLiczbakostek = findViewById(R.id.editTextLiczbakostek);
        buttonLosuj = findViewById(R.id.buttonLosuj);
        gridKostki = findViewById(R.id.gridKostki);
        textViewWyniki = findViewById(R.id.textViewWyniki);
        buttonRosnaco = findViewById(R.id.buttonRosnaco);
        buttonMalejaco = findViewById(R.id.buttonMalejaco);

        buttonLosuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                losuj();
            }
        });

        buttonRosnaco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wylosowano) {
                    textViewWyniki.setText("Najpierw wylosuj kostki!");
                    return;
                }
                sortujRosnaco();
                pokazWyniki();
            }
        });

        buttonMalejaco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wylosowano) {
                    textViewWyniki.setText("Najpierw wylosuj kostki!");
                    return;
                }
                sortujMalejaco();
                pokazWyniki();
            }
        });
    }

    private void losuj() {
        String s = editTextLiczbakostek.getText().toString().trim();
        if (s.isEmpty()) {
            textViewWyniki.setText("Podaj liczbę kostek!");
            return;
        }

        int ile;
        try {
            ile = Integer.parseInt(s);
        } catch (Exception e) {
            textViewWyniki.setText("Błędna wartość!");
            return;
        }

        if (ile <= 0 || ile > 100) {
            textViewWyniki.setText("Podaj liczbę od 1 do 100[cite: 1]!");
            gridKostki.removeAllViews();
            wylosowano = false;
            return;
        }

        wyniki.clear();
        Random r = new Random();
        for (int i = 0; i < ile; i++) {
            wyniki.add(r.nextInt(6) + 1);
        }

        wylosowano = true;
        pokazWyniki();
    }

    private void sortujRosnaco() {
        int n = wyniki.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (wyniki.get(j) > wyniki.get(j + 1)) {
                    int tmp = wyniki.get(j);
                    wyniki.set(j, wyniki.get(j + 1));
                    wyniki.set(j + 1, tmp);
                }
            }
        }
    }

    private void sortujMalejaco() {
        int n = wyniki.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (wyniki.get(j) < wyniki.get(j + 1)) {
                    int tmp = wyniki.get(j);
                    wyniki.set(j, wyniki.get(j + 1));
                    wyniki.set(j + 1, tmp);
                }
            }
        }
    }

    private void pokazWyniki() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wyniki.size(); i++) {
            sb.append(wyniki.get(i));
            if (i < wyniki.size() - 1) {
                sb.append(", ");
            }
        }
        textViewWyniki.setText(sb.toString());

        gridKostki.removeAllViews();
        for (int w : wyniki) {
            TextView kafel = new TextView(this);
            kafel.setText(String.valueOf(w));
            kafel.setTextSize(18);
            kafel.setPadding(20, 20, 20, 20);
            kafel.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            gridKostki.addView(kafel);
        }
    }
}