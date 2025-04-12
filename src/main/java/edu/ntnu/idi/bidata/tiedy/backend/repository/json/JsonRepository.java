package edu.ntnu.idi.bidata.tiedy.backend.repository.json;

import edu.ntnu.idi.bidata.tiedy.backend.io.json.JsonDAO;
import edu.ntnu.idi.bidata.tiedy.backend.repository.DataRepository;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * The base JSON implementation of the Repository design pattern, it includes all the necessary
 * methods to work with any concrete type. All JSON repositories should extend this class.
 *
 * @see JsonGroupRepository
 * @see JsonUserRepository
 * @see JsonTaskRepository
 * @author Nick Heggø
 * @version 2025.04.11
 * @param <T> the concrete type the Repository will be working with.
 */
public abstract class JsonRepository<T> implements DataRepository<T> {

  protected final JsonDAO<T> jsonDAO;
  private final Map<UUID, T> entities;
  protected final Function<T, UUID> idExtractor;

  /**
   * Constructs a new JsonRepository with the specified entity class and ID extractor function.
   *
   * @param entityClass the class of the entity type
   * @param idExtractor a function that extracts the UUID from an entity
   */
  protected JsonRepository(Class<T> entityClass, Function<T, UUID> idExtractor) {
    this.jsonDAO = new JsonDAO<>(entityClass);
    this.entities = new HashMap<>();
    this.idExtractor = idExtractor;
    refresh();
  }

  @Override
  public Stream<T> loadAll() {
    return jsonDAO.loadJsonAsStream();
  }

  @Override
  public Optional<T> getById(UUID id) {
    return Optional.ofNullable(entities.get(id));
  }

  @Override
  public Stream<T> getAll() {
    return entities.values().stream();
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
    jsonDAO.writeCollection(new HashSet<>(entities.values()));
  }

  @Override
  public void refresh() {
    entities.clear();
    loadAll().forEach(entity -> entities.put(idExtractor.apply(entity), entity));
  }
}
