package org.example.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.grpc.ChatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {

//  @Value("${grpc.port}")
//  private int port;
//
//  @Bean
//  public Server grpcServer(ChatService chatService) {
//    return ServerBuilder.forPort(port)
//            .addService(chatService)
//            .build();
//  }
}