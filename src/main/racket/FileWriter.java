package racket;

import java.io.IOException;
import java.nio.file.Path;

public interface FileWriter {
    void writeFile(Path path, String content) throws IOException;
}
