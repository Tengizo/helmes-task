package com.helmes.task.rest.controllers;

import com.helmes.task.config.AppConstants;
import com.helmes.task.persistence.model.Sector;
import com.helmes.task.services.SectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin(origins = AppConstants.CLIENT_URL_DEV)
@RestController
@RequestMapping(value = "/api/sectors")
public class SectorController {
    private SectorService sectorService;

    public SectorController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Sector>> getAll() {
        return ResponseEntity.ok().body(sectorService.getAll());
    }
}
