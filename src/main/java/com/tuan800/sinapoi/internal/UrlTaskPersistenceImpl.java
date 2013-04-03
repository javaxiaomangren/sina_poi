package com.tuan800.sinapoi.internal;

import com.tuan800.sinapoi.model.UrlTask;
import com.tuan800.sinapoi.util.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @auth Yang Hua
 * Date: 1/24/13 Time: 4:49 PM
 */
public class UrlTaskPersistenceImpl extends DefaultPersistence<UrlTask> {

    private final Logger log = LoggerFactory.getLogger(UrlTaskPersistenceImpl.class) ;

    @Override
    public void publish(UrlTask  tasks) {
       Connection conn = null ;
       Statement stmt = null ;
        try{
            conn = DB.getConnection() ;
            String sqlTemplate="insert into task_url(url, lat, lng, status, shopId) " ;
            stmt = conn.createStatement() ;
            stmt.execute(String.format(sqlTemplate+tasks.toSqlValue())) ;
        } catch (SQLException e) {
            log.info("fail to save lost url " + e.getMessage());
        }finally {
            DB.free(conn, stmt, null);
        }
    }

    @Override
    public List<UrlTask> getTasksBySql(String sql) {
        Connection conn = null ;
        Statement stmt  = null ;
        ResultSet rs    = null ;
        List<UrlTask> result = new ArrayList<UrlTask>() ;
        try{
            conn = DB.getConnection() ;
            if(null == sql){
                sql = "select taskId, url, lat, lng, status, shopId from task_url where (status='ready' or status='timeout') limit 16 " ;
            }
            stmt = conn.createStatement() ;
            rs = stmt.executeQuery(sql) ;
            while(rs.next()){
                UrlTask ut = new UrlTask(rs.getString(2),
                                         rs.getBigDecimal(3),
                                         rs.getBigDecimal(4),
                                         rs.getString(5),
                                         rs.getInt(6)) ;
                ut.setTaskId(rs.getInt(1));
                result.add(ut);
            }

        } catch (SQLException e) {
            log.info("fail to save lost url " + e.getMessage());
        }finally {
            DB.free(conn, stmt, rs);
        }
        return result ;
    }

    /**
     * assign task ,set task status and execute lastTime
     * @param task
     */
    @Override
    public void assign(UrlTask task) {
        Connection conn = DB.getConnection() ;
        PreparedStatement pstmt   = null ;
        ResultSet rs = null ;
        try{
            String sql = "UPDATE task_url SET status=? WHERE taskId=?" ;
            pstmt  = conn.prepareStatement(sql) ;
            pstmt.setString(1, task.getStatus());
            pstmt.setLong(2, task.getTaskId());
            pstmt.executeUpdate();
            log.info("assigned task -->" + task.getStatus());
        } catch (SQLException e) {
            log.info("fail to assign tasks -->" + task.getTaskId() +" : "+ e.getMessage());
        }finally {
            DB.free(conn, pstmt, rs);
        }
    }
}
