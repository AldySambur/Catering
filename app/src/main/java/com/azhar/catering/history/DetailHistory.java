package com.azhar.catering.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.azhar.catering.R;

public class DetailHistory extends AppCompatActivity {

    private TextView tvNama, tvDate, tvJumlah, tvPrice;
    private Button btnCancel, btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_order); // Pastikan nama layout sesuai

        // Inisialisasi TextView
        tvNama = findViewById(R.id.tvOrderName);
        tvDate = findViewById(R.id.tvOrderDate);
        tvJumlah = findViewById(R.id.tvOrderItems);
        tvPrice = findViewById(R.id.tvTotalPrice);

        // Inisialisasi Button
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnConfirm);

        // Ambil data dari intent
        Intent intent = getIntent();
        String nama = intent.getStringExtra("NAMA");
        String date = intent.getStringExtra("DATE");
        int jumlah = intent.getIntExtra("JUMLAH", 0);
        String price = intent.getStringExtra("PRICE");

        // Tampilkan data di TextView
        tvNama.setText(nama);
        tvDate.setText(date);
        tvJumlah.setText(String.format("Jumlah Item: %d", jumlah));
        tvPrice.setText(String.format("Total Harga: Rp %s", price));

        // Fungsi untuk tombol "Batalkan Pesanan"
        btnCancel.setOnClickListener(v -> {
            // Logika batalkan pesanan
            // Misalnya kembali ke halaman sebelumnya
            setResult(RESULT_CANCELED);
            finish(); // Tutup activity
        });

        // Fungsi untuk tombol "Konfirmasi Pesanan"
        btnConfirm.setOnClickListener(v -> {
            // Logika konfirmasi pesanan
            // Misalnya kirim kembali data yang diperbarui
            Intent resultIntent = new Intent();
            resultIntent.putExtra("STATUS", "CONFIRMED");
            setResult(RESULT_OK, resultIntent);
            finish(); // Tutup activity
        });
    }
}
