package com.helmes.task.services;

import com.helmes.task.persistence.model.Sector;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SectorService {
    List<Sector> getAll();
}
