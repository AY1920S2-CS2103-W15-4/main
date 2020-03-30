package seedu.tracker.logic.commands.pet;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.tracker.commons.core.Messages;
import seedu.tracker.commons.core.index.Index;
import seedu.tracker.logic.commands.general.Command;
import seedu.tracker.logic.commands.general.CommandResult;
import seedu.tracker.logic.commands.general.exceptions.CommandException;
import seedu.tracker.model.Model;
import seedu.tracker.model.pet.Pet;

/**
 * Deletes a pet identified using it's displayed index from the pet tracker.
 */
public class DeletePetCommand extends Command {

    public static final String COMMAND_WORD = "deletepet";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the pet identified by the index number used in the displayed pet list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PET_SUCCESS = "Deleted Pet: %1$s";

    private final Index targetIndex;

    public DeletePetCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Pet> lastShownList = model.getFilteredPetList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
        }

        Pet petToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePet(petToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PET_SUCCESS, petToDelete));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePetCommand // instanceof handles nulls
                && targetIndex.equals(((DeletePetCommand) other).targetIndex)); // state check
    }
}
