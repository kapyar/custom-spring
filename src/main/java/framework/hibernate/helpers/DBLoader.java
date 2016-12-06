package framework.hibernate.helpers;

import framework.hibernate.QueryFormater;
import framework.parsers.entities.DBProperties;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBLoader {

    public List loadEntities(DBProperties dbProperties, DBEntity entity) throws Exception{
        List<Object> list   = new ArrayList<Object>();
        Connection conn     = null;
        Statement stmt      = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName(dbProperties.getDriverClass());

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(dbProperties.getConnectionURL(),dbProperties.getUserName(),dbProperties.getPassword());

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            QueryFormater queryFormater = new  QueryFormater();
            sql = queryFormater.formatSelectQuery(entity);
            ResultSet rs = stmt.executeQuery(sql);
            Class clazz = entity.getClazz();
            //STEP 5: Extract data from result set

            while(rs.next()) {
                Object res = entity.getClazz().newInstance();
                for (String par : entity.getParamNames()) {
                    Field param = clazz.getDeclaredField(par);
                    Class paramclass = param.getType();
                    switch (paramclass.getSimpleName()) {
                        case "int":
                        case "Integer":
                            param.set(res, rs.getInt(par));
                            break;
                        case "float":
                            param.set(res, rs.getFloat(par));
                            break;
                        case "double":
                            param.set(res, rs.getDouble(par));
                            break;
                        case "String":
                            param.set(res, rs.getString(par));
                            break;
                        case "boolean":
                            param.set(res, rs.getBoolean(par));
                            break;
                        default:
                            rs.getObject(par);
                            break;
                    }
                }
                list.add(res);
            }

            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try

        return list;
    }


}
