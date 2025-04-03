package edu.ntnu.idi.bidata.tiedy.backend.state.repository;

import edu.ntnu.idi.bidata.tiedy.backend.state.JsonService;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class JsonRepository<T> implements DataRepository<T> {

  protected final JsonService<T> jsonService;
  private final Map<UUID, T> entities;
  protected final Function<T, UUID> idExtractor;

  /**
   * Constructs a new JsonRepository with the specified entity class and ID extractor function
   *
   * @param entityClass the class of the entity type
   * @param idExtractor a function that extracts the UUID from an entity
   */
  protected JsonRepository(Class<T> entityClass, Function<T, UUID> idExtractor) {
    this.jsonService = new JsonService<>(entityClass);
    this.entities = new HashMap<>();
    this.idExtractor = idExtractor;
    refresh();
  }

  @Override
  public Stream<T> loadAll() {
    return jsonService.loadJsonAsStream();
  }

  @Override
  public Optional<T> getById(UUID id) {
    return Optional.ofNullable(entities.get(id));
  }

  @Override
  public Collection<T> getAll() {
    return entities.values();
  }

  @Override
  public T add(T entity) {
    UUID id = idExtractor.apply(entity);
    entities.put(id, entity);
    return entity;
  }

  @Override
  public T update(T entity) {
    UUID id = idExtractor.apply(entity);
    return entities.computeIfPresent(id, (k, v) -> entity);
  }

  @Override
  public boolean remove(UUID id) {
    return entities.remove(id) != null;
  }

  @Override
  public void saveChanges() {
    jsonService.writeCollection(new HashSet<>(entities.values()));
  }

  @Override
  public void refresh() {
    entities.clear();
    loadAll().forEach(entity -> entities.put(idExtractor.apply(entity), entity));
  }
}
