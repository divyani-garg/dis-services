package sgsits.cse.dis.infrastructure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgsits.cse.dis.infrastructure.model.Infrastructure;
import sgsits.cse.dis.infrastructure.repo.InfrastructureRepository;

@Service("infrastructureService")
public class InfrastructureServiceImpl implements InfrastructureService {

	@Autowired
	private InfrastructureRepository infrastructureRepository;
	
	@Override
	public void save(Infrastructure infrastructure) {
		infrastructureRepository.save(infrastructure);
	}

	@Override
	public void update(Infrastructure infrastructure) {
	}

	@Override
	public void delete(Long id) {
		infrastructureRepository.deleteById(id);
	}

	@Override
	public List<Infrastructure> findByName(String name) {
		return infrastructureRepository.findByName(name);
	}

	@Override
	public List<Infrastructure> findAll() {
		return infrastructureRepository.findAll();
	}
}