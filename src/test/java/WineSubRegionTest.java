import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.exceptions.NoSuchRecordException;
import org.neo4j.harness.junit.Neo4jRule;

import neowine.Grape;
import neowine.Neo4jWineSubregion;
import neowine.WineSubregion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WineSubRegionTest {
  @Rule
  public Neo4jRule neo4j = new Neo4jRule().withFixture(FakeData.FAKE_DATA_CREATOR);

  @Test
  public void shouldReturnExistingSubregion() {
    try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      String expectedSubregionName = "Mátra";
      WineSubregion subregion = new Neo4jWineSubregion(driver, expectedSubregionName);
      assertEquals(expectedSubregionName, subregion.getName());
    }
  }

  @Test
  public void shouldFindGrapesGrowingAt() {
    try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      String existingSubregionName = "Eger";
      List<String> expectedGrapes = Arrays.asList("Merlot", "Pinot Noir", "Cabernet Franc");
      WineSubregion subregion = new Neo4jWineSubregion(driver, existingSubregionName);
      assertTrue(expectedGrapes.containsAll(grapesAsSetOfStringOfTheirNames(subregion.getGrapesGrownAt()))
                     && grapesAsSetOfStringOfTheirNames(subregion.getGrapesGrownAt()).containsAll(expectedGrapes));
    }
  }

  @Test
  public void shouldFindIncludingRegion() {
    try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      String existingSubregionName = "Mátra";
      WineSubregion subregion = new Neo4jWineSubregion(driver, existingSubregionName);
      assertEquals("Eger", subregion.getIncludingRegion().getName());
    }
  }

  @Test(expected = NoSuchRecordException.class)
  public void nonExistingSubregionThrowsException() {
    try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      String nonExistingSubregionName = "Csepel";
      WineSubregion subregion = new Neo4jWineSubregion(driver, nonExistingSubregionName);
      System.out.println(subregion.toString());
    }
  }

  @Test
  public void regionStringRepresentationShouldMatch() {
    try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      String expectedSubregionDescription = "[name: Eger, id: 1, including region: Eger, grapes: [Cabernet Franc, Merlot, Pinot Noir]]";
      WineSubregion subregion = new Neo4jWineSubregion(driver, "Eger");
      assertEquals(expectedSubregionDescription, subregion.toString());
    }
  }

  private Set<String> grapesAsSetOfStringOfTheirNames(Set<Grape> grapes) {
    return grapes.stream().map(grape -> grape.getName()).collect(Collectors.toSet());
  }

}
