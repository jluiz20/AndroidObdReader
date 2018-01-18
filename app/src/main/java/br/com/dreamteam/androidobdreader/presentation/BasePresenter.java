package br.com.dreamteam.androidobdreader.presentation;

import android.util.Log;

/**
 * This base class wraps common behaviors from the presenters.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class BasePresenter {

    /**
     * Log the error with the default log mechanism.
     */
    protected void defaultErrorHandling(String tag, Exception e) {
        Log.e(tag, Log.getStackTraceString(e));
    }

}
