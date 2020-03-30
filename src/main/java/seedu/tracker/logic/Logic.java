package seedu.tracker.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.tracker.commons.core.GuiSettings;
import seedu.tracker.logic.commands.general.CommandResult;
import seedu.tracker.logic.commands.general.exceptions.CommandException;
import seedu.tracker.logic.parser.general.exceptions.ParseException;
import seedu.tracker.model.ReadOnlyPetTracker;
import seedu.tracker.model.pet.FoodCollection;
import seedu.tracker.model.pet.Pet;
import seedu.tracker.model.slot.Slot;
import seedu.tracker.ui.DisplayItem;
import seedu.tracker.ui.DisplaySystemType;

/**
 * API of the Logic component for Pet Store Helper
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the PetTracker.
     */
    ReadOnlyPetTracker getPetTracker();

    /**
     * Returns an unmodifiable view of the filtered list to be displayed.
     */
    ObservableList<DisplayItem> getFilteredDisplayList();

    ObservableList<Pet> getFilteredPetList();

    ObservableList<Slot> getFilteredSlotList();

    ObservableList<FoodCollection> getFilteredFoodCollectionList();

    DisplaySystemType getDisplaySystemType();

    /**
     * Returns the user prefs' pet tracker file path.
     */
    Path getPetTrackerFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
