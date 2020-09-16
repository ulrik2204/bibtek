package bibtek.json;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.gson.Gson;

import bibtek.core.BookEntry;
import com.google.gson.reflect.TypeToken;

/**
 *
 * Converts book entries to json format, which is then stored as a file in local storage
 *
 */
public final class StorageHandler {

    private static final Path PATH_TO_LOCAL_STORAGE = Paths.get("target/library.json");

    public StorageHandler() {

        if (!Files.exists(PATH_TO_LOCAL_STORAGE)) {

            try {
                Files.createFile(PATH_TO_LOCAL_STORAGE);
            } catch (IOException e) {
                System.err.println("Exception when creating library.json: " + e.getCause());
                e.printStackTrace();
            }

        }

    }

    public void storeBookEntries(final Set<BookEntry> bookEntries) throws IOException {

        final Gson gson = new Gson();
        final Writer writer = Files.newBufferedWriter(PATH_TO_LOCAL_STORAGE);
        gson.toJson(bookEntries, writer);
        writer.close();

    }

    public Set<BookEntry> fetchBookEntries() throws IOException {

        final Gson gson = new Gson();
        final Reader reader = Files.newBufferedReader(PATH_TO_LOCAL_STORAGE);
        final Set<BookEntry> bookEntries = gson.fromJson(reader, new TypeToken<Set<BookEntry>>() {}.getType());
        reader.close();

        return Objects.requireNonNullElseGet(bookEntries, HashSet::new);

    }

}
