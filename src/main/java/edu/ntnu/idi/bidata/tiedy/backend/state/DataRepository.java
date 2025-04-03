package edu.ntnu.idi.bidata.tiedy.backend.state;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface DataRepository<T> {

  /**
   * Loads all entities from the data source (JSON)
   *
   * @return a stream of all entities
   */
  Stream<T> loadAll();

  /**
   * Gets an entity by its unique identifier.
   *
   * @param id the UUID of the entity
   * @return an Optional containing the entity if found
   */
  Optional<T> getById(UUID id);

  /**
   * Gets all entities managed by this repository. (Not from source)
   *
   * @return a collection of all entities
   */
  Collection<T> getAll();

  /**
   * Adds a new entity in the repository.
   *
   * @param entity the entity to update
   * @return the updated entity, or null if the entity doesn't exist
   */
  T add(T entity);

  /**
   * Updates an existing entity in the repository.
   *
   * @param entity the entity to update
   * @return the updated entity, or null if the entity doesn't exist
   */
  T update(T entity);

  /**
   * Removes an entity from the repository.
   *
   * @param id the unique identifier of the entity to remove
   * @return true if the entity was removed, false if it doesn't exist
   */
  boolean remove(UUID id);

  /** Deserializing back to source. */
  void saveChanges();

  /** Serialize from source */
  void refresh();
}
