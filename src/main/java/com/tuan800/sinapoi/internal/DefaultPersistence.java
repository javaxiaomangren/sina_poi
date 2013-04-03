package com.tuan800.sinapoi.internal;

import com.tuan800.sinapoi.Persistence;
import com.tuan800.sinapoi.model.PoiShop;
import com.tuan800.sinapoi.util.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

/**
 * @auth Yang Hua
 * Date: 1/24/13 Time: 5:24 PM
 */
abstract class DefaultPersistence<T> implements Persistence<T> {
    private final Logger log = LoggerFactory.getLogger(DefaultPersistence.class) ;

    @Override
    public void publish() {
         throw new UnsupportedOperationException("not implemented yet") ;
    }

    @Override
    public void publish(T  tasks) {
         throw new UnsupportedOperationException("not implemented yet") ;
    }

    @Override
    public List getTasks() {
        throw new UnsupportedOperationException("not implemented yet") ;
    }

    @Override
    public List getTasksBySql(String sql) {
        throw new UnsupportedOperationException("not implemented yet") ;
    }

    @Override
    public List<T> getTasks(String sqlTemplate, Object[] args, Class<T> clazz) {
        throw new UnsupportedOperationException("not implemented yet") ;
    }

    @Override
    public void assign(T task) {
        throw new UnsupportedOperationException("not implemented yet") ;
    }
    /**
     * Report task exception
     * @param taskId
     * @param exception(@code String)
     */
    @Override
    public void report(int taskId, String exception) {
        Connection conn = DB.getConnection() ;
        PreparedStatement pstmt   = null ;
        ResultSet rs = null ;
        try{
            String sql = "INSERT INTO task_report(taskId, report) VALUES(?, ?)" ;
            pstmt  = conn.prepareStatement(sql) ;
            pstmt.setLong(1, taskId);
            pstmt.setString(2, exception);
            pstmt.execute();
        } catch (SQLException e) {
            log.info("fail to report tasks -->" + e.getMessage());
        }finally {
            DB.free(conn, pstmt, rs);
        }
    }

    @Override
    public void saveOrUpdateShops(List<PoiShop> shops) throws SQLException {
        Statement pstmt = null ;
        Connection conn = null ;
        String sql = "replace INTO poi_shop_multi(poiid ,title ,address ,lon ,lat ,category ,city ,province ,country ,url ,phone ,postcode ,weibo_id ,icon ,categorys ,category_name,map ,poi_pic ,poi_street_address ,checkin_user_num , herenow_user_num , selected , icon_show , checkin_num , tip_num , photo_num , todo_num , distance , hui_lng , hui_lat , huiShopId) VALUES" ;
        String tempsql = null  ;
        try {
            conn = DB.getConnection() ;
            pstmt = conn.createStatement();
            for(PoiShop s : shops){
                tempsql = null ;
                tempsql = sql+s.toSqlValue() ;
                pstmt.execute(sql+s.toSqlValue());
            }
           int [] r = pstmt.executeBatch() ;
        } catch (SQLException e) {
            log.info("failed insert into :" + tempsql);
            throw e ;
        }finally {
            DB.free(conn, pstmt, null);
        }
    }


    @Override
    public void executeBatchSql(List<String> sqls) throws SQLException {
        Statement pstmt = null ;
        Connection conn = null ;
        try {
            conn = DB.getConnection() ;
            pstmt = conn.createStatement();
            for(String sql : sqls){
                pstmt.addBatch(sql);
            }
            pstmt.executeBatch() ;
        } catch (SQLException e) {
            throw e;
        }finally {
            DB.free(conn, pstmt, null);
        }
    }
}
