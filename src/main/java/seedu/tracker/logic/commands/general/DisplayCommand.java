package seedu.tracker.logic.commands.general;

import static java.util.Objects.requireNonNull;

import seedu.tracker.commons.exceptions.IllegalValueException;
import seedu.tracker.logic.commands.general.exceptions.CommandException;
import seedu.tracker.model.Model;
import seedu.tracker.ui.DisplaySystemType;

/**
 * Displays the specified system.
 */
public class DisplayCommand extends Command {

    public static final String COMMAND_WORD = "display";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the display board to show the specified system.\n"
            + "Parameter: SYSTEM (must be p (pets) or s (schedule) or i (inventory)).\n"
            + "Example: display p";

    public static final String MESSAGE_SUCCESS = "Display changed to %s. \nShowing all.";

    public static final String ADDITIONAL_MESSAGE_INVENTORY = "Click on each item to view list breakdown.";

    public static final String MESSAGE_INVALID_SYSTEM_TYPE = "Invalid system type specified.";

    private final DisplaySystemType type;

    public DisplayCommand(DisplaySystemType type) {
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.changeDisplaySystem(type);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_INVALID_SYSTEM_TYPE);
        }
        String message = String.format(getMessageSuccess(), type);
        return new CommandResult(message, false, false, true, false);
    }

    public String getMessageSuccess() {
        if (type.equals(DisplaySystemType.INVENTORY)) {
            return MESSAGE_SUCCESS + "\n" + ADDITIONAL_MESSAGE_INVENTORY;
        } else {
            return MESSAGE_SUCCESS;
        }
    }
}
