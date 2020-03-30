package seedu.tracker.model;

import java.nio.file.Path;

import seedu.tracker.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {
    GuiSettings getGuiSettings();

    Path getPetTrackerFilePath();
}

