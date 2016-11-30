package application.beans.FarFarGalaxy.Ammo;

/**
 * Created by yaroslav on 11/30/16.
 */
public class Weapon {

    @Override
    public String toString() {
        return "Weapon{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", caliber=" + caliber +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCaliber() {
        return caliber;
    }

    public void setCaliber(String caliber) {
        this.caliber = Integer.parseInt(caliber);
    }

    private String type;
    private String name;
    private int caliber;

}
