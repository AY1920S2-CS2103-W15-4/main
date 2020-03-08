package seedu.address.logic.petCommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.petCore.Messages;
import seedu.address.logic.generalCommands.Command;
import seedu.address.logic.generalCommands.CommandResult;
import seedu.address.logic.generalCommands.exceptions.CommandException;
import seedu.address.model.GeneralModel;
import seedu.address.model.PetModel;
import seedu.address.model.pet.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPetCommand extends Command {

    public static final String COMMAND_WORD = "findpet";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all pets whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindPetCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(GeneralModel model) throws CommandException {
        requireNonNull(model);
        if(model instanceof PetModel) {
            ((PetModel) model).updateFilteredPetList(predicate);
            return new CommandResult(
                    String.format(Messages.MESSAGE_PETS_LISTED_OVERVIEW, ((PetModel) model).getFilteredPetList().size()));
        } else {
            throw new CommandException("Something is wrong with model handling");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPetCommand // instanceof handles nulls
                && predicate.equals(((FindPetCommand) other).predicate)); // state check
    }
}
