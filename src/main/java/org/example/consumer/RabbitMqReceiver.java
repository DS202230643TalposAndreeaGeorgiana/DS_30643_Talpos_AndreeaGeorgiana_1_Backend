package org.example.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.dto.MeasuresDTO;
import org.example.model.Device;
import org.example.model.Measures;
import org.example.repository.DeviceRepository;
import org.example.repository.UserRepository;
import org.example.service.MeasuresService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class RabbitMqReceiver implements RabbitListenerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqReceiver.class);
    private ObjectMapper objectMapper;
    @Autowired
    private MeasuresService measuresService;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SimpMessagingTemplate template;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(String measuresDTO) {
        objectMapper = new ObjectMapper();
        try {
            MeasuresDTO measureToSave = objectMapper.readValue(measuresDTO, MeasuresDTO.class);
            Optional<Device> device = deviceRepository.findById(measureToSave.getDeviceId());
            if (device.isPresent()) {
                String username = device.get().getUser().getUsername();
                System.out.println("yesyes");
                System.out.println(username);
                Float energyConsumption = this.computeEnergyConsumption(measureToSave.getTimestamp(), measureToSave.getDeviceId()) + measureToSave.getEnergyConsumption();
                MeasuresDTO hourlyConsumption = new MeasuresDTO();
                MeasuresDTO currentMeasure = this.measuresService.getCurrentMeasure(measureToSave.getTimestamp(), measureToSave.getDeviceId());
                if(currentMeasure!=null) {
                    hourlyConsumption.setMeasureId(currentMeasure.getMeasureId());
                    hourlyConsumption.setEnergyConsumption(energyConsumption);
                    this.measuresService.updateMeasure(currentMeasure);
                }
                hourlyConsumption.setEnergyConsumption(energyConsumption);
                hourlyConsumption.setTimestamp(measureToSave.getTimestamp());
                hourlyConsumption.setDeviceId(measureToSave.getDeviceId());
                this.measuresService.createMeasure(hourlyConsumption);
                if (energyConsumption > device.get().getMaximumHourlyConsumption()) {
                    System.out.println("EXCEEDED!!!!!");
                    this.template.convertAndSend("/topic/message/" + username, "Exceeded!!");
                } else {
                    this.template.convertAndSend("/topic/message/" + username, measureToSave.getEnergyConsumption());
                    logger.info("Measures Details Received is.. " + measureToSave);
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Float computeEnergyConsumption(Timestamp timestamp, Long deviceId) {
        List<Float> measures =  this.measuresService.getMeasuresByHour(timestamp, deviceId);
        Float result = 0f;
        for (Float measure : measures) {
            result+=measure;
        }
        return result;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }
}