package seedu.tracker.logic.parser.pet;

import java.util.Arrays;

import seedu.tracker.commons.core.Messages;
import seedu.tracker.logic.commands.pet.FindPetCommand;
import seedu.tracker.logic.parser.general.Parser;
import seedu.tracker.logic.parser.general.exceptions.ParseException;
import seedu.tracker.model.pet.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPetCommand object
 */
public class FindPetParser implements Parser<FindPetCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindPetCommand
     * and returns a FindPetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPetCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindPetCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindPetCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
