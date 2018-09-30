package com.helmes.task.services.impl;

import com.helmes.task.persistence.model.Sector;
import com.helmes.task.persistence.repository.SectorRepository;
import com.helmes.task.services.SectorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorServiceImpl implements SectorService {
    private SectorRepository sectorRepository;

    public SectorServiceImpl(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    @Override
    public List<Sector> getAll() {
        return sectorRepository.findAll();
    }
}
