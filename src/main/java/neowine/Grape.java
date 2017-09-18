package neowine;

import java.util.Set;

public interface Grape {
  long getId();

  String getName();

  Set<WineSubregion> getProducingSubregions();
}
