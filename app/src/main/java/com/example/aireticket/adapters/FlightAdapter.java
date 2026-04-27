package com.example.aireticket.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aireticket.R;
import com.example.aireticket.SeatSelectionActivity;
import com.example.aireticket.models.Flight;
import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder> {

    private List<Flight> flightList;
    private Context context;

    public FlightAdapter(List<Flight> flightList, Context context) {
        this.flightList = flightList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_flight, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flight flight = flightList.get(position);
        holder.tvAirline.setText(flight.airline);
        holder.tvFrom.setText(flight.from);
        holder.tvTo.setText(flight.to);
        holder.tvPrice.setText(flight.price);
        holder.tvTime.setText(flight.time);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SeatSelectionActivity.class);
            intent.putExtra("airline", flight.airline);
            intent.putExtra("from", flight.from);
            intent.putExtra("to", flight.to);
            intent.putExtra("price", flight.price);
            intent.putExtra("time", flight.time);
            
            // ইউজারের সার্চ করা তারিখ ও যাত্রী সংখ্যা পাস করা হচ্ছে
            intent.putExtra("date", flight.date);
            intent.putExtra("passengers", flight.passengers);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAirline, tvFrom, tvTo, tvPrice, tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAirline = itemView.findViewById(R.id.tvAirline);
            tvFrom = itemView.findViewById(R.id.tvFrom);
            tvTo = itemView.findViewById(R.id.tvTo);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
