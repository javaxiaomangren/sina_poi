package com.tuan800.sinapoi.model;

import java.math.BigDecimal;

/**
 * sina weibo poi shop bean
 * @author Yang Hua
 */
public class PoiShop {
    private String poiid ;
    private String title ;
    private String  address ;
    private BigDecimal lon ;
    private BigDecimal lat ;
    private String category ;
    private String city ;
    private String province ;
    private String country ;
    private String url ;
    private String phone ;
    private String postcode ;
    private String weibo_id ;
    private String icon ;
    private String categorys ;
    private String category_name;
    private String map ;
    private String poi_pic ;
    private String poi_street_address ;
    private Integer checkin_user_num ;
    private Integer herenow_user_num ;
    private Integer selected ;
    private String  icon_show ;
    private Integer checkin_num ;
    private Integer tip_num ;
    private Integer photo_num ;
    private Integer todo_num ;
    private Integer distance ;
    private BigDecimal hui_lng ;
    private BigDecimal hui_lat ;
    private Integer huiShopId ;

    public String getPoiid() {
        return poiid;
    }

    public void setPoiid(String poiid) {
        this.poiid = poiid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getWeibo_id() {
        return weibo_id;
    }

    public void setWeibo_id(String weibo_id) {
        this.weibo_id = weibo_id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCategorys() {
        return categorys;
    }

    public void setCategorys(String categorys) {
        this.categorys = categorys;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getPoi_pic() {
        return poi_pic;
    }

    public void setPoi_pic(String poi_pic) {
        this.poi_pic = poi_pic;
    }

    public String getPoi_street_address() {
        return poi_street_address;
    }

    public void setPoi_street_address(String poi_street_address) {
        this.poi_street_address = poi_street_address;
    }

    public Integer getCheckin_user_num() {
        return checkin_user_num;
    }

    public void setCheckin_user_num(Integer checkin_user_num) {
        this.checkin_user_num = checkin_user_num;
    }

    public Integer getHerenow_user_num() {
        return herenow_user_num;
    }

    public void setHerenow_user_num(Integer herenow_user_num) {
        this.herenow_user_num = herenow_user_num;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public String getIcon_show() {
        return icon_show;
    }

    public void setIcon_show(String icon_show) {
        this.icon_show = icon_show;
    }

    public Integer getCheckin_num() {
        return checkin_num;
    }

    public void setCheckin_num(Integer checkin_num) {
        this.checkin_num = checkin_num;
    }

    public Integer getTip_num() {
        return tip_num;
    }

    public void setTip_num(Integer tip_num) {
        this.tip_num = tip_num;
    }

    public Integer getPhoto_num() {
        return photo_num;
    }

    public void setPhoto_num(Integer photo_num) {
        this.photo_num = photo_num;
    }

    public Integer getTodo_num() {
        return todo_num;
    }

    public void setTodo_num(Integer todo_num) {
        this.todo_num = todo_num;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public BigDecimal getHui_lng() {
        return hui_lng;
    }

    public void setHui_lng(BigDecimal hui_lng) {
        this.hui_lng = hui_lng;
    }

    public BigDecimal getHui_lat() {
        return hui_lat;
    }

    public void setHui_lat(BigDecimal hui_lat) {
        this.hui_lat = hui_lat;
    }

    public Integer getHuiShopId() {
        return huiShopId;
    }

    public void setHuiShopId(Integer huiShopId) {
        this.huiShopId = huiShopId;
    }

    @Override
    public String toString() {
        return  "poiid='" + poiid + '\'' +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", category='" + category + '\'' +
                ", city='" + city + '\'' +
                ", province=" + province +
                ", country='" + country + '\'' +
                ", url='" + url + '\'' +
                ", phone='" + phone + '\'' +
                ", postcode='" + postcode + '\'' +
                ", weibo_id='" + weibo_id + '\'' +
                ", icon='" + icon + '\'' +
                ", categorys='" + categorys + '\'' +
                ", category_name='" + category_name + '\'' +
                ", map='" + map + '\'' +
                ", poi_pic='" + poi_pic + '\'' +
                ", poi_street_address='" + poi_street_address + '\'' +
                ", checkin_user_num=" + checkin_user_num +
                ", herenow_user_num=" + herenow_user_num +
                ", selected=" + selected +
                ", icon_show='" + icon_show + '\'' +
                ", checkin_num='" + checkin_num + '\'' +
                ", tip_num=" + tip_num +
                ", photo_num=" + photo_num +
                ", todo_num=" + todo_num +
                ", distance=" + distance +
                ", hui_lng=" + hui_lng +
                ", hui_lat=" + hui_lat +
                ", huiShopId=" + huiShopId
                ;
    }

    public String toSqlValue() {
        return "(\'"+ poiid + "\' ,\'" + title + "\' ,\'" +address  + "\' ," +lon + "," + lat + ",\'" + category + "\' ,\'" +
                city + "\' ,\'" +province + "\' ,\'" +country + "\' ,\'" +url + "\' ,\'" +phone + "\' ,\'" +postcode + "\' ,\'" +
                weibo_id + "\' ,\'" + icon + "\' ,\'" + categorys + "\' ,\'" + category_name + "\' ,\'" + map + "\' ,\'" + poi_pic
                + "\' ,\'" + poi_street_address + "\' ," + checkin_user_num + "," + herenow_user_num + ", " + selected +",\'" + icon_show
                + "\' ," + checkin_num + " ," + tip_num + "," + photo_num + "," + todo_num +", " + distance+", " + hui_lng +  "," + hui_lat +
                ", " + huiShopId +")" ;
    }
}
