package pl.ubytes.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.transaction.Transactional;

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
	
	
	@RequestMapping(method = RequestMethod.POST, path = "/readout", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Readout addReadout(@RequestBody final Readout readout) {
		return readoutRepository.save(readout);		
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/readout/{sensorId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Readout addReadout(@RequestBody final Readout readout, @PathVariable Long sensorId) {
		readout.setSensor(sensorRepository.findOne(sensorId));
		return readoutRepository.save(readout);		
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/readout/{readoutId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Readout getReadout(@PathVariable Long readoutId) {
		return readoutRepository.findOne(readoutId);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/readouts/{sensorId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Readout> getReadouts(@PathVariable Long sensorId) {
		return readoutRepository.findBySensor(sensorRepository.findOne(sensorId));

	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/readouts/{sensorId}/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Readout> getReadoutsTimeRange(@PathVariable Long sensorId, @PathVariable String start, @PathVariable String end) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
		Timestamp startTime = null;
		Timestamp endTime = null;
		try {
			startTime  = new Timestamp(df.parse(start).getTime());
			endTime  = new Timestamp(df.parse(end).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return readoutRepository.findByTimestampBetweenAndSensor(startTime, endTime, sensorRepository.findOne(sensorId));

	}
	

}
