package neowine;

import org.neo4j.driver.v1.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  private Driver driver;

  @Autowired
  public Controller(Driver driver) {
    this.driver = driver;
  }

  @RequestMapping(value = "/wineregions/{regionName}", method = RequestMethod.GET, produces = "text/plain")
  public String getWineRegionByName(@PathVariable String regionName) {
    WineRegionService service = new WineRegionService(driver);
    WineRegion retrievedWineRegion = service.wineRegionByName(regionName);
    System.out.println(retrievedWineRegion);
    return retrievedWineRegion.toString();
  }

}
