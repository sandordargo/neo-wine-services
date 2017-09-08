import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class Neo4jWineRegion implements WineRegion {
  private final Driver db;
  private String name;
  private Set<String> subregions;
  private long id;


  public Neo4jWineRegion(Driver db, String name) {
    this.db = db;
    this.subregions = new HashSet<>();
    initializeFieldsFromNeo4j(name);
  }

  private void initializeFieldsFromNeo4j(String name) {
    String query = "MATCH (wr:WineRegion {name:{regionName}})-[relationship:CONTAINS]->(wsr:WineSubRegion) "
        + "RETURN wr, wsr";
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("regionName", name);
    try (Session session = db.session()) {
      StatementResult result = session.run(query, parameters);
      Record record = result.next();
      InternalNode region = (InternalNode) record.get(0).asObject();
      this.name = region.asMap().get("name").toString();
      this.id = region.id();
      collectSubregions(result, record);
    }
  }

  private void collectSubregions(StatementResult result, Record record) {
    subregions.add(((InternalNode) record.get(1).asObject()).asMap().get("name").toString());
    while (result.hasNext()) {
      subregions.add(((InternalNode) result.next().get(1).asObject()).asMap().get("name").toString());
    }
  }

  @Override public long getId() {
    return this.id;
  }

  @Override public String getName() {
    return this.name;
  }

  @Override public Set<String> getContainedSubregions() {
    return subregions;
  }

  @Override
  public String toString() {
    List<String> sortedSubRegions = new ArrayList<>(getContainedSubregions());
    Collections.sort(sortedSubRegions);
    return "[name: " + this.getName() + ", id: " + this.getId() + ", subregions: " +  sortedSubRegions.toString() + "]";
  }
}
