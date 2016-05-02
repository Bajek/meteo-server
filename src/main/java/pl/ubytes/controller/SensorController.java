package pl.ubytes.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.ubytes.entity.Sensor;
import pl.ubytes.repository.SensorRepository;

@Transactional
@RestController
@RequestMapping("/sensor")
@ExposesResourceFor(Sensor.class)
public class SensorController {
	
	@Autowired
	SensorRepository sensorRepository;
	
	@RequestMapping(method = RequestMethod.GET, path = "/{sensorId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Sensor getSensor(@PathVariable Long sensorId) {
		return sensorRepository.findOne(sensorId);
	}

}
