import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class Neo4jWineRegion implements WineRegion {
  private final Driver db;
  private final String name;

  public Neo4jWineRegion(Driver db, String name) {
    this.db = db;
    this.name = returnNameIfExistsInDatabase(name);
  }

  private String returnNameIfExistsInDatabase(String name) {
    String query = "MATCH (wr:WineRegion {name:{regionName}}) RETURN wr";
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("regionName", name);
    return getRegionAsInternalNode(query, parameters).asMap().get("name").toString();
  }

  @Override public long getId() {
    String query = "MATCH (wr:WineRegion {name:{regionName}}) RETURN wr";
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("regionName", this.name);
    return getRegionAsInternalNode(query, parameters).id();
  }

  private InternalNode getRegionAsInternalNode(String query, Map<String, Object> parameters) {
    try (Session session = db.session()) {
      StatementResult result = session.run(query, parameters);
      return (InternalNode) result.next().get(0).asObject();
    }
  }

  @Override public String getName() {
    return this.name;
  }

  @Override public Set<String> getContainedSubregions() {
    String query = "MATCH (wr:WineRegion {name:{regionName}})-[relationship:CONTAINS]->(wsr:WineSubRegion) RETURN wsr";
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("regionName", name);
    Set<String> results = new HashSet<>();
    try (Session session = db.session()) {
      StatementResult result = session.run(query, parameters);
      while (result.hasNext()) {
        InternalNode region = (InternalNode) result.next().get(0).asObject();
        results.add(region.asMap().get("name").toString());
      }
      return results;
    }
  }

  @Override
  public String toString() {
    List<String> sortedSubRegions = new ArrayList<>(getContainedSubregions());
    Collections.sort(sortedSubRegions);
    return "[name: " + this.name + ", id: " + this.getId() + ", subregions: " +  sortedSubRegions.toString() + "]";
  }
}
