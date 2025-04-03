package edu.ntnu.idi.bidata.tiedy.backend.state;

import java.util.stream.Stream;

public interface DAO<T> {
  void save();

  Stream<T> load();

  void delete();

  void clear();

  void close();

  boolean isClosed();
}
