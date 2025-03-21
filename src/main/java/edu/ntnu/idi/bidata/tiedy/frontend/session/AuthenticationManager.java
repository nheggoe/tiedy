package edu.ntnu.idi.bidata.tiedy.frontend.session;

import edu.ntnu.idi.bidata.tiedy.backend.util.FileUtil;
import java.io.File;
import java.io.IOException;

public class AuthenticationManager {
  File indexFile = new File("data/json/Index.json");

  // Map<String, String> userIndex = JsonMapper.getInstance().readValue();

  public void authenticate() throws IOException {
    FileUtil.ensureFileAndDirectoryExists(indexFile);
  }
}
