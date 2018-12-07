package sgsits.cse.dis.infrastructure.service;

import java.util.List;

import sgsits.cse.dis.infrastructure.model.Infrastructure;

public interface InfrastructureService {
	public void save(Infrastructure infrastructure);
	public void update(Infrastructure infrastructure);
	public void delete(Long id);
	public List<Infrastructure> findByName(String name); 
	public List<Infrastructure> findAll();
}
