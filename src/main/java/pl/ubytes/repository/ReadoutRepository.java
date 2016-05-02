package pl.ubytes.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.ubytes.entity.Readout;
import pl.ubytes.entity.Sensor;

public interface ReadoutRepository extends CrudRepository<Readout, Long>  {
	
	List<Readout> findBySensor(Sensor sensor);
	
	List<Readout> findByTimestampBetweenAndSensor(Timestamp start, Timestamp end, Sensor sensor);

}
