package racket;

import java.io.IOException;
import java.nio.file.Path;

public interface FileReader {
    void loadFile(Path path) throws IOException, RacketSyntaxError;
}
