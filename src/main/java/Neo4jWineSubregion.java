import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class Neo4jWineSubregion implements WineSubregion {
  
  private final Driver db;
  private String name;
  private Set<Grape> grapes;
  private WineRegion parentRegion;
  private String parentRegionName;
  private long id;

  public Neo4jWineSubregion(Driver db, String name) {
    this.db = db;
    this.grapes = new HashSet<>();
    initializeFieldsFromNeo4j(name);
  }

  private void initializeFieldsFromNeo4j(String name) {
    String query = "MATCH (wr:WineRegion)-[:CONTAINS]->(wsr:WineSubRegion {name:{subregionName}}) OPTIONAL MATCH (wsr)<-[:GROWS_AT*0..1]-(grape:Grape) "
        + "RETURN wr, wsr, grape";
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("subregionName", name);
    try (Session session = db.session()) {
      StatementResult result = session.run(query, parameters);
      Record record = result.next();
      InternalNode region = (InternalNode) record.get(0).asObject();
      this.parentRegionName = region.asMap().get("name").toString();
      InternalNode subregion = (InternalNode) record.get(1).asObject();
      this.name = subregion.asMap().get("name").toString();
      this.id = subregion.id();
      collectGrapes(result, record);
    }
  }

  private void collectGrapes(StatementResult result, Record record) {
    if (record.get(2).asObject() != null) {
      String grapeName = ((InternalNode) record.get(2).asObject()).asMap().get("name").toString();
      grapes.add(new Neo4jGrape(this.db, grapeName));
      while (result.hasNext()) {
        grapeName = ((InternalNode) result.next().get(2).asObject()).asMap().get("name").toString();
        grapes.add(new Neo4jGrape(this.db, grapeName));
      }
    }
  }

  @Override
  public long getId() {
    return this.id;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Set<Grape> getGrapesGrownAt() {
    return this.grapes;
  }

  @Override
  public WineRegion getIncludingRegion() {
    if (this.parentRegion == null) {
      this.parentRegion = new Neo4jWineRegion(this.db, this.parentRegionName);
    }
    return this.parentRegion;
  }

  @Override
  public String toString() {
    List<String> sortedGrapes = new ArrayList<>(getGrapesGrownAt().stream()
                                                                  .map(Grape::getName)
                                                                  .collect(Collectors.toList()));
    Collections.sort(sortedGrapes);
    return "[name: " + this.getName() + ", id: " + this.getId() + ", including region: "
        + getIncludingRegion().getName() + ", grapes: " +  sortedGrapes.toString() + "]";
  }
}
