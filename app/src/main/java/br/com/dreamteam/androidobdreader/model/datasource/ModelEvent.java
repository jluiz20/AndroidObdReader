package br.com.dreamteam.androidobdreader.model.datasource;

import java.util.Calendar;

/**
 * Super class to identify all model events that will notify the presentation layer.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>
 */
public abstract class ModelEvent {

    private Calendar dispatchTime;

    public ModelEvent(Calendar dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public Calendar getDispatchTime() {
        return dispatchTime;
    }

    @Override
    public String toString() {
        return "ModelEvent{" +
                "dispatchTime=" + dispatchTime +
                '}';
    }
}
