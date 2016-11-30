package framework.parsers;

import framework.parsers.entities.Bean;

import java.util.List;

public interface Parser {
    
    public List<Bean> getBeanList();
    
    public String toString();     
    
}
