package seedu.tracker.logic.commands.slot;

import static java.util.Objects.requireNonNull;
import static seedu.tracker.logic.parser.general.CliSyntax.PREFIX_DATETIME;
import static seedu.tracker.logic.parser.general.CliSyntax.PREFIX_DURATION;
import static seedu.tracker.logic.parser.general.CliSyntax.PREFIX_NAME;

import seedu.tracker.logic.commands.general.Command;
import seedu.tracker.logic.commands.general.CommandResult;
import seedu.tracker.model.Model;
import seedu.tracker.model.slot.Slot;

/**
 * Adds a slot to the schedule.
 */
public class AddSlotCommand extends Command {

    public static final String COMMAND_WORD = "addslot";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a pet to the pet store helper. "
            + "Parameters: "
            + PREFIX_NAME + "PETNAME "
            + PREFIX_DATETIME + "DATETIME "
            + PREFIX_DURATION + "DURATION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Coco "
            + PREFIX_DATETIME + "16/11/2020 1300 "
            + PREFIX_DURATION + "90 ";

    public static final String MESSAGE_SUCCESS = "New slot added: %1$s";

    private final Slot slotToAdd;

    /**
     * Creates an AddSlotCommand to add the specified {@code slot}
     */
    public AddSlotCommand(Slot slot) {
        requireNonNull(slot);
        slotToAdd = slot;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.addSlot(slotToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, slotToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddSlotCommand // instanceof handles nulls
                && slotToAdd.equals(((AddSlotCommand) other).slotToAdd));
    }
}
