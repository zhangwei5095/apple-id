package com.appleframework.id.zookeeper;

import com.appleframework.id.ZookeeperIdGenerator;

public class QndZookeeperIdGenerator {

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        try {
            ZookeeperIdGenerator idGenerator = ZookeeperIdGenerator.getInstance("localhost:2181/id-server");
            long id = idGenerator.nextId("default");
            System.out.println(id);
        } catch (Exception e) {
        	e.printStackTrace();
            long t2 = System.currentTimeMillis();
            System.out.println("Failed after [" + (t2 - t1) + "].");
        }
    }
}
