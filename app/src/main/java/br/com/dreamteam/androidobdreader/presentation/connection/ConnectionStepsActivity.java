package br.com.dreamteam.androidobdreader.presentation.connection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.stepstone.stepper.StepperLayout;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.ObdReaderApplication;
import br.com.dreamteam.androidobdreader.R;
import br.com.dreamteam.androidobdreader.presentation.connection.fragments.StepControlListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ConnectionStepsActivity extends AppCompatActivity implements
        StepControlListener,
        ConnectionStepsContract.View {

    @BindView(R.id.stepperLayout)
    StepperLayout stepperLayout;

    MyStepperAdapter adapter;

    @Inject
    ConnectionStepsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        ButterKnife.bind(this);
        ((ObdReaderApplication) getApplication()).component().inject(this);

        adapter = new MyStepperAdapter(getSupportFragmentManager(), this);

        stepperLayout.setOffscreenPageLimit(adapter.getCount());
        stepperLayout.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onViewResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onViewPause(this);
    }

    @Override
    public void onProceed() {
        stepperLayout.proceed();
    }

    @Override
    public void onReturn() {
        returnStep();
    }

    @Override
    public void onCompleted() {
        finish();
    }

    private void returnStep() {
        int currentStepPosition = stepperLayout.getCurrentStepPosition();
        if (currentStepPosition > 0) {
            stepperLayout.onBackClicked();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        returnStep();
    }
}
