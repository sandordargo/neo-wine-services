import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.exceptions.NoSuchRecordException;
import org.neo4j.harness.junit.Neo4jRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WineRegionTest {

  @Rule
  public Neo4jRule neo4j = new Neo4jRule()
      .withFixture("CREATE (eger:WineRegion {name:'Eger'})-[:CONTAINS]->(egerSR:WineSubRegion {name:'Eger'}),"
                       + "(eger)-[:CONTAINS]->(matra:WineSubRegion {name:'Mátra'}),"
                       + "(eger)-[:CONTAINS]->(bukk:WineSubRegion {name:'Bükk'})");

  @Test
  public void shouldReturnExistingRegion() {
    try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      String expectedRegionName = "Eger";
      WineRegion region = new Neo4jWineRegion(driver, expectedRegionName);
      assertEquals(expectedRegionName, region.getName());
    }
  }

  @Test
  public void shouldFindSubRegions() {
    try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      String existingRegionName = "Eger";
      List<String> expectedSubregions = Arrays.asList("Eger", "Mátra", "Bükk");
      WineRegion region = new Neo4jWineRegion(driver, existingRegionName);
      assertTrue(expectedSubregions.containsAll(region.getContainedSubregions())
                 && region.getContainedSubregions().containsAll(expectedSubregions));
    }
  }

  @Test(expected = NoSuchRecordException.class)
  public void nonExistingRegionThrowsException() {
    try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      String nonExistingRegionName = "Tokaj";
      WineRegion region = new Neo4jWineRegion(driver, nonExistingRegionName);
      System.out.println(region.toString());
    }
  }

  @Test
  public void regionStringRepresentationShouldMatch() {
    try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      String expectedRegionDescription = "[name: Eger, id: 0, subregions: [Bükk, Eger, Mátra]]";
      WineRegion region = new Neo4jWineRegion(driver, "Eger");
      assertEquals(expectedRegionDescription, region.toString());
    }
  }
}
