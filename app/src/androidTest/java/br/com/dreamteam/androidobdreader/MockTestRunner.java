package br.com.dreamteam.androidobdreader;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * This Runner class replaces the regular {@link ObdReaderApplication} class for the
 * {@link MockObdReaderApplication} class, in order to enable mocking on android instrumented tests.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class MockTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, MockObdReaderApplication.class.getName(), context);
    }
}
