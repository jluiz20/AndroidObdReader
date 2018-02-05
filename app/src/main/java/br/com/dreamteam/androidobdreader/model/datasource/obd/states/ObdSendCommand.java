package br.com.dreamteam.androidobdreader.model.datasource.obd.states;


import com.github.pires.obd.commands.ObdCommand;

import java.util.Calendar;

import br.com.dreamteam.androidobdreader.model.datasource.ModelEvent;

/**
 * Connection state has changed
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */

public class ObdSendCommand extends ModelEvent {
    private final ObdCommand command;

    public ObdSendCommand(Calendar dispatchTime, ObdCommand command) {
        super(dispatchTime);
        this.command = command;
    }

    public ObdCommand getCommand() {
        return command;
    }
}
