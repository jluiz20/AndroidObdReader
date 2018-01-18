package br.com.dreamteam.androidobdreader.di;

import android.app.Application;
import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Named;
import javax.inject.Singleton;

import br.com.dreamteam.androidobdreader.di.threads.JobExecutor;
import br.com.dreamteam.androidobdreader.di.threads.PostExecutionThread;
import br.com.dreamteam.androidobdreader.di.threads.ThreadExecutor;
import br.com.dreamteam.androidobdreader.di.threads.UIThread;
import dagger.Module;
import dagger.Provides;

/**
 * This module is responsible for provide general classes related to the application,
 * usually these objects live during the entire lifecycle of the application.
 *
 * @author João Luiz Vieira <vieira.jluiz@gmail.com>.
 */
@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Named("applicationContext")
    @SuppressWarnings({"unused", "WeakerAccess"})
    public Context providesApplication() {
        return application;
    }

    @Provides
    @SuppressWarnings({"unused", "WeakerAccess"})
    public EventBus providesEventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }


}
