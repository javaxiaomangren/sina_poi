package com.tuan800.sinapoi.internal;

import com.tuan800.sinapoi.PoiService;
import com.tuan800.sinapoi.model.Coordinate;
import com.tuan800.sinapoi.model.PoiShop;
import com.tuan800.sinapoi.model.UrlTask;
import com.tuan800.sinapoi.util.URLException;
import com.tuan800.sinapoi.util.Utils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @auth Yang Hua
 * Date: 1/22/13 Time: 4:51 PM
 */
public class PoiServiceImpl implements PoiService {

    private final Logger log = LoggerFactory.getLogger(PoiServiceImpl.class);

    ObjectMapper mapper = new ObjectMapper() ;
    static PropertiesConfiguration conf = Utils.getConfig() ;

    /**
     * Fetch PoiShops by the given Coordinate
     * @param basicPoint
     * @return  a list of PoiShop
     * @throws IOException
     */
    @Override
    public List<PoiShop> nearByPoiShop(Coordinate basicPoint) throws IOException, URLException {
        int pageSize = getPageSize(getUrl(basicPoint.getLat(), basicPoint.getLng(), "1")) ;
        List<PoiShop> nearBy = new ArrayList<PoiShop>() ;

        for (int i = 1; i <= pageSize; i++) {
            JsonNode tree = null;
            String ur = getUrl(basicPoint.getLat(), basicPoint.getLng(), i+"") ;
            System.out.println(ur);
            try {
                tree = mapper.readTree(new URL(ur));
            } catch (IOException e) {
                log.info("lost url---->" + ur);
                throw new URLException(ur) ;
            }
            if(null != tree){
                JsonNode arrays = tree.get("pois") ;
                if(null != arrays){
                    Iterator<JsonNode> it = arrays.iterator() ;
                    while (it.hasNext()){
                        JsonNode node = it.next() ;
                        PoiShop p = setPoiShop(node) ;
                        if(null != p)
                            nearBy.add(p);
                    }
                }
            }
        }
        log.info("get near shops count=" + (nearBy.size()==0?basicPoint.toString():nearBy.size()));

        return nearBy;
    }

    public int getPageSize(String url) throws IOException {
        int pageSize = 0 ;
        try {
            JsonNode tree = mapper.readTree(new URL(url)) ;
            if(null != tree){
                JsonNode node = tree.get("total_number") ;
                if(null != node){
                    int total = node.getIntValue() ;
                    pageSize = (total + 49) / 50 ;
                }
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
        return pageSize;
    }

    private PoiShop setPoiShop(JsonNode jo){
        PoiShop shop = new PoiShop() ;
        try{
        shop.setPoiid(getValue(jo.get("poiid")));
        shop.setTitle(getValue(jo.get("title")));
        shop.setAddress(getValue(jo.get("address")));
        shop.setLon(new BigDecimal(getValue(jo.get("lon"))));
        shop.setLat(new BigDecimal(getValue(jo.get("lat"))));
        shop.setCategory(jo.get("category").getTextValue());
        shop.setCity(jo.get("city").getTextValue());
        shop.setProvince(jo.get("province").getTextValue()) ;
        shop.setCountry(jo.get("country").getTextValue()) ;
        shop.setUrl(getValue(jo.get("url")));
        shop.setPhone(jo.get("phone").getTextValue());
        shop.setPostcode(jo.get("postcode").getTextValue());
        shop.setWeibo_id(jo.get("weibo_id").getTextValue()) ;
        shop.setIcon(jo.get("icon").getTextValue());
        shop.setCategorys(getValue(jo.get("categorys")));
        shop.setCategory_name(getValue(jo.get("category_name")));
        shop.setMap(jo.get("map").getTextValue());
        shop.setPoi_pic(jo.get("poi_pic").getTextValue());
        shop.setPoi_street_address(getValue(jo.get("poi_street_address")));
        shop.setCheckin_user_num(jo.get("checkin_user_num").getIntValue());
        shop.setHerenow_user_num(jo.get("herenow_user_num").getIntValue());
        shop.setSelected(jo.get("selected").getIntValue());
        shop.setIcon_show(jo.get("icon_show").getTextValue()) ;
        shop.setCheckin_num(jo.get("checkin_num").getIntValue());
        shop.setTip_num(jo.get("tip_num").getIntValue());
        shop.setPhoto_num(jo.get("photo_num").getIntValue());
        shop.setTodo_num(jo.get("todo_num").getIntValue());
        shop.setDistance(jo.get("distance").getIntValue());
        } catch (NumberFormatException e){
            shop = null ;
            log.info("set shop Value error "+ e.getMessage() + getValue(jo.get("lon")) + getValue(jo.get("lat")) ) ;
        }catch (Exception e){
            shop = null ;
            log.info("set shop Value error "+ e.getMessage() + getValue(jo.get("lon")) + getValue(jo.get("lat")) ) ;
        }
        return shop ;
    }

    /**
     *  replace some special character like "'", ","
     * @param n
     * @return
     */
    private String getValue(JsonNode n){
        String s = "" ;
        if(null != n){
            String value = n.getTextValue() ;
            if(null != value){
                if(value.length()>1&&!"".equals(value) && !"null".equals(value)){
                   s= value.replace("\\","").replace("'", "\\'").replace(",", "\\,") ;
                }
            }
        }
        return s ;
    }

    private String getUrl(BigDecimal lat, BigDecimal lng, String page){
        String url = conf.getString("urlTemplate") ;
        return String.format(url, lat.toString(), lng.toString(), page) ;
    }

    /**
     * handle timeout urls
     * @param tasks
     * @return
     * @throws URLException
     */
    @Override
    public List<PoiShop> getPoiShopsByUrl(UrlTask ... tasks) throws URLException {
        List<PoiShop> result = new ArrayList<PoiShop>() ;
        for (UrlTask ut: tasks) {
            try {
                JsonNode tree = mapper.readTree(new URL(ut.getUrl())) ;
                if(null != tree){
                    JsonNode arrays = tree.get("pois") ;
                    if(null != arrays){
                        Iterator<JsonNode> it = arrays.iterator() ;
                        while (it.hasNext()){
                            JsonNode node = it.next() ;
                            PoiShop p = setPoiShop(node) ;
                            if (null != p)
                                result.add(p);
                        }
                    }
                }
            } catch (IOException e) {
                throw new URLException(e.getMessage()) ;
            }
        }
        return result ;
    }

}
