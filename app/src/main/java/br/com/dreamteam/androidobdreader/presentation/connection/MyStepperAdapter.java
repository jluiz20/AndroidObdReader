package br.com.dreamteam.androidobdreader.presentation.connection;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import br.com.dreamteam.androidobdreader.R;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.BaseStepFragment;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.connecting.ConnectingFragment;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.connectobd.ConnectObdFragment;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.discovery.DiscoveryFragment;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.turnvehicleon.TurnVehicleOnFragment;

/**
 * Handles step coordination
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class MyStepperAdapter extends AbstractFragmentStepAdapter {

    private static final String CURRENT_STEP_POSITION_KEY = "CURRENT_STEP_POSITION_KEY";

    public MyStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        final BaseStepFragment step;
        switch (position) {
            case 0:
                step = new ConnectObdFragment();
                break;
            case 1:
                step = new TurnVehicleOnFragment();
                break;
            case 2:
                step = new DiscoveryFragment();
                break;
            case 3:
                step = new ConnectingFragment();
                break;

            default:
                throw new UnsupportedOperationException("Invalid Step:" + position);
        }

        Bundle b = new Bundle();
        b.putInt(CURRENT_STEP_POSITION_KEY, position);
        step.setArguments(b);
        return step;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        String title;
        String subTitle;
        switch (position) {
            case 0:
                title = context.getString(R.string.connection_step_connect_obd_title);
                subTitle = "";
                break;
            case 1:
                title = context.getString(R.string.connection_step_turn_on_vehicle_title);
                subTitle = "";
                break;
            case 2:
                title = context.getString(R.string.connection_step_peer_list_title);
                subTitle = "";
                break;
            case 3:
                title = context.getString(R.string.connection_step_connecting_title);
                subTitle = "";
                break;
            default:
                throw new UnsupportedOperationException("Step title invalid id: " + position);
        }
        return new StepViewModel.Builder(context)
                .setTitle(title)
                .setSubtitle(subTitle)
                .create();
    }
}