package com.example.demo.controller;

import com.example.demo.client.DomainServiceClient;
import com.example.demo.dto.RequestDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrchestratorControllerTest {

    @InjectMocks
    private OrchestratorController orchestratorController;

    @Mock
    private DomainServiceClient domainServiceClient;

    public OrchestratorControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessRequest_Success() {
        RequestDto requestDto = new RequestDto();
        requestDto.setData("Test data");

        doNothing().when(domainServiceClient).sendData(any(RequestDto.class));

        ResponseEntity<String> response = orchestratorController.processRequest(requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Processed successfully", response.getBody());
        verify(domainServiceClient, times(1)).sendData(requestDto);
    }

    @Test
    public void testProcessRequest_BadRequest() {
        RequestDto requestDto = new RequestDto();

        ResponseEntity<String> response = orchestratorController.processRequest(requestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request", response.getBody());
        verify(domainServiceClient, times(0)).sendData(any(RequestDto.class));
    }

    @Test
    public void testProcessRequest_InternalServerError() {
        RequestDto requestDto = new RequestDto();
        requestDto.setData("Test data");

        doThrow(new RuntimeException("Test Exception")).when(domainServiceClient).sendData(any(RequestDto.class));

        ResponseEntity<String> response = orchestratorController.processRequest(requestDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", response.getBody());
        verify(domainServiceClient, times(1)).sendData(requestDto);
    }
}
