package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.RequestDto;

@FeignClient(name = "domainService", url = "http://localhost:8081")
public interface DomainServiceClient {

    @PostMapping("/domain/save")
    void sendData(@RequestBody RequestDto requestDto);
}