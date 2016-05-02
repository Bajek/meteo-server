package pl.ubytes.repository;

import org.springframework.data.repository.CrudRepository;

import pl.ubytes.entity.Sensor;


public interface SensorRepository extends CrudRepository<Sensor, Long> {

}
