import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.harness.junit.Neo4jRule;

import static org.junit.Assert.assertEquals;

public class GrapeServiceTest {

  @Rule
  public Neo4jRule neo4j = new Neo4jRule().withFixture(FakeData.FAKE_DATA_CREATOR);
  
  @Test
  public void shouldReturnExistingRegion() {
    try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      String expectedGrapeName = "Merlot";
      GrapeService service = new GrapeService(driver);
      assertEquals(expectedGrapeName, service.grapeByName(expectedGrapeName).getName());
    }
  }
}
