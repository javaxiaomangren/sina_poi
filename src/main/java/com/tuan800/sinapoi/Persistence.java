package com.tuan800.sinapoi;

import com.tuan800.sinapoi.model.PoiShop;

import java.sql.SQLException;
import java.util.List;

/**
 * Manage and handle tasks
 * @auth Yang Hua
 * Date: 1/22/13 Time: 10:12 AM
 */
public interface Persistence<T> {
    /**
     * Publish tasks
     */
    public void publish() ;

    /**
     * publish a sequence of Task
     * @param tasks
     */
    public void publish(T  tasks) ;

    /**
     * get tasks
     * @return
     */
    public List<T> getTasks() ;

    /**
     * get Tasks By a sql
     * @param sql
     * @return
     */
    public List<T> getTasksBySql(String sql) ;

    /**
     * get Tasks by sql params
     * @param sqlTemplate
     * @param args
     * @param clazz
     * @return
     */
    public List<T> getTasks(String sqlTemplate, Object[] args, Class<T> clazz) ;

    /**
     * Assign a task ,set status failed ,completed, timeout
     * @param task
     */
    public void assign(T task) ;

    /**
     * report why this task is failed or timeout
     * @param taskId
     * @param exception(@code String)
     */
    public void report(int taskId, String exception) ;

    /**
     * save shops
     * @param shops
     */
    public void saveOrUpdateShops(List<PoiShop> shops) throws SQLException;

    /**
     * save the mapping of (lat, lng) with poiid
     * @param sqls
     */

    public void executeBatchSql(List<String> sqls) throws SQLException;

}
