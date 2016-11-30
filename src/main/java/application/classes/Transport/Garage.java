package application.classes.Transport;

import application.classes.Transport.Bus;
import application.classes.Transport.Transport;

public class Garage {

    Bus transport;

    public Transport getTransport() {
        return transport;
    }

    @Override
    public String toString() {
        return "Garage{" +
                "transport=" + transport +
                '}';
    }

    public void setTransport(Bus transport) {
        this.transport = transport;
    }
}
