package br.com.dreamteam.androidobdreader.presentation.connection.fragments.discovery;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.ObdReaderApplication;
import br.com.dreamteam.androidobdreader.R;
import br.com.dreamteam.androidobdreader.model.BluetoothDeviceWrapper;
import br.com.dreamteam.androidobdreader.model.datasource.obd.states.ConnectionState;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.BaseStepFragment;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.StepControlListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DiscoveryFragment extends BaseStepFragment implements DiscoveryContract.View {

    private static final String TAG = "DiscoveryFragment";

    @BindView(R.id.step_progress)
    ProgressBar vProgressBar;

    @BindView(R.id.step_btn_retry)
    Button vRetryButton;

    @Inject
    DiscoveryContract.Presenter presenter;
    @BindView(R.id.recycler_paired_devices)
    RecyclerView recyclerViewPairedDevices;
    @BindView(R.id.recycler_discovered)
    RecyclerView recyclerViewDiscoveredDevices;
    @Nullable
    private StepControlListener stepControlListener;
    private boolean isDeviceSelected;
    private OnDeviceSelected mListener = new OnDeviceSelected() {
        @Override
        public void onDeviceSelected(BluetoothDeviceWrapper item) {
            presenter.onUserSelectedDeviceToConnect(item);
        }
    };

    @Inject
    public DiscoveryFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.step_btn_retry)
    void onRetryClick() {
        presenter.onUserStartConnection();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_discovery, container, false);
        ButterKnife.bind(this, rootView);
        ((ObdReaderApplication) getActivity().getApplication()).component().inject(this);

        vRetryButton.setVisibility(View.GONE);

        return rootView;
    }

    private void setPairedDevicesAdapter(Set<BluetoothDeviceWrapper> devices) {
        List<BluetoothDeviceWrapper> list = new ArrayList<>(devices);
        recyclerViewPairedDevices.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPairedDevices.setAdapter(new DeviceRecyclerViewAdapter(list, mListener));
    }

    private void setDiscoveredDevicesAdapter(Set<BluetoothDeviceWrapper> devices) {
        List<BluetoothDeviceWrapper> list = new ArrayList<>(devices);
        recyclerViewDiscoveredDevices.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewDiscoveredDevices.setAdapter(new DeviceRecyclerViewAdapter(list, mListener));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onViewPause(this);
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        if (!isDeviceSelected) {
            return new VerificationError(getString(R.string.connection_step_select_device));
        }
        return null;
    }

    @Override
    public void onSelected() {
        presenter.onUserStartConnection();
        isDeviceSelected = false;
    }


    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Override
    public void showDiscoveredDevices(Set<BluetoothDeviceWrapper> data) {
        setDiscoveredDevicesAdapter(data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StepControlListener) {
            stepControlListener = (StepControlListener) context;
        }
    }

    @Override
    public void updateConnectionState(ConnectionState connectionState) {
        switch (connectionState) {
            case DISCOVERY_STARTED:
                vRetryButton.setVisibility(View.GONE);
                vProgressBar.setVisibility(View.VISIBLE);
                break;
            case DISCOVERY_FINISHED:
            case UNKNOWN:
                vRetryButton.setVisibility(View.VISIBLE);
                vProgressBar.setVisibility(View.GONE);
                break;
            default:
                Log.d(TAG, "updateConnectionState: do nothing");
                break;

        }
    }

    @Override
    public void showPairedDevices(Set<BluetoothDeviceWrapper> data) {
        setPairedDevicesAdapter(data);
    }

    @Override
    public void showConnectionInProgress() {
        isDeviceSelected = true;
        stepControlListener.onProceed();
    }
}
