package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityService {
  @Autowired
  private CityRepository repository;

  public List<CityDTO> findAll() {
    List<City> list = repository.findAll(Sort.by("name"));
    return list
      .stream()
      .map(x -> new CityDTO(x))
      .collect(java.util.stream.Collectors.toList());
  }

  @Transactional
  public CityDTO insert(CityDTO dto) {
    City entity = new City();
    entity.setName(dto.getName());
    entity = repository.save(entity);
    return new CityDTO(entity);
  }

  public void delete(Long id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Integrity violation");
    }
  }
}
