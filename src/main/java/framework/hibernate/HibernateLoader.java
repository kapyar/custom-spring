package framework.hibernate;

import framework.parsers.HibernateSaxParser;
import framework.parsers.entities.DBProperties;


/**
 * Created by yaroslav on 12/6/16.
 */
public class HibernateLoader
{

    String propertiesFileName;
    String packageToScanName;
    DBProperties dbProperties;

    public  HibernateLoader(String propertiesFileName,String packageToScanName){

        this.propertiesFileName = propertiesFileName;
        this.packageToScanName  = packageToScanName;
        this.dbProperties       = new HibernateSaxParser(propertiesFileName).getDbProperties();
    }

    public String getPropertiesFileName() {
        return propertiesFileName;
    }

    public void setPropertiesFileName(String propertiesFileName) {
        this.propertiesFileName = propertiesFileName;
    }

    @Override
    public String toString() {
        return "HibernateLoader{" +
                "propertiesFileName='" + propertiesFileName + '\'' +
                ", packageToScanName='" + packageToScanName + '\'' +
                ", dbProperties=" + dbProperties +
                '}';
    }



}
