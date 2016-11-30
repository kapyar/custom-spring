package application.beans.darkside;

import framework.core.annotations.Service;

@Service
public class SupplyTroopService {

    private String service = "Service";

    @Override
    public String toString() {
        return "SupplyTroopService{" +
                "service='" + service + '\'' +
                '}';
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
