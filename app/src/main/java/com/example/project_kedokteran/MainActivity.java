package com.example.project_kedokteran;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText inputUmur, inputBeratBadan, inputSerumCreatinine;
    private RadioButton radioButtonPria, radioButtonWanita;
    private Button buttonHitung, buttonReset;
    private TextView hasilCrCl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputUmur = findViewById(R.id.input_umur);
        inputBeratBadan = findViewById(R.id.input_berat_badan);
        inputSerumCreatinine = findViewById(R.id.input_serum_creatinine);
        radioButtonPria = findViewById(R.id.radio_button_pria);
        radioButtonWanita = findViewById(R.id.radio_button_wanita);
        buttonHitung = findViewById(R.id.button_hitung);
        hasilCrCl = findViewById(R.id.hasil_crcl);
        // Deklarasikan tombol reset
        buttonReset = findViewById(R.id.button_reset);

        buttonHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitungCrCl();
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menghapus nilai-nilai inputan
                inputUmur.getText().clear();
                inputBeratBadan.getText().clear();
                inputSerumCreatinine.getText().clear();

                // Mengosongkan TextView hasilCrCl
                hasilCrCl.setText("");

                // Membersihkan pemilihan radio button
                radioButtonPria.setChecked(false);
                radioButtonWanita.setChecked(false);
            }
        });
    }



    private void hitungCrCl() {
        try {
            int umur = Integer.parseInt(inputUmur.getText().toString());
            double beratBadan = Double.parseDouble(inputBeratBadan.getText().toString());
            double serumCreatinine = Double.parseDouble(inputSerumCreatinine.getText().toString());

            double crcl;

            if (radioButtonPria.isChecked()) {
                crcl = ((140 - umur) * beratBadan) / (serumCreatinine * 72);
            } else {
                crcl = ((140 - umur) * beratBadan) / (serumCreatinine * 72) * 0.85; // Faktor koreksi untuk wanita
            }

            String status = "";

            if (crcl > 90) {
                status = "Stage 1 | Normal";
            } else if (crcl >= 60 && crcl <= 89) {
                status = "Stage 2 | Ringan";
            } else if (crcl >= 45 && crcl <= 59) {
                status = "Stage 3a | Ringan - Sedang";
            } else if (crcl >= 30 && crcl <= 44) {
                status = "Stage 3b | Sedang - Berat";
            } else if (crcl >= 15 && crcl <= 29) {
                status = "Stage 4 | Berat";
            } else if (crcl < 15) {
                status = "Stage 5 | Gagal Ginjal";
            }

            hasilCrCl.setText("CrCl: " + String.format("%.2f", crcl) + " mL/min/1.73 m2\n Status: " + status);
        } catch (NumberFormatException e) {
            hasilCrCl.setText("Mohon masukkan semua data dengan benar.");
        }
    }
}


