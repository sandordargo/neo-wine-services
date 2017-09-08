import java.util.Set;

public class ConstWineRegion implements WineRegion {

  private final WineRegion origin;
  private final long id;
  private final Set<String> subregions;
  private final String name;

  public ConstWineRegion(WineRegion origin, long id, Set<String> subregions) {
    this.origin = origin;
    this.name = origin.getName();
    this.id = id;
    this.subregions = subregions;
  }

  @Override public long getId() {
    return this.id;
  }

  @Override public String getName() {
    return this.name;
  }

  @Override public Set<String> getContainedSubregions() {
    return this.subregions;
  }
}
