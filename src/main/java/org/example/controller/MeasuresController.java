package org.example.controller;

import org.example.dto.MeasuresDTO;
import org.example.service.MeasuresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/measures")
public class MeasuresController {

    @Autowired
    private MeasuresService measuresService;

    @GetMapping("/day/{timestamp}/{id}")
    public ResponseEntity<List<Float>> getMeasuresByDay(@PathVariable("timestamp") String timestamp, @PathVariable("id") Long id) {
        return new ResponseEntity<>(measuresService.getMeasuresByDay(timestamp, id), HttpStatus.OK);
    }





}
