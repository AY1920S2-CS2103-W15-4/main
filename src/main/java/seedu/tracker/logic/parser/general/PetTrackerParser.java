package seedu.tracker.logic.parser.general;

import static seedu.tracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tracker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.tracker.logic.commands.general.Command;
import seedu.tracker.logic.commands.general.DisplayCommand;
import seedu.tracker.logic.commands.general.ExitCommand;
import seedu.tracker.logic.commands.general.HelpCommand;
import seedu.tracker.logic.commands.general.StatsCommand;
import seedu.tracker.logic.commands.pet.AddPetCommand;
import seedu.tracker.logic.commands.pet.DeletePetCommand;
import seedu.tracker.logic.commands.pet.EditPetCommand;
import seedu.tracker.logic.commands.pet.FindPetCommand;
import seedu.tracker.logic.commands.slot.AddSlotCommand;
import seedu.tracker.logic.commands.slot.ConflictCommand;
import seedu.tracker.logic.commands.slot.DeleteSlotCommand;
import seedu.tracker.logic.commands.slot.EditSlotCommand;
import seedu.tracker.logic.commands.slot.FindSlotCommand;
import seedu.tracker.logic.parser.general.exceptions.ParseException;
import seedu.tracker.logic.parser.pet.AddPetParser;
import seedu.tracker.logic.parser.pet.DeletePetParser;
import seedu.tracker.logic.parser.pet.EditPetParser;
import seedu.tracker.logic.parser.pet.FindPetParser;
import seedu.tracker.logic.parser.slot.AddSlotParser;
import seedu.tracker.logic.parser.slot.DeleteSlotParser;
import seedu.tracker.logic.parser.slot.EditSlotParser;
import seedu.tracker.logic.parser.slot.FindSlotParser;
import seedu.tracker.model.Model;

/**
 * Parse user input.
 */
public class PetTrackerParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private final Model model;

    public PetTrackerParser(Model model) {
        this.model = model;
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        return parseCommand(commandWord, arguments);
    }

    /**
     * Parses user input into command for execution.
     *
     * @param commandWord the command name
     * @param arguments the string of arguments to the command
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    private Command parseCommand(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {

        // general
        case DisplayCommand.COMMAND_WORD:
            return new DisplayParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        // pet tracker
        case AddPetCommand.COMMAND_WORD:
            return new AddPetParser().parse(arguments);

        case EditPetCommand.COMMAND_WORD:
            return new EditPetParser().parse(arguments);

        case DeletePetCommand.COMMAND_WORD:
            return new DeletePetParser().parse(arguments);

        case FindPetCommand.COMMAND_WORD:
            return new FindPetParser().parse(arguments);

        // schedule
        case AddSlotCommand.COMMAND_WORD:
            return new AddSlotParser(model).parse(arguments);

        case EditSlotCommand.COMMAND_WORD:
            return new EditSlotParser(model).parse(arguments);

        case DeleteSlotCommand.COMMAND_WORD:
            return new DeleteSlotParser().parse(arguments);

        case FindSlotCommand.COMMAND_WORD:
            return new FindSlotParser().parse(arguments);

        case ConflictCommand.COMMAND_WORD:
            return new ConflictCommand();

        case StatsCommand.COMMAND_WORD:
            return new StatsCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
