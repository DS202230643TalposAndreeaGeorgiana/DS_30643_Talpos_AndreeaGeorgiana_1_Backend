package org.example.controller;

import org.example.model.Measures;
import org.example.service.MeasuresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measures")
public class MeasuresController {

    @Autowired
    private MeasuresService measuresService;

    @PostMapping
    public Long createMeasure(@RequestBody Measures measure) {
        measuresService.createMeasure(measure);
        return measure.getId();
    }

    @GetMapping
    public List<Measures> getAllMeasures() {
        return measuresService.getAllMeasures();
    }

    @GetMapping("/{measureId}")
    public Measures getMeasureById(@PathVariable("measureId") Long measureId) {
        return measuresService.getMeasureById(measureId);
    }

    @PutMapping("/{measureId}")
    public Measures updateMeasure(@RequestBody Measures measure, @PathVariable("measureId") Long measureId) {
        return measuresService.updateMeasure(measure, measureId);
    }

    @DeleteMapping("/{measureId}")
    public void deleteMeasure(@PathVariable("measureId") Long measureId) {
        measuresService.deleteMeasureById(measureId);
    }
}
