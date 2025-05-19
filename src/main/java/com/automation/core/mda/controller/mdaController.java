package com.automation.core.mda.controller;

import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.mda.dto.request.mdaRequest;
import com.automation.core.mda.dto.response.mdaResponse;
import com.automation.core.mda.model.ServicesModel;
import com.automation.core.mda.service.service.mdaService;
import com.automation.util.constant.AppConstant;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class mdaController {
    private final mdaService service;

    @PostMapping("/mda")
    public ResponseEntity<AppResponse<mdaResponse>> createMda(@RequestBody mdaRequest mdaRequest) {
        mdaResponse mdaResponse = service.createMda(mdaRequest);
        AppResponse<mdaResponse> appResponse = AppResponse.<mdaResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(mdaResponse).error("").build();
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @GetMapping("/mda")
    public ResponseEntity<AppResponse<List<mdaResponse>>> getMda() {
        List<mdaResponse> responses = service.getMdas();
        return ResponseEntity.ok().body(AppResponse.<List<mdaResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value()).data(responses).build());
    }

    @DeleteMapping("/mda/{mdaCode}")
    public ResponseEntity<AppResponse<String>> deleteMdaByCode(@PathVariable String mdaCode) {
        try {
            service.deleteMdaByCode(mdaCode);
            return ResponseEntity.ok(
                    new AppResponse<>("MDA deleted successfully", true, null)
            );

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AppResponse<>("MDA not found", false, null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AppResponse<>("Failed to delete MDA", false, null));
        }
    }

    @PostMapping("/mda/service")
    public ResponseEntity<AppResponse<mdaResponse>> createMdaWithServices(@RequestParam String mdaName, @RequestBody List<String> services) {
        mdaResponse mda = service.createMdaWithServices(mdaName, services);
        return ResponseEntity.ok().body(AppResponse.<mdaResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(mda).error("").build());
    }

    @GetMapping("/{id}/services")
    public ResponseEntity<List<ServicesModel>> getServicesByMda(@PathVariable Long id) {
        return ResponseEntity.ok(service.getServicesByMda(id));
    }


    @DeleteMapping("/mda/{id}")
    public ResponseEntity<AppResponse<String>> deleteMda(@PathVariable Long id) {
        String message = service.deleteMda(id);
        AppResponse<String> response = new AppResponse<>(true, message);
        return ResponseEntity.ok(response);
    }




}
