package com.tuan800.sinapoi.internal;

import com.tuan800.sinapoi.Persistence;
import com.tuan800.sinapoi.PoiService;
import com.tuan800.sinapoi.TaskManager;
import com.tuan800.sinapoi.model.PoiShop;
import com.tuan800.sinapoi.model.Task;
import com.tuan800.sinapoi.model.UrlTask;
import com.tuan800.sinapoi.util.STATUS;
import com.tuan800.sinapoi.util.URLException;
import com.tuan800.sinapoi.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @auth Yang Hua
 * Date: 1/22/13 Time: 4:18 PM
 */
public class TaskManagerMultiImpl implements TaskManager<Task> {

    private static PoiService<PoiShop> poiService = new PoiServiceImpl() ;
    private final Logger log = LoggerFactory.getLogger(TaskManagerMultiImpl.class);
    private final static Persistence persistence = new TaskPersistenceImpl();
    private final static Persistence urlpersistence = new UrlTaskPersistenceImpl();
    static String saveRelation = Utils.getConfig().getString("saveRelation") ;
    static String lostUrlStatus = Utils.getConfig().getString("lostUrlStatus") ;
    @Override
    public Persistence getPersistence(){
        return  persistence ;
    }

    @Override
    public void handle(Task task) {
        try {
            List<PoiShop> poi_shops = poiService.nearByPoiShop(task.getCoordinate()) ;
            List<PoiShop> shops = new ArrayList<PoiShop>() ;
            for(PoiShop shop: poi_shops){
                shop.setHui_lat(task.getCoordinate().getLat());
                shop.setHui_lng(task.getCoordinate().getLng());
                shop.setHuiShopId(task.getShopId());
                shops.add(shop);
            }
            persistence.saveOrUpdateShops(shops);
            //(lat, lng) 到 sina poiid 的对应关系
            if(null != saveRelation && "true".equals(saveRelation)){
                List<String> poi_xy = new ArrayList<String>() ;
                String sql_xy = "replace INTO shop_xy_multi(hui_lat, hui_lng, poiid) VALUES (" +task.getCoordinate().getLat()+"," + task.getCoordinate().getLng()+",\"";
                for(PoiShop s : poi_shops){
                    s.setHui_lat(task.getCoordinate().getLat());
                    s.setHui_lng(task.getCoordinate().getLng());
                    s.setHuiShopId(task.getShopId());
                    poi_xy.add(sql_xy+s.getPoiid()+"\")");
                    shops.add(s);
                }
                persistence.executeBatchSql(poi_xy);
            }
            task.setStatus(STATUS.COMPLETED.getDesc());
            task.setLastTime(Utils.format(new java.util.Date(System.currentTimeMillis()), "yyyy-MM-dd HH:MM"));
            persistence.assign(task);
        } catch (SQLException e) {
            log.info("task: -->" +task.toString()+"\nMassage:-->"+e.getMessage()+"  StackTrace-->" +  e.getStackTrace());
            task.setStatus(STATUS.ERROR.getDesc());
            task.setLastTime(Utils.format(new java.util.Date(System.currentTimeMillis()), "yyyy-MM-dd HH:MM"));
            persistence.assign(task);
        } catch (IOException e) {
            log.info("task: -->" +task.toString()+"\nMassage:-->"+e.getMessage()+"  StackTrace-->" +  e.getStackTrace());
            task.setStatus(STATUS.TIMEOUT.getDesc());
            task.setLastTime(Utils.format(new java.util.Date(System.currentTimeMillis()), "yyyy-MM-dd HH:MM"));
            persistence.assign(task);
            try {
                String thName = Thread.currentThread().getName() ;
                log.info(thName + " --> connect to sina timeout sleep for ten minutes");
                Thread.sleep(600000);
                log.info(thName + " --> wake up");
            } catch (InterruptedException e1) {
                e.printStackTrace();
                log.info("connect to sina timeout sleep for ten minutes");
            }
        } catch (URLException e) {
            UrlTask ut = new UrlTask(e.getMessage(), task.getCoordinate().getLat(), task.getCoordinate().getLng(), lostUrlStatus, task.getShopId()) ;
            urlpersistence.publish(ut);
        }

    }

    @Override
    public void handle(Task... tasks) {
           for(Task t: tasks){
               handle(t);
           }
    }
}
