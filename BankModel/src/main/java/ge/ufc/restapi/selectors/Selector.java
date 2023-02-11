package ge.ufc.restapi.selectors;

import java.util.Set;

public interface Selector<T> {
  Set<T> select();
}
