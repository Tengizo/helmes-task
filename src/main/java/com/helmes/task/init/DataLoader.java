package com.helmes.task.init;

import com.helmes.task.persistence.model.Sector;
import com.helmes.task.persistence.repository.SectorRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class DataLoader implements ApplicationRunner {
    /*CONSTANTS*/
    private final String DATA_FILE_PATH = "sectors/index.html";
    private final String PARSE_CHARSET = "UTF-8";

    /*Beans*/
    private SectorRepository sectorRepository;

    public DataLoader(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    public void run(ApplicationArguments args) {
        loadSectors();

    }

    private void loadSectors() {
        if (sectorRepository.count() > 0) {
            log.info("Sectors are already loaded");
            return;
        }
        log.info("Starting sectors loading");
        List<Sector> sectors = parseHtml();
        this.sectorRepository.saveAll(sectors);
        log.info("all sectors are loaded");
    }

    private List<Sector> parseHtml() {
        Optional<Elements> optionsWrapper = getOptions();
        if (!optionsWrapper.isPresent()) return new ArrayList<>();
        Elements options = optionsWrapper.get();
        return getSectorList(options);

    }

    /**
     * @param options Jsoup Elements which are needed to get sectors
     * @return List<Sector> result of parsing, taking into account, hierarchy based on indentation of options
     * <p>
     * sectors taken from options, are saved in Sector list, with hierarchy based on indentation of options
     * sectors which have less whitespaces in option name are parents of sectors which have more whitespaces
     */
    private List<Sector> getSectorList(Elements options) {
        List<Sector> sectorList = new ArrayList<>();
        Sector lastParentSector = null;
        Sector lastSector = null;
        for (Element option : options) {
            String optionText = option.text();
            int currentSectorSpaceCount = getFirstSpaceCount(optionText);
            Sector currentSector = Sector.builder()
                    .name(optionText)
                    .children(new ArrayList<>())
                    .build();
            //  if option does't have any whitespaces, it is root on sectors hierarchy
            if (currentSectorSpaceCount == 0) {
                lastParentSector = currentSector;
                sectorList.add(currentSector);
            } else {
                assert lastParentSector != null;
                int lastSectorSpaceCount = getFirstSpaceCount(lastSector.getName());
                if (lastSectorSpaceCount < currentSectorSpaceCount) {
                    // if previous sector has less whitespaces than current one
                    // means that current sector is child of previous sector
                    lastParentSector = lastSector;
                    currentSector.setParent(lastParentSector);
                    lastParentSector.getChildren().add(currentSector);
                } else if (lastSectorSpaceCount > currentSectorSpaceCount) {
                    // if current sector has less whitespaces than previous on
                    // we need to search in parent sectors for one which has
                    // less whitespaces than current sector has
                    do {
                        lastParentSector = lastParentSector.getParent();
                    }
                    while (getFirstSpaceCount(lastParentSector.getName()) >= currentSectorSpaceCount);
                    currentSector.setParent(lastParentSector);
                    lastParentSector.getChildren().add(currentSector);
                } else {
                    // if current sector's whitespaces count and previous sectors whitespaces count are
                    // equal this means that current secotor and previous sector has same parent
                    currentSector.setParent(lastParentSector);
                    lastParentSector.getChildren().add(currentSector);
                }
            }
            lastSector = currentSector;
        }
        return sectorList;
    }

    private int getFirstSpaceCount(String text) {
        int spaceCount = 0;
        for (char character : text.toCharArray()) {
            if ((int) character != 160) return spaceCount;
            spaceCount++;
        }
        return spaceCount;
    }

    private Optional<Elements> getOptions() {
        Optional<File> dataFile = getDataFile();
        if (!dataFile.isPresent()) {
            return Optional.empty();
        }
        try {
            Document document = Jsoup.parse(dataFile.get(), PARSE_CHARSET);
            Element select = document.getElementsByTag("select").get(0);
            return Optional.of(select.getElementsByTag("option"));
        } catch (IOException e) {
            log.error("Unable to parse HTML of data file", e);
            return Optional.empty();
        }
    }

    private Optional<File> getDataFile() {
        Resource resource = new ClassPathResource(DATA_FILE_PATH);
        try {
            return Optional.of(resource.getFile());
        } catch (IOException e) {

            log.error(" -- unable to open file" + DATA_FILE_PATH, e);
            return Optional.empty();
        }
    }
}


