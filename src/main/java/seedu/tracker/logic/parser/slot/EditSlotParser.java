package seedu.tracker.logic.parser.slot;

import static java.util.Objects.requireNonNull;
import static seedu.tracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tracker.logic.parser.general.CliSyntax.PREFIX_DATETIME;
import static seedu.tracker.logic.parser.general.CliSyntax.PREFIX_DURATION;
import static seedu.tracker.logic.parser.general.CliSyntax.PREFIX_INDEX;
import static seedu.tracker.logic.parser.general.CliSyntax.PREFIX_NAME;

import seedu.tracker.commons.core.index.Index;
import seedu.tracker.logic.commands.slot.EditSlotCommand;
import seedu.tracker.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;
import seedu.tracker.logic.parser.general.ArgumentMultimap;
import seedu.tracker.logic.parser.general.ArgumentTokenizer;
import seedu.tracker.logic.parser.general.Parser;
import seedu.tracker.logic.parser.general.exceptions.ParseException;
import seedu.tracker.model.Model;

/**
 * Parses input arguments and creates a new EditSlotCommand object
 */
public class EditSlotParser implements Parser<EditSlotCommand> {

    private Model model;

    public EditSlotParser(Model model) {
        this.model = model;
    }


    /**
     * Parses the given {@code String} of arguments in the context of the EditSlotCommand
     * and returns an EditSlotCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditSlotCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_DATETIME, PREFIX_DURATION, PREFIX_NAME);

        Index index;

        try {
            index = SlotParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSlotCommand.MESSAGE_USAGE), pe);
        }

        EditSlotDescriptor editSlotDescriptor = new EditSlotDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editSlotDescriptor.setPet(SlotParserUtil.parsePet(argMultimap.getValue(PREFIX_NAME).get(), model));
        }

        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editSlotDescriptor.setDateTime(SlotParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get()));
        }
        if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
            editSlotDescriptor.setDuration(SlotParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get()));
        }

        if (!editSlotDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditSlotCommand.MESSAGE_NOT_EDITED);
        }

        return new EditSlotCommand(index, editSlotDescriptor);
    }
}
