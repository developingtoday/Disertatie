package com.abstracte;

/**
 * Created with IntelliJ IDEA.
 * User: Revan
 * Date: 4/25/12
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public interface INotifier<T> {
    public void notifyView(T t);
}
