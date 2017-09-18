import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.harness.junit.Neo4jRule;

import neowine.WineSubregionService;

import static org.junit.Assert.assertEquals;

public class WineSubRegionServiceTest {
  @Rule
  public Neo4jRule neo4j = new Neo4jRule().withFixture(FakeData.FAKE_DATA_CREATOR);

  @Test
  public void shouldReturnExistingSubregion() {
    try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      String expectedSubregionName = "Mátra";
      WineSubregionService service = new WineSubregionService(driver);
      assertEquals(expectedSubregionName, service.wineSubregionByName(expectedSubregionName).getName());
    }
  }

}
