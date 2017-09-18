package neowine;

import org.neo4j.driver.v1.Driver;

public class WineRegionService {

    private final Driver db;

    public WineRegionService(Driver db) {
      this.db = db;
    }

    public WineRegion wineRegionByName(String regionName) {
      WineRegion region = new Neo4jWineRegion(db, regionName);
      return region;
    }
}
