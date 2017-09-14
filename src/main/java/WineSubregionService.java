import org.neo4j.driver.v1.Driver;

public class WineSubregionService {

    private final Driver db;

    public WineSubregionService(Driver db) {
      this.db = db;
    }

    public WineSubregion wineSubregionByName(String subregionName) {
      WineSubregion subregion = new Neo4jWineSubregion(db, subregionName);
      return subregion;
    }
}
