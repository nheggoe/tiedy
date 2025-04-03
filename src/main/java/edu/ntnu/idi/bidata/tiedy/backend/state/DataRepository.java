package edu.ntnu.idi.bidata.tiedy.backend.state;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface DataRepository<T> {

  Stream<T> loadAll();

  Optional<T> getById(UUID id);

  Collection<T> getAll();

  T add(T entity);

  T update(T entity);

  boolean remove(UUID id);

  void saveChanges();

  void refresh();
}
