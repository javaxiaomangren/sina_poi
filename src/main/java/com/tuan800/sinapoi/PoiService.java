package com.tuan800.sinapoi;

import com.tuan800.sinapoi.model.Coordinate;
import com.tuan800.sinapoi.model.UrlTask;
import com.tuan800.sinapoi.util.URLException;

import java.io.IOException;
import java.util.List;

/**
 * Define a interface for get poi shop
 * @author Yang Hua
 */
public interface PoiService<T> {
    /**
     * Get shops near by the given location from sina weibo
     * @param basicPoint
     * @return  List<T>
     */
    public List<T>  nearByPoiShop(Coordinate basicPoint) throws IOException, URLException;

    public List<T> getPoiShopsByUrl(UrlTask ... task) throws URLException;

}
