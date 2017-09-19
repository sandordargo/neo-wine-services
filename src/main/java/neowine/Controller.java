package neowine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import neowine.resources.GrapePojo;
import neowine.resources.WineRegionPojo;
import neowine.resources.WineSubregionPojo;

@RestController
public class Controller {

  private WineRegionService wineRegionService;
  private WineSubregionService wineSubregionService;
  private GrapeService grapeService;

  @Autowired
  public Controller(WineRegionService wineRegionService, WineSubregionService wineSubregionService,
                    GrapeService grapeService) {
    this.wineRegionService = wineRegionService;
    this.wineSubregionService = wineSubregionService;
    this.grapeService = grapeService;
  }

  @RequestMapping(value = "/wineregions/{regionName}", method = RequestMethod.GET, produces = "application/json")
  public WineRegionPojo getWineRegionByName(@PathVariable String regionName) {
    WineRegion retrievedWineRegion = wineRegionService.wineRegionByName(regionName);
    return retrievedWineRegion.asPojo();
  }

  @RequestMapping(value = "/winesubregions/{subregionName}", method = RequestMethod.GET, produces = "application/json")
  public WineSubregionPojo getWineSubregionByName(@PathVariable String subregionName) {
    WineSubregion retrievedWineSubregion = wineSubregionService.wineSubregionByName(subregionName);
    return retrievedWineSubregion.asPojo();
  }

  @RequestMapping(value = "/grapes/{grapeName}", method = RequestMethod.GET, produces = "application/json")
  public GrapePojo getGrapeByName(@PathVariable String grapeName) {
    Grape retrievedGrape = grapeService.getGrapeByName(grapeName);
    return retrievedGrape.asPojo();
  }

}
