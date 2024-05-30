package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.client.DomainServiceClient;
import com.example.demo.dto.RequestDto;

@RestController
@RequestMapping("/orchestrator")
public class OrchestratorController {

    @Autowired
    private DomainServiceClient domainServiceClient;

    @PostMapping("/process")
    public ResponseEntity<String> processRequest(@RequestBody RequestDto requestDto) {
        try {
            // Validar el request
            if (requestDto == null || requestDto.getData() == null) {
                return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
            }
            // Enviar al dominio
            domainServiceClient.sendData(requestDto);
            return new ResponseEntity<>("Processed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
