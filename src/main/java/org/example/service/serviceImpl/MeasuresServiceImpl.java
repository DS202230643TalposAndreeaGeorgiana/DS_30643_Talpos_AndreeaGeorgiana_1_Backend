package org.example.service.serviceImpl;

import org.example.model.Device;
import org.example.model.Measures;
import org.example.repository.DeviceRepository;
import org.example.service.MeasuresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeasuresServiceImpl implements MeasuresService {
    @Autowired
    private DeviceRepository deviceRepository;

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

}
