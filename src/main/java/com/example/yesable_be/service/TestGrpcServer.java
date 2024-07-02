package com.example.yesable_be.service;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class TestGrpcServer {

    private Server helloServer;

    public TestGrpcServer(int port) {
        helloServer= ServerBuilder.forPort(port).addService(new GrpcServerService()).build();
    }

    public void start() {
        try {
            helloServer.start();
            System.out.println("Server start "+helloServer.getPort());
            helloServer.awaitTermination();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
