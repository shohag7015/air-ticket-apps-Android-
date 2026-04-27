package com.example.aireticket.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aireticket.R;
import com.example.aireticket.models.Flight;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private List<Flight> bookingList;
    private Context context;

    public BookingAdapter(List<Flight> bookingList, Context context) {
        this.bookingList = bookingList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flight flight = bookingList.get(position);
        holder.tvAirline.setText(flight.airline);
        holder.tvRoute.setText(flight.from + " to " + flight.to);
        holder.tvDate.setText("Date: " + flight.date);
        holder.tvSeats.setText("Seats: " + flight.selectedSeats);
        holder.tvPrice.setText(flight.price);
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAirline, tvRoute, tvDate, tvSeats, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAirline = itemView.findViewById(R.id.tvAirline);
            tvRoute = itemView.findViewById(R.id.tvRoute);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvSeats = itemView.findViewById(R.id.tvSeats);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
