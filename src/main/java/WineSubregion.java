import java.util.Set;

public interface WineSubregion {
  long getId();

  String getName();

  Set<String> getGrapesGrownAt();

  WineRegion getIncludingRegion();

}
