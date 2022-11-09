package org.example.service;

import java.util.List;

public interface MeasuresService {
    List<Float> getMeasuresByDay(String timestamp, Long deviceId);

}
