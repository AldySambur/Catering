package com.azhar.catering.history;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.catering.R;
import com.azhar.catering.database.DatabaseModel;
import com.azhar.catering.main.MainActivity;
import com.azhar.catering.order.OrderActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class HistoryOrderActivity extends AppCompatActivity {

    List<DatabaseModel> modelDatabaseList = new ArrayList<>();
    HistoryAdapter historyAdapter;
    HistoryViewModel historyViewModel;
    Toolbar toolbar;
    RecyclerView rvHistory;
    TextView tvNotFound;
    MaterialButton btnPesanLagi;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);

        setToolbar();
        setInitLayout();
        setViewModel();
        setSwipeToDelete();
    }



    // Toolbar setup
    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    // Initialize layout elements and adapter
    private void setInitLayout() {
        rvHistory = findViewById(R.id.rvHistory);
        tvNotFound = findViewById(R.id.tvNotFound);

        tvNotFound.setVisibility(View.GONE);

        historyAdapter = new HistoryAdapter(this, modelDatabaseList);
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(historyAdapter);
    }


    // Get data from ViewModel and handle empty list case
    private void setViewModel() {
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        historyViewModel.getDataList().observe(this, modelDatabases -> {
            if (modelDatabases.size() != 0) {
                historyAdapter.setDataAdapter(modelDatabases);
            } else {
                tvNotFound.setVisibility(View.VISIBLE);
                rvHistory.setVisibility(View.GONE);
            }
        });
    }

    // Implement swipe-to-delete with confirmation
    private void setSwipeToDelete() {
        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START | ItemTouchHelper.END) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        // Get the position of the swiped item
                        final int position = viewHolder.getAdapterPosition();
                        final DatabaseModel databaseModel = historyAdapter.getData().get(position);

                        // Create and display the confirmation dialog
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                HistoryOrderActivity.this);
                        alertDialogBuilder.setMessage("Hapus riwayat ini?");
                        alertDialogBuilder.setPositiveButton("Ya, Hapus", (dialogInterface, i) -> {
                            // Remove item from ViewModel and adapter
                            int uid = databaseModel.uid;
                            historyViewModel.deleteDataById(uid);
                            historyAdapter.setSwipeRemove(position);
                            Toast.makeText(HistoryOrderActivity.this,
                                    "Data yang dipilih sudah dihapus", Toast.LENGTH_SHORT).show();
                        });

                        alertDialogBuilder.setNegativeButton("Batal", (dialogInterface, i) -> {
                            // Restore the item if deletion is canceled
                            historyAdapter.restoreItem(databaseModel, position);
                            rvHistory.scrollToPosition(position);
                        });

                        // Show the dialog
                        alertDialogBuilder.show();
                    }
                };

        // Attach the ItemTouchHelper to RecyclerView
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(rvHistory);
    }
}
