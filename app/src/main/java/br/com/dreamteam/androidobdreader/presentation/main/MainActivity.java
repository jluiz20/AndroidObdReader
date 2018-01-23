package br.com.dreamteam.androidobdreader.presentation.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import javax.inject.Inject;

import br.com.dreamteam.androidobdreader.ObdReaderApplication;
import br.com.dreamteam.androidobdreader.R;
import br.com.dreamteam.androidobdreader.presentation.connection.ConnectionStepsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
public class MainActivity extends AppCompatActivity implements MainContract.View {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 742;
    private static final String TAG = "MainActivity";
    @Inject
    MainContract.Presenter presenter;
    @BindView(R.id.app_version)
    TextView appVersion;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.button_connect)
    Button connectButton;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                Toast.makeText(this, getString(R.string.title_home), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navigation_dashboard:
                Toast.makeText(this, getString(R.string.title_dashboard), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navigation_notifications:
                Toast.makeText(this, getString(R.string.title_notifications), Toast.LENGTH_SHORT).show();
                return true;
            default:
                throw new UnsupportedOperationException("Invalid option for Navigation bar: " + item.getItemId());
        }
    };

    @OnClick(R.id.button_connect)
    void onConnectClick() {
        if (hasPermission()) {
            startConnectionActivity();
        } else {
            requestPermission();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((ObdReaderApplication) getApplication()).component().inject(this);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setVisibility(View.GONE);

        hockeyAppCheckForUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onViewResume(this);
        hockeyAppCheckForCrashes();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onViewPause(this);
        hockeyAppUnregisterManagers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hockeyAppUnregisterManagers();
    }

    private boolean hasPermission() {
        return
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startConnectionActivity();
            } else {

                Log.d(TAG, "onRequestPermissionsResult: Permission Denied!");
                // 1. Instantiate an AlertDialog.Builder with its constructor
                showAlertPermissionRequired();
            }
            return;
        }
    }

    private void showAlertPermissionRequired() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(R.string.alert_location_permission_required_title)
                .setMessage(R.string.alert_location_permission_required_description)
                .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> openAppSettings());
        builder.create().show();
    }

    private void showAlertBluetoothNotSupported() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(R.string.alert_bluetooth_not_supported_title)
                .setMessage(R.string.alert_bluetooth_not_supported_title_description)
                .setNeutralButton(android.R.string.ok, null);
        builder.create().show();
    }

    private void openAppSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void startConnectionActivity() {
        Intent intent = new Intent(this, ConnectionStepsActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAppVersion(String appVersion) {
        this.appVersion.setText(appVersion);
    }

    @Override
    public void showBluetoothIsNotSupported() {
        connectButton.setEnabled(false);
        showAlertBluetoothNotSupported();
    }

    private void hockeyAppCheckForCrashes() {
        CrashManager.register(this);
    }

    private void hockeyAppCheckForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void hockeyAppUnregisterManagers() {
        UpdateManager.unregister();
    }
}
