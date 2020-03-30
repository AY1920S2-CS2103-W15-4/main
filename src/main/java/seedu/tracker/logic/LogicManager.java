package seedu.tracker.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.tracker.commons.core.GuiSettings;
import seedu.tracker.commons.core.LogsCenter;
import seedu.tracker.logic.commands.general.Command;
import seedu.tracker.logic.commands.general.CommandResult;
import seedu.tracker.logic.commands.general.exceptions.CommandException;
import seedu.tracker.logic.parser.general.PetTrackerParser;
import seedu.tracker.logic.parser.general.exceptions.ParseException;
import seedu.tracker.model.Model;
import seedu.tracker.model.ReadOnlyPetTracker;
import seedu.tracker.model.pet.FoodCollection;
import seedu.tracker.model.pet.Pet;
import seedu.tracker.model.slot.Slot;
import seedu.tracker.storage.Storage;
import seedu.tracker.ui.DisplayItem;
import seedu.tracker.ui.DisplaySystemType;

/**
 * The main LogicManager of Pet Store Helper.
 */
public class LogicManager implements Logic {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final PetTrackerParser petTrackerParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        petTrackerParser = new PetTrackerParser(model);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = petTrackerParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.savePetTracker(model.getPetTracker());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyPetTracker getPetTracker() {
        return model.getPetTracker();
    }

    @Override
    public ObservableList<DisplayItem> getFilteredDisplayList() {
        return model.getFilteredDisplayList();
    }


    @Override
    public ObservableList<Pet> getFilteredPetList() {
        return model.getFilteredPetList();
    }

    @Override
    public ObservableList<Slot> getFilteredSlotList() {
        return model.getFilteredSlotList();
    }

    @Override
    public ObservableList<FoodCollection> getFilteredFoodCollectionList() {
        return model.getFilteredFoodCollectionList();
    }

    @Override
    public DisplaySystemType getDisplaySystemType() {
        return model.getCurrentDisplaySystemType();
    }

    @Override
    public Path getPetTrackerFilePath() {
        return model.getPetTrackerFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
