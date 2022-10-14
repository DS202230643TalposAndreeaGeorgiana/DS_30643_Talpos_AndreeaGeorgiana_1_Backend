package org.example.service;

import org.example.model.Measures;
import org.example.repository.MeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasuresService {

    @Autowired
    private MeasureRepository measureRepository;

    public List<Measures> getAllMeasures() {
        return (List<Measures>) measureRepository.findAll();
    }

    public Measures getMeasureById(Long id) {
        if(measureRepository.findById(id).isPresent()) {
            return measureRepository.findById(id).get();
        }
        return null;
    }

    public Measures updateMeasure(Measures measure, Long id) {
        Measures measureToUpdate = measureRepository.findById(id).get();

        if(!measure.getEnergyConsumption().isNaN() && !measure.getEnergyConsumption().isInfinite()) {
            measureToUpdate.setEnergyConsumption(measure.getEnergyConsumption());
        }

        measureToUpdate.setTimestamp(measure.getTimestamp());

        return measureRepository.save(measureToUpdate);
    }

    public void createMeasure(Measures measure) {
        measureRepository.save(measure);
    }

    public void deleteMeasureById(Long id) {
        measureRepository.deleteById(id);
    }
}
