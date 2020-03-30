package seedu.tracker.logic.parser.pet;

import seedu.tracker.commons.core.Messages;
import seedu.tracker.commons.core.index.Index;
import seedu.tracker.logic.commands.pet.DeletePetCommand;
import seedu.tracker.logic.parser.general.Parser;
import seedu.tracker.logic.parser.general.ParserUtil;
import seedu.tracker.logic.parser.general.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeletePetCommand object
 */
public class DeletePetParser implements Parser<DeletePetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePetCommand
     * and returns a DeletePetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePetCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePetCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeletePetCommand.MESSAGE_USAGE), pe);
        }
    }
}
