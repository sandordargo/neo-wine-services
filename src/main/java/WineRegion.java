import java.util.Set;

public interface WineRegion {

  long getId();

  String getName();

  Set<WineSubregion> getContainedSubregions();
  
}
