package br.com.dreamteam.androidobdreader.presentation.connection.fragments.connecting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.ObdReaderApplication;
import br.com.dreamteam.androidobdreader.R;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.BaseStepFragment;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.StepControlListener;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ConnectingFragment extends BaseStepFragment implements ConnectingContract.View {


    @BindView(R.id.step_title)
    TextView stepTitle;

    @BindView(R.id.step_subtitle)
    TextView stepSubtitle;

    @BindView(R.id.step_result_icon)
    ImageView stepResultIcon;

    @BindView(R.id.step_connecting_progress)
    ProgressBar connectionProgress;
    @Inject
    ConnectingContract.Presenter presenter;
    private boolean isConnected = false;
    private StepControlListener stepControlListener;


    @Inject
    public ConnectingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_connecting, container, false);
        ButterKnife.bind(this, rootView);
        ((ObdReaderApplication) getActivity().getApplication()).component().inject(this);
        stepResultIcon.setVisibility(View.GONE);
        connectionProgress.setVisibility(View.VISIBLE);
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
        if (!isConnected) {
            return new VerificationError(getString(R.string.connection_step_connecting_complete_error_message));
        }
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
        isConnected = false;
        stepTitle.setText(getString(R.string.connection_step_connecting_title));
        stepSubtitle.setText(getString(R.string.connection_step_connecting_explanation));
        stepResultIcon.setVisibility(View.GONE);
        connectionProgress.setVisibility(View.VISIBLE);
    }


    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        if (isConnected) {
            stepControlListener.onCompleted();
        }
    }

    @Override
    public void showConnectionSuccess() {
        stepTitle.setText(getString(R.string.connection_step_connecting_title_success));
        stepSubtitle.setText(getString(R.string.connection_step_connecting_explanation_success));
        stepResultIcon.setImageDrawable(getContext().getDrawable(R.drawable.ic_check_circle_24dp));
        stepResultIcon.setVisibility(View.VISIBLE);
        connectionProgress.setVisibility(View.GONE);
        isConnected = true;
    }

    @Override
    public void showConnectionFailed() {
        stepTitle.setText(getString(R.string.connection_step_connecting_title_failed));
        stepSubtitle.setText(getString(R.string.connection_step_connecting_explanation_failed));
        stepResultIcon.setImageDrawable(getContext().getDrawable(R.drawable.ic_error_24dp));
        stepResultIcon.setVisibility(View.VISIBLE);
        connectionProgress.setVisibility(View.GONE);
    }
}
