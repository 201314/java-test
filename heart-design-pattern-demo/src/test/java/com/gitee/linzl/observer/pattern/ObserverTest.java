package com.gitee.linzl.observer.pattern;

/**
 * 在JAVA中可能通过继续Observable来实现
 *
 * @author linzl
 * @description
 * @email 2225010489@qq.com
 * @date 2019年7月6日
 */
public class ObserverTest {
    public static void main(String[] args) {
        ReceptionSubject recept = new ReceptionSubject();

        new NBAObserver("张三", recept);
        new StockObserver("李四", recept);

        //告知状态变化，通知所有观察者
        recept.setState("老板回来了,大家注意");
    }
}
