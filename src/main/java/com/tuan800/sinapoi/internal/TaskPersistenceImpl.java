package com.tuan800.sinapoi.internal;

import com.tuan800.sinapoi.model.Coordinate;
import com.tuan800.sinapoi.model.Task;
import com.tuan800.sinapoi.util.DB;
import com.tuan800.sinapoi.util.Utils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @auth Yang Hua
 * Date: 1/22/13 Time: 3:55 PM
 */
 public class TaskPersistenceImpl extends DefaultPersistence<Task> {

    private final Logger log = LoggerFactory.getLogger(TaskPersistenceImpl.class);

    static PropertiesConfiguration conf = Utils.getConfig();

    /**
     * Publish task from multi point (the edge of a city)
     */
    @Override
    public void publish() {
        Connection conn = DB.getConnection() ;
        Statement insertStmt = null ;
        try{
            insertStmt = conn.createStatement() ;
            Set<Coordinate> coordinates = Utils.genMultiPoint(
                    conf.getFloat("publish.minLat"),
                    conf.getFloat("publish.maxLat"),
                    conf.getFloat("publish.minLng"),
                    conf.getFloat("publish.maxLng")) ;

            String shopId = conf.getString("publish.shopId") ;
            String status = conf.getString("publish.status") ;
            String df     = conf.getString("publish.date_fmt") ;
            String city   = conf.getString("publish.city") ;
            String sqlTemplate = "insert into task_multi(shopId, lat, lng, status, date_fmt,city)" +
                    "  values(%s, %s, %s, '%s', '%s', '%s') " ;
            int count = 0 ;
            for(Coordinate c: coordinates){
                String sl = String.format(sqlTemplate, shopId, c.getLat(), c.getLng(), status, df, city) ;
                insertStmt.addBatch(sl) ;
                if(++count % 1000 == 0){
                    insertStmt.executeBatch() ;
                    insertStmt.clearBatch() ;
                }
            }
            insertStmt.executeBatch() ;
            log.info("published tasks count->" + coordinates.size());
        } catch (SQLException e) {
            log.info("fail to publish tasks -->" + e.getMessage());
        }finally {
            DB.free(conn, insertStmt, null);
        }
    }

    @Override
    public List<Task> getTasksBySql(String sql) {
        Connection conn =  DB.getConnection() ;
        PreparedStatement pstmt   = null ;
        ResultSet rs = null ;
        List<Task> tasks = new ArrayList<Task>() ;
        try{
            pstmt  = conn.prepareStatement(sql) ;
            rs = pstmt.executeQuery() ;
            while (rs.next()){
                Task t = new Task() ;
                t.setTaskId(rs.getInt(1));
                t.setShopId(rs.getInt(2));
                t.setCoordinate(new Coordinate(rs.getBigDecimal(3), rs.getBigDecimal(4))) ;
                t.setCreateDate(rs.getDate(5));
                t.setLastTime(rs.getString(6));
                t.setStatus(rs.getString(7));
                t.setDate_fmt(rs.getString(8));
                t.setCity(rs.getString(9));
                tasks.add(t);
            }
        } catch (SQLException e) {
            log.info("fail to get tasks -->" + e.getMessage());
        }finally {
            DB.free(conn, pstmt, rs);
        }
        return tasks ;
    }

    /**
     * assign task ,set task status and execute lastTime
     * @param task
     */
    @Override
    public void assign(Task task) {
        Connection conn = DB.getConnection() ;
        PreparedStatement pstmt   = null ;
        ResultSet rs = null ;
        try{
            String sql = "UPDATE task_multi SET lastTime=?, status=? WHERE taskId=?" ;
            pstmt  = conn.prepareStatement(sql) ;
            pstmt.setString(1, task.getLastTime());
            pstmt.setString(2, task.getStatus());
            pstmt.setLong(3, task.getTaskId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.info("fail to assign tasks -->" + task.getTaskId() +" : "+ e.getMessage());
        }finally {
            DB.free(conn, pstmt, rs);
        }
    }
}
