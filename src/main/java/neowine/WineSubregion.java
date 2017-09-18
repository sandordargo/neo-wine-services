package neowine;

import java.util.Set;

public interface WineSubregion {
  long getId();

  String getName();

  Set<Grape> getGrapesGrownAt();

  WineRegion getIncludingRegion();

}
