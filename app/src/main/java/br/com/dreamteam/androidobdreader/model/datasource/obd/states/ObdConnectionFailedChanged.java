package br.com.dreamteam.androidobdreader.model.datasource.obd.states;


import java.util.Calendar;

import br.com.dreamteam.androidobdreader.model.datasource.ModelEvent;

/**
 * Connection state has changed
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class ObdConnectionFailedChanged extends ModelEvent {
    public ObdConnectionFailedChanged(Calendar dispatchTime) {
        super(dispatchTime);
    }

}
