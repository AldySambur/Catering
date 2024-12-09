package com.azhar.catering.history;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azhar.catering.R;
import com.azhar.catering.database.DatabaseModel;
import com.azhar.catering.order.OrderActivity;
import com.azhar.catering.utils.FunctionHelper;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    List<DatabaseModel> modelDatabase;
    Context mContext;

    public HistoryAdapter(Context context, List<DatabaseModel> modelInputList) {
        this.mContext = context;
        this.modelDatabase = modelInputList;
    }

    //untuk set data ke menu view
    public void setDataAdapter(List<DatabaseModel> items) {
        modelDatabase.clear();
        modelDatabase.addAll(items);
        notifyDataSetChanged();
    }

    //untuk inisialisasi layout
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_riwayat, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DatabaseModel model = modelDatabase.get(position);

        // Set data ke view
        holder.tvNama.setText(model.getNama_menu());
        holder.tvDate.setText(model.getUid());
        holder.tvJml.setText(String.valueOf(model.getUid()));
        holder.tvPrice.setText(model.getUid());

        // Tambahkan click listener ke button
        holder.btnPesanLagi.setOnClickListener(v -> {
            // Intent untuk pindah ke DetailHistory
            Intent intent = new Intent(mContext, DetailHistory.class);

            // Kirim data ke DetailHistory
            intent.putExtra("NAMA", model.getNama_menu());
            intent.putExtra("DATE", model.getUid());
            intent.putExtra("JUMLAH", model.getUid());
            intent.putExtra("PRICE", model.getUid());

            // Start activity
            mContext.startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return modelDatabase.size();
    }

    //untuk inisialisasi id di Adapter
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNama, tvDate, tvJml, tvPrice;
        public Button btnPesanLagi; // Tambahkan tombol Pesan Lagi

        public ViewHolder(View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvJml = itemView.findViewById(R.id.tvJml);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnPesanLagi = itemView.findViewById(R.id.btnPesan); // Inisialisasi tombol Pesan Lagi
        }
    }

    //untuk function swipe hapus data
    public void setSwipeRemove(int position) {
        modelDatabase.remove(position);
        notifyItemRemoved(position);
    }

    //untuk restore data jika cancel delete
    public void restoreItem(DatabaseModel databaseModel, int position) {
        modelDatabase.add(position, databaseModel);
        notifyItemInserted(position);
    }

    public List<DatabaseModel> getData() {
        return modelDatabase;
    }

}
