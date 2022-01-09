package com.example.grpc;

import com.example.helloworld.HelloRequest;
import com.example.helloworld.HelloResponse;
import com.example.helloworld.HelloWorldServiceGrpc;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.security.access.annotation.Secured;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@GRpcService
@Slf4j
@Secured({})
public class HelloWorldServiceImpl extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {
    
    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {

        log.info("Hello function calledm, request={}", request);
        var name = request.getText();
        var message = "Hello, " + name;

        var helloResponse = HelloResponse.newBuilder().setText(message).build();

        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }
}
