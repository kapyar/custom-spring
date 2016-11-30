package application.beans.darkside;

import framework.core.annotations.Autowiring;
import framework.core.annotations.Controller;

@Controller
public class DarkStarDefencePointController {

    public LazerBazuka getLazerBazuka() {
        return lazerBazuka;
    }

    public void setLazerBazuka(LazerBazuka lazerBazuka) {
        this.lazerBazuka = lazerBazuka;
    }

    @Autowiring
    private LazerBazuka lazerBazuka;

    private String controller = "DarkStarDefencePointController";

    @Override
    public String toString() {
        return "DarkStarDefencePointController{" +
                "controller='" + controller + '\'' +
                ", lazerBazuka=" + lazerBazuka +
                '}';
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }
}
