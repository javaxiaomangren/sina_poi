package com.tuan800.sinapoi.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tuan800.sinapoi.internal.TaskExecutorThreadImpl;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Database Connection
 * @author Yang Hua
 */
public class DB {

    private static final ComboPooledDataSource cpds = new ComboPooledDataSource() ;
    static Logger log = Logger.getLogger(TaskExecutorThreadImpl.class.getName());
    static PropertiesConfiguration conf = Utils.getConfig() ;

    static {
        try {
            cpds.setDriverClass(conf.getString("jdbc_driver")); //loads the jdbc driver
        } catch (PropertyVetoException e) {
           log.info(e.getMessage());
        }
        cpds.setJdbcUrl(conf.getString("jdbc_url"));
        cpds.setUser(conf.getString("jdbc_user"));
        cpds.setPassword(conf.getString("jdbc_password"));

        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(60);
    }


    /**
     * Return a connection from connection pool
     * @return
     */
      public static Connection getConnection()  {
          Connection conn = null ;
          try {
              conn = cpds.getConnection() ;
          } catch (SQLException e) {
             log.info(e.getMessage());
          }
          return conn ;
      }

    /**
     * set resource free
     * @param conn
     * @param stmt
     * @param rs
     */
      public static void free(Connection conn, Statement stmt, ResultSet rs){
          try{
              if(rs != null){
                  rs.close();
              }
              if(stmt != null){
                  stmt.close();
              }
              if(conn != null){
                  conn.close();
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }

}
