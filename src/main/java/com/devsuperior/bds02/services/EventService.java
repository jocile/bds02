package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {
  @Autowired
  private EventRepository repository;

  public List<EventDTO> findAll() {
    List<Event> list = repository.findAll(Sort.by("name"));
    return list
      .stream()
      .map(x -> new EventDTO(x))
      .collect(java.util.stream.Collectors.toList());
  }

  @Transactional
  public EventDTO insert(EventDTO dto) {
    Event entity = new Event();
    entity.setName(dto.getName());
    entity = repository.save(entity);
    return new EventDTO(entity);
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

  @Transactional
  public EventDTO update(Long id, EventDTO dto) {
    try {
      Event entity = repository.getOne(id);
      entity.setName(dto.getName());
      entity.setDate(dto.getDate());
      entity.setUrl(dto.getUrl());
      entity.setCity(new City(dto.getCityId(), null));

      entity = repository.save(entity);
      return new EventDTO(entity);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    }
  }
}
