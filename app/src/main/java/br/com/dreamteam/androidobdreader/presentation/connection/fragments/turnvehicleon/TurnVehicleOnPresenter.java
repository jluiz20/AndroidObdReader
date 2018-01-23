package br.com.dreamteam.androidobdreader.presentation.connection.fragments.turnvehicleon;


import android.util.Log;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.presentation.BasePresenter;

/**
 * Example of presenter iImplementation fully unit tested.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
public class TurnVehicleOnPresenter extends BasePresenter implements TurnVehicleOnContract.Presenter {

    private static final String TAG = "TurnVehicleOnPresenter";


    private TurnVehicleOnContract.View view;

    @Inject
    public TurnVehicleOnPresenter() {
        //Empty Constructor for DI
    }

    @Override
    public void onViewResume(TurnVehicleOnContract.View view) {
        Log.d(TAG, "onViewResume: ");
        attachView(view);
    }


    private void attachView(TurnVehicleOnContract.View view) {
        Log.d(TAG, "attachView: " + view.toString());
        this.view = view;
    }

    @Override
    public void onViewPause(TurnVehicleOnContract.View view) {
        Log.d(TAG, "onViewPaused: ");
        detachView(view);
    }


    private void detachView(TurnVehicleOnContract.View view) {
        Log.d(TAG, "detachView: " + view.toString());
        this.view = null;
    }

    TurnVehicleOnContract.View getView() {
        return view;
    }
}
