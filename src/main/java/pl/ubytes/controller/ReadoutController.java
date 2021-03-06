package pl.ubytes.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.ubytes.entity.Readout;
import pl.ubytes.repository.ReadoutRepository;
import pl.ubytes.repository.SensorRepository;

@Transactional
@RestController
public class ReadoutController {

	@Autowired
	ReadoutRepository readoutRepository;
	
	@Autowired
	SensorRepository sensorRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReadoutController.class);
	
	@RequestMapping(method = RequestMethod.POST, path = "/readout", 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Readout addReadout(@RequestBody final Readout readout) {
		return readoutRepository.save(readout);		
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/readout/{sensorId}", 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Readout addReadout(@RequestBody final Readout readout, @PathVariable Long sensorId) {
		readout.setSensor(sensorRepository.findOne(sensorId));
		return readoutRepository.save(readout);		
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/readout/{readoutId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Readout getReadout(@PathVariable Long readoutId) {
		return readoutRepository.findOne(readoutId);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/readouts/{sensorId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Readout> getReadouts(@PathVariable Long sensorId, HttpServletRequest request) {
		LOGGER.info("Got readouts request for {} from {}", sensorId, request.getRemoteAddr());
		return readoutRepository.findBySensor(sensorRepository.findOne(sensorId));

	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/readouts/{sensorId}/{start}/{end}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Readout> getReadoutsTimeRange(@PathVariable Long sensorId, 
			@PathVariable String start, @PathVariable String end, HttpServletRequest request) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
		Timestamp startTime = null;
		Timestamp endTime = null;
		try {
			startTime  = new Timestamp(df.parse(start).getTime());
			endTime  = new Timestamp(df.parse(end).getTime());
		} catch (ParseException e) {
			//in case of an exception with parsing extract one last hour:
			endTime = Timestamp.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant());
			startTime = new Timestamp(endTime.getTime() - 60 * 60 * 1000);
			LOGGER.warn("Error with parsing time ranges: {}", e.getMessage());
		}
		LOGGER.info("Got readouts request for {} for date {} - {} from {}", sensorId, startTime, endTime, 
				request.getRemoteAddr());
		return readoutRepository.findByTimestampBetweenAndSensor(startTime, endTime, 
				sensorRepository.findOne(sensorId));

	}
	

}
