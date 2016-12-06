package framework.hibernate.helpers;

import java.util.List;

public class DBEntity {
    private String tableName;
    private List<String> paramNames;

    private Class clazz;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getParamNames() {
        return paramNames;
    }

    public void setParamNames(List<String> paramNames) {
        this.paramNames = paramNames;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "DBEntity{" +
                "tableName='" + tableName + '\'' +
                ", paramNames=" + paramNames +
                ", clazz=" + clazz +
                '}';
    }

    public String getParamsForSelect() {
        StringBuffer st = new StringBuffer();
        for (int i =0;i< paramNames.size()-1;i++){
            st.append(paramNames.get(i)).append(",");
        }
        st.append(paramNames.get(paramNames.size()-1));
        return st.toString();
    }

}
