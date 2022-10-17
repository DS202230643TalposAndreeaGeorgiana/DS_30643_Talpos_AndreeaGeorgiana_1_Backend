package org.example.controller;

import org.example.model.Measures;
import org.example.service.serviceImpl.MeasuresServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/measures")
public class MeasuresController {

    @Autowired
    private MeasuresServiceImpl measuresServiceImpl;

    @PostMapping
    public Long createMeasure(@RequestBody Measures measure) {
        measuresServiceImpl.createMeasure(measure);
        return measure.getMeasureId();
    }

    @GetMapping
    public List<Measures> getAllMeasures() {
        return measuresServiceImpl.getAllMeasures();
    }

    @GetMapping("/{measureId}")
    public Measures getMeasureById(@PathVariable("measureId") Long measureId) {
        return measuresServiceImpl.getMeasureById(measureId);
    }

    @PutMapping("/{measureId}")
    public Measures updateMeasure(@RequestBody Measures measure, @PathVariable("measureId") Long measureId) {
        return measuresServiceImpl.updateMeasure(measure, measureId);
    }

    @DeleteMapping("/{measureId}")
    public void deleteMeasure(@PathVariable("measureId") Long measureId) {
        measuresServiceImpl.deleteMeasureById(measureId);
    }
}
