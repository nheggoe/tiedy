package edu.ntnu.idi.bidata.tiedy.backend.state.repository;

import edu.ntnu.idi.bidata.tiedy.backend.state.JsonService;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

public class JsonRepository<T> implements DataRepository<T> {

  protected final JsonService<T> jsonService;
  protected final Map<UUID, T> entities;
  protected final Function<T, UUID> idExtractor;

  protected JsonRepository(Class<T> targetClass, Function<T, UUID> idExtractor) {
    this.jsonService = new JsonService<>(targetClass);
    this.entities = new HashMap<>();
    this.idExtractor = idExtractor;
  }

  @Override
  public Stream<T> loadAll() {
    return Stream.empty();
  }

  @Override
  public Optional<T> getById(UUID id) {
    return Optional.empty();
  }

  @Override
  public Collection<T> getAll() {
    return List.of();
  }

  @Override
  public T add(T entity) {
    return null;
  }

  @Override
  public T update(T entity) {
    return null;
  }

  @Override
  public boolean remove(UUID id) {
    return false;
  }

  @Override
  public void saveChanges() {}

  @Override
  public void refresh() {}
}
