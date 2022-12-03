package org.example.service.serviceImpl;

import org.example.controller.handlers.exceptions.ResourceNotFoundException;
import org.example.dto.DeviceDTO;
import org.example.dto.MeasuresDTO;
import org.example.model.Device;
import org.example.model.Measures;
import org.example.repository.DeviceRepository;
import org.example.repository.MeasureRepository;
import org.example.service.MeasuresService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MeasuresServiceImpl implements MeasuresService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private MeasureRepository measureRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Float> getMeasuresByDay(String timestamp, Long deviceId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        final LocalDate localDate = LocalDate.parse(timestamp.replaceAll("_", "/"), df);
        Optional<Device> device = deviceRepository.findById(deviceId);
        List<Measures> measures = new ArrayList<>();
        if (device.isPresent()) {
            measures = device.get()
                    .getMeasures()
                    .stream()
                    .filter(x -> x.getTimestamp().toLocalDateTime().getDayOfMonth() == localDate.getDayOfMonth())
                    .collect(Collectors.toList());
        }
        return measures.stream()
                .map(Measures::getEnergyConsumption)
                .collect(Collectors.toList());
    }

    public List<Float> getMeasuresByHour(Timestamp timestamp, Long deviceId) {
        Optional<Device> device = deviceRepository.findById(deviceId);
        List<Measures> measures = new ArrayList<>();
        if (device.isPresent()) {
            measures = device.get()
                    .getMeasures()
                    .stream()
                    .filter(x -> x.getTimestamp().getDay() == timestamp.getDay())
                    .filter(x -> x.getTimestamp().getHours() == timestamp.getHours())
                    .collect(Collectors.toList());
        }
        return measures.stream()
                .map(Measures::getEnergyConsumption)
                .collect(Collectors.toList());
    }

    public MeasuresDTO getCurrentMeasure(Timestamp timestamp, Long deviceId) {
        Optional<Device> device = deviceRepository.findById(deviceId);
        List<Measures> measures = new ArrayList<>();
        if (device.isPresent()) {
            measures = device.get()
                    .getMeasures()
                    .stream()
                    .filter(x -> x.getTimestamp().getDay() == timestamp.getDay())
                    .filter(x -> x.getTimestamp().getHours() == timestamp.getHours())
                    .collect(Collectors.toList());
        }
        if(!measures.isEmpty()) {
            return this.modelMapper.map(measures.get(0), MeasuresDTO.class);
        } else {
            return null;
        }
    }

    public MeasuresDTO createMeasure(MeasuresDTO measure) {
        Measures measureToCreate = modelMapper.map(measure, Measures.class);
        return modelMapper.map(measureRepository.save(measureToCreate), MeasuresDTO.class);
    }

    public MeasuresDTO updateMeasure(MeasuresDTO measure) {
        Optional<Measures> measureToUpdate = measureRepository.findById(measure.getMeasureId());

        if (measureToUpdate.isEmpty()) {
            throw new ResourceNotFoundException(Measures.class.getSimpleName() + " with ID " + measure.getMeasureId());
        }

        measureToUpdate.get().setDevice(this.deviceRepository.findById(measure.getDeviceId()).get());
        measureToUpdate.get().setEnergyConsumption(measure.getEnergyConsumption());
        measureToUpdate.get().setTimestamp(measure.getTimestamp());
        return modelMapper.map(measureRepository.save(measureToUpdate.get()), MeasuresDTO.class);
    }

}
