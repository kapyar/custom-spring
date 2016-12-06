package application.beans.darkside;

import framework.core.annotations.Autowiring;
import framework.core.annotations.Component;

@Component
public class LazerBazuka {

    private String name = "SuperMassiveBazuka";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @Autowiring
//    private OldStormTrooper oldStormTrooper;
}
