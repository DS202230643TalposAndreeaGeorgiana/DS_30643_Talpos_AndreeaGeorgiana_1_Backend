package org.example.grpc;

import grpc.chat.*;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GRpcService
public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {

    private Logger logger = LoggerFactory.getLogger(ChatService.class);

    private List<ChatMessage> messages = new CopyOnWriteArrayList<>();
    private List<StreamObserver<ChatMessage>> listeners = new CopyOnWriteArrayList<>();
    @Override
    public void receiveMessages(ReceiveMessagesRequests request, StreamObserver<ChatMessage> responseObserver) {
        try {
            System.out.println(request.getNumberOfMessages());
            System.out.println("called");
            this.listeners.add(responseObserver);

            messages.forEach(message -> sendMessageToListener(message, responseObserver));
            this.messages.clear();
            waitForNextMessages(responseObserver);
        } finally {
            logger.debug("Listener {} closed connection, removing", responseObserver);
            this.listeners.remove(responseObserver);
            responseObserver.onCompleted();
        }
    }

    private void dispatchMessageToListeners(ChatMessage message) {
        logger.info("{} listeners will receive message {}", this.listeners.size(), message);
        listeners.forEach(listener -> sendMessageToListener(message, listener));
    }

    @Override
    public void sendMessage(ChatMessage message, StreamObserver<Empty> responseObserver) {
        System.out.println("aici");
        message = ChatMessage.newBuilder(message)
                .setRead(false)//
                .build();
        this.messages.clear();
        this.messages.add(message);
        dispatchMessageToListeners(message);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    private void sendMessageToListener(ChatMessage message, StreamObserver<ChatMessage> listener) {
        synchronized (listener) {
            try {
//                System.out.println("in send message to listener");
//                this.messages.clear();
                listener.onNext(message);
//                if (message.getRead()) {
//                    System.out.println("read");
//                    this.messages.remove(message);
//                }
            } catch (Exception e) {
                logger.error("Error occured while dispatching message {} to {}, removing listener", message, listener);
                listeners.remove(listener);
                listener.notifyAll();
            }
        }
    }

    private void waitForNextMessages(StreamObserver<ChatMessage> responseObserver) {
        try {
            synchronized (responseObserver) {
                responseObserver.wait(1000000);
            }
        } catch (InterruptedException e) {
            logger.error("Error occurred while waiting for messages", e);
        }
    }


    @Override
    public void ping(ChatMessage request, StreamObserver<ChatMessage> responseObserver) {
        ChatMessage response = ChatMessage.newBuilder(request)
                .setMessage("Hello " + request.getUser() + ", your message was: " + request.getMessage())
                .setUser("gRPC server: " + serverDetails()).build();
        logger.info("Got {} sending {}", request, response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private String serverDetails() {
        try {
            return InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }

}
