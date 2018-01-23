package br.com.dreamteam.androidobdreader.presentation.connection.fragments.turnvehicleon;

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
import butterknife.ButterKnife;


public class TurnVehicleOnFragment extends BaseStepFragment implements TurnVehicleOnContract.View {


    @Inject
    TurnVehicleOnContract.Presenter presenter;

    @Inject
    public TurnVehicleOnFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_turn_vehicle_on, container, false);
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
        callback.goToPrevStep();
    }

    @Override
    public void onSelected() {
        //do nothing
    }


    @Override
    public void onError(@NonNull VerificationError error) {
        //do nothing
    }
}
