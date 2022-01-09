package com.example.grpc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.helloworld.HelloRequest;
import com.example.helloworld.HelloWorldServiceGrpc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.grpc.Metadata;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.MetadataUtils;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class GrpcApplicationTests {

	@Test
	void testHello() throws Exception {

		var sslContext = GrpcSslContexts.forClient()
		.trustManager(InsecureTrustManagerFactory.INSTANCE)
		.build();
		
		var channel = NettyChannelBuilder
		.forTarget("localhost:9090")
		.useTransportSecurity()
		.sslContext(sslContext)
		.build();
		var service = HelloWorldServiceGrpc.newBlockingStub(channel); 

		var extraHeaders = new Metadata();
		extraHeaders.put(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER), "Bearer 1234");

		service = MetadataUtils.attachHeaders(service, extraHeaders);

		var request = HelloRequest.newBuilder().setText("Thamatis").build();
		var response = service.hello(request);

		log.info("response from gRPC is {}", response);

		assertEquals("Hello, Thamatis", response.getText());
	}

}
