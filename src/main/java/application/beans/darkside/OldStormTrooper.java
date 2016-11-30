package application.beans.darkside;

import framework.core.annotations.Component;

@Component
public class OldStormTrooper {

    private String name = "OldStormTrooper";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
