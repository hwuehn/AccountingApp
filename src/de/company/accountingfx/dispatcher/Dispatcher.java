package de.company.accountingfx.dispatcher;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.company.accountingfx.store.AppDB;

public class Dispatcher {

    private final AppDB appDB;
    private final EventBus eventBus;

    private Dispatcher() {
        appDB = new AppDB();
        eventBus = new EventBus();
        eventBus.register(this);
    }

    public static void subscribe(Object o) {
        System.out.println("Dispatcher.subscribe");
        System.out.println("o= " + o);
        getInstance().eventBus.register(o);
    }

    public static Dispatcher getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Dispatcher INSTANCE = new Dispatcher();
    }

    public static void dispatch(IMsg msg) {
        getInstance().dispatch_(msg);
    }

    private void dispatch_(IMsg msg) {
        System.out.println(msg);
        eventBus.post(msg);
    }

    @Subscribe
    public void handleDeadEvent(DeadEvent deadEvent) {
        System.out.println("!!! No subscriber message: !!!");
        System.out.println(deadEvent.getEvent().toString());
    }

    @Subscribe
    public void dispatchPersistMessage(PersistMessage msg) {
        switch (msg.getMsgType()) {


        }
    }

}
