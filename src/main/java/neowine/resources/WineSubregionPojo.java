package neowine.resources;

import java.util.HashSet;
import java.util.Set;

public class WineSubregionPojo {

  private long id;
  private String name;
  private Set<String> grapes;
  private String parentRegionName;

  public WineSubregionPojo() {
    this.id = -1;
    this.name = new String();
    this.grapes = new HashSet<>();
    this.parentRegionName = new String();
  }


  public WineSubregionPojo(long id, String name, Set<String> grapes, String parentRegionName) {
    this.id = id;
    this.name = name;
    this.grapes = grapes;
    this.parentRegionName = parentRegionName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<String> getGrapes() {
    return grapes;
  }

  public void setGrapes(Set<String> grapes) {
    this.grapes = grapes;
  }

  public String getParentRegionName() {
    return parentRegionName;
  }

  public void setParentRegionName(String parentRegionName) {
    this.parentRegionName = parentRegionName;
  }

}
