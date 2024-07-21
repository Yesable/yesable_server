package com.example.yesable_be;

import com.example.yesable_be.service.TestGrpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class YesableBeApplication {

    public static void main(String[] args) {

        SpringApplication.run(YesableBeApplication.class, args);
        TestGrpcServer ts=new TestGrpcServer(4321);
        ts.start();
    }

}
