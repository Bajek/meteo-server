package pl.ubytes.entity;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Readout  {

	@PrePersist
	void recievedAt() {
		this.timestamp = Timestamp.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Timestamp timestamp;
	private Double value;

	@ManyToOne(optional = false)
	private Sensor sensor;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Voltage voltage;

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@JsonIgnore
	public Sensor getSensor() {
		return sensor;
	}

	@JsonProperty
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Voltage getVoltage() {
		return voltage;
	}

	public void setVoltage(Voltage voltage) {
		this.voltage = voltage;
	}

}
