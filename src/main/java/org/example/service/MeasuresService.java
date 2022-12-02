package org.example.service;

import org.example.dto.MeasuresDTO;

import java.sql.Timestamp;
import java.util.List;

public interface MeasuresService {
    List<Float> getMeasuresByDay(String timestamp, Long deviceId);
    List<Float> getMeasuresByHour(Timestamp timestamp, Long deviceId);
    MeasuresDTO createMeasure(MeasuresDTO measure);
    MeasuresDTO updateMeasure(MeasuresDTO measure);
    MeasuresDTO getCurrentMeasure(Timestamp timestamp, Long deviceId);

}
