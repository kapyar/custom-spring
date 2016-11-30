package application.classes.Transport;

import application.classes.Transport.Car;
import framework.core.annotations.Autowiring;


public class ParkingPlace {

    @Autowiring
    private Car transport;

    public Car getTransport() {
        return transport;
    }

    public void setTransport(Car transport) {
        this.transport = transport;
    }

    @Override
    public String toString() {
        return "ParkingPlace{" +
                "transport=" + transport +
                '}';
    }

}
