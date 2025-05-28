package com.automation.core.lands.controller;

import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.lands.dto.response.GroundRentResponse;
import com.automation.core.lands.service.service.GroundRentService;
import com.automation.util.constant.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lands/ground-rent")
@RequiredArgsConstructor
@CrossOrigin(
        origins = {
                "http://localhost:5174","https://bauchi-mda.netlify.app"
        },
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
        })
public class GroundRentController {

    private final GroundRentService grountRentService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadDocuments(@RequestParam String baNo,
                                                               @RequestParam String landNo,
                                                               @RequestParam String record,
                                                               @RequestParam Double rent,
                                                               @RequestParam(required = false) MultipartFile file) throws Exception {
        Map<String, String> response = grountRentService.submitform(baNo,landNo,record, rent , file);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/all")
//    public ResponseEntity<List<GroundRent>> getAllGroundRents() {
//        List<GroundRent> rent = grountRentService.getAllRentService();
//        return ResponseEntity.ok(rent);
//    }


    @GetMapping("/all")
    public ResponseEntity<AppResponse<List<GroundRentResponse>>> getAllAllocations() {
        List<GroundRentResponse> applyCofO = grountRentService.getCofOs();
        return ResponseEntity.ok().body(AppResponse.<List<GroundRentResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value()).data(applyCofO).error("").build());
    }

}
