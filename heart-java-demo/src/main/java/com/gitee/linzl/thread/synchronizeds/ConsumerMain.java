package com.gitee.linzl.thread.synchronizeds;

import java.util.concurrent.TimeUnit;

interface Data {
    String getRequest();
}

class RealData implements Data {
    private String result;

    public RealData(String queryStr) {
        System.out.println("根据" + queryStr + "进行查询，这是一个很耗时的操作..");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("操作完毕，获取结果");
        result = "查询结果";
    }

    @Override
    public String getRequest() {
        return result;
    }
}

/**
 * 生产者
 */
class ProducerData implements Data {
    private RealData realData;

    private boolean isReady = false;

    public synchronized void produce(String queryStr) {
        // 如果已经装载完毕了，就直接返回
        if (isReady) {
            return;
        }
        // 如果没装载，进行装载真实对象
        this.realData = new RealData(queryStr);
        isReady = true;
        // 进行通知
        this.notifyAll();
    }

    @Override
    public synchronized String getRequest() {
        // 如果没装载好 程序就一直处于阻塞状态
        while (!isReady) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 装载好直接获取数据即可
        return this.realData.getRequest();
    }
}

public class ConsumerMain {

    public Data consumer(final String queryStr) {
        // 1 我想要一个代理对象（Data接口的实现类）先返回给发送请求的客户端，告诉他请求已经接收到，可以做其他的事情
        ProducerData producerData = new ProducerData();
        // 2 启动一个新的线程，去加载真实的数据，传递给这个代理对象
        new Thread(() -> {
            // 3 这个新的线程可以去慢慢的加载真实对象，然后传递给代理对象
            producerData.produce(queryStr);
        }).start();
        return producerData;
    }

    public static void main(String[] args) {
        ConsumerMain fc = new ConsumerMain();
        Data data = fc.consumer("请求参数");
        System.out.println("请求发送成功!");
        System.out.println("做其他的事情...");

        String result = data.getRequest();
        System.out.println(result);
    }
}
