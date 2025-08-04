package br.com.danilobandeira29.infrastructure.gateways;

public interface QueueGateway {
    void publish(String content);
}
