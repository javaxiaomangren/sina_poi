package com.tuan800.sinapoi;

/**
 * The way to execute tasks
 * @author Yang Hua
 */
public interface TaskExecutor<T> {

    public void execute() ;

    public void execute(T ... tasks) ;

}
