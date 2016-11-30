package application.beans.darkside;

import framework.core.annotations.Repository;

@Repository
public class DarkStarRepository {

    private String repository = "DarkStarRepository";

    @Override
    public String toString() {
        return "DarkStarRepository{" +
                "repository='" + repository + '\'' +
                '}';
    }

    public String getRepository() {
        return repository;
    }
    public void setRepository(String repository) {
        this.repository = repository;
    }

}
