package framework.hibernate;

import framework.hibernate.helpers.DBEntity;

import java.util.HashMap;
import java.util.Map;

import static framework.hibernate.Constants.*;

public class QueryFormater {
    public QueryFormater() {
        fillQueriesTemplated();
    }

    private static Map<String, String> queries = new HashMap<String,String>();

    private void fillQueriesTemplated(){
        queries.put(TYPE_SELECT,SELECT_STATEMENT);
        queries.put(TYPE_UPDATE,UPDATE_STATEMENT);
        queries.put(TYPE_DELETE,DELETE_STATEMENT);
        queries.put(TYPE_INSERT,INSERT_STATEMENT);
        queries.put(TYPE_SELECT_WHERE,SELECT_WHERE_STATEMENT);

    }

    public String formatSelectQuery(DBEntity entity){
        String retQuery =queries.get(TYPE_SELECT);
        retQuery =  retQuery.replace(Constants.REG_TABLE_NAME,entity.getTableName());
        retQuery = retQuery.replace(Constants.REG_PARAMS_NAMES,entity.getParamsForSelect());
        return retQuery;


    }
}