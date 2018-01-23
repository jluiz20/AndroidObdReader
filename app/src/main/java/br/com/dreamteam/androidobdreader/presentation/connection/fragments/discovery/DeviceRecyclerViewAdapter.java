package br.com.dreamteam.androidobdreader.presentation.connection.fragments.discovery;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.dreamteam.androidobdreader.R;
import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BluetoothDevice} and makes a call to the
 * specified {@link OnDeviceSelected}.
 */
public class DeviceRecyclerViewAdapter extends RecyclerView.Adapter<DeviceRecyclerViewAdapter.DeviceViewHolder> {

    private final List<BluetoothDeviceWrapper> devices;
    private final OnDeviceSelected mListener;

    public DeviceRecyclerViewAdapter(List<BluetoothDeviceWrapper> items, OnDeviceSelected listener) {
        devices = items;
        mListener = listener;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_peerlist, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeviceViewHolder holder, int position) {
        holder.mItem = devices.get(position);
        holder.nameView.setText(devices.get(position).getName());
        holder.macAddressView.setText(devices.get(position).getAddress());

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onDeviceSelected(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        @BindView(R.id.name)
        TextView nameView;
        @BindView(R.id.mac_address)
        TextView macAddressView;
        private BluetoothDeviceWrapper mItem;

        public DeviceViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + macAddressView.getText() + "'";
        }
    }
}
