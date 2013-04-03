package com.tuan800.sinapoi.internal;

import com.tuan800.sinapoi.Persistence;
import com.tuan800.sinapoi.PoiService;
import com.tuan800.sinapoi.TaskManager;
import com.tuan800.sinapoi.model.PoiShop;
import com.tuan800.sinapoi.model.UrlTask;
import com.tuan800.sinapoi.util.URLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @auth Yang Hua
 * Date: 1/24/13 Time: 11:58 AM
 */
public class UrlTaskManagerImpl implements TaskManager<UrlTask> {

    private static PoiService<PoiShop> poiService = new PoiServiceImpl() ;
    private final Logger log = LoggerFactory.getLogger(UrlTaskManagerImpl.class);
    private final static Persistence urlpersistence = new UrlTaskPersistenceImpl();


    @Override
    public void handle(UrlTask task) {
        try{
            List<PoiShop> shops = poiService.getPoiShopsByUrl(task) ;
            List<PoiShop> temp = new ArrayList<PoiShop>() ;
            for(PoiShop shop: shops){
                shop.setHui_lat(task.getLat());
                shop.setHui_lng(task.getLng());
                shop.setHuiShopId(task.getShopId());
                temp.add(shop);
            }
            urlpersistence.saveOrUpdateShops(temp);
            task.setStatus("completed");
            urlpersistence.assign(task);
        } catch (URLException e) {
        } catch (SQLException e) {
        }
    }

    @Override
    public void handle(UrlTask... tasks) {
           for(UrlTask ut: tasks){
               this.handle(ut);
           }
    }

    @Override
    public Persistence<UrlTask> getPersistence() {
        return urlpersistence;
    }
}
