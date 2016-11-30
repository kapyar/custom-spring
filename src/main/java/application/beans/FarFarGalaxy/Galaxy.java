package application.beans.FarFarGalaxy;

import application.beans.FarFarGalaxy.Ammo.Weapon;
import framework.core.annotations.Component;

public class Galaxy {

    private String name;
    private Weapon weapon;

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Galaxy{" +
                "name='" + name + '\'' +
                ", weapon=" + weapon +
                '}';
    }


}
