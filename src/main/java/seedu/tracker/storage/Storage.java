package seedu.tracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.tracker.commons.exceptions.DataConversionException;
import seedu.tracker.model.ReadOnlyPetTracker;
import seedu.tracker.model.ReadOnlyUserPrefs;
import seedu.tracker.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends PetTrackerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getPetTrackerFilePath();

    @Override
    Optional<ReadOnlyPetTracker> readPetTracker() throws DataConversionException, IOException;

    @Override
    void savePetTracker(ReadOnlyPetTracker petTracker) throws IOException;

}
