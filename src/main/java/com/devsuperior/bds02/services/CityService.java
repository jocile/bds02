package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
}
