package br.com.dreamteam.androidobdreader.presentation.connection.fragments.connectobd;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.ObdReaderApplication;
import br.com.dreamteam.androidobdreader.R;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.BaseStepFragment;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.StepControlListener;
import butterknife.ButterKnife;


public class ConnectObdFragment extends BaseStepFragment implements ConnectObdContract.View {


    @Inject
    ConnectObdContract.Presenter presenter;

    private StepControlListener stepControlListener;

    @Inject
    public ConnectObdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_connect_obd, container, false);
        ButterKnife.bind(this, rootView);
        ((ObdReaderApplication) getActivity().getApplication()).component().inject(this);

        return rootView;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StepControlListener) {
            stepControlListener = (StepControlListener) context;
        }
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        callback.goToNextStep();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        stepControlListener.onReturn();
    }

    @Override
    public void onSelected() {
        //do noting
    }


    @Override
    public void onError(@NonNull VerificationError error) {
        // do nothing
    }
}
