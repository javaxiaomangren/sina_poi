package com.tuan800.sinapoi;

/**
 * @auth Yang Hua
 * Date: 1/22/13 Time: 10:53 AM
 */
public interface TaskManager<T>{

    public void handle(T task) ;

    public void handle(T ... tasks) ;

    public Persistence<T> getPersistence() ;


}
