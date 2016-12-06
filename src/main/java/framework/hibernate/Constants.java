package framework.hibernate;

public class Constants {

    public static final String REG_TABLE_NAME       = "table_name";
    public static final String REG_PARAMS_NAMES     = "params_names";
    public static final String REG_PARAMS_VALUES    = "params_values";
    public static final String REG_WHERE_VALUE      = "where_values";


    public static final String TYPE_SELECT          = "SELECT";
    public static final String TYPE_SELECT_WHERE    = "SELECT WHERE ";

    public static final String TYPE_UPDATE          = "UPDATE";
    public static final String TYPE_DELETE          = "DELETE";
    public static final String TYPE_INSERT          = "INSERT";


    public static final String INSERT_STATEMENT         = "INSERT INTO "+REG_TABLE_NAME+" ("+REG_PARAMS_NAMES +") VALUES ("+REG_PARAMS_VALUES+")";
    public static final String SELECT_STATEMENT         =" SELECT "+REG_PARAMS_NAMES +" FROM "+REG_TABLE_NAME;
    public static final String SELECT_WHERE_STATEMENT   =" SELECT "+REG_PARAMS_NAMES +" FROM "+REG_TABLE_NAME+" WHERE "+REG_WHERE_VALUE +" = ? ";



}
