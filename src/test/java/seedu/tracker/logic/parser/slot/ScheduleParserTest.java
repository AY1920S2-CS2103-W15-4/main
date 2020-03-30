package seedu.tracker.logic.parser.slot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tracker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.tracker.logic.parser.general.CliSyntax.PREFIX_NAME;
import static seedu.tracker.testutil.Assert.assertThrows;
import static seedu.tracker.testutil.TypicalIndexes.INDEX_FIRST_SLOT;
import static seedu.tracker.testutil.pet.TypicalPets.getTypicalModelManager;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tracker.logic.commands.slot.AddSlotCommand;
import seedu.tracker.logic.commands.slot.DeleteSlotCommand;
import seedu.tracker.logic.commands.slot.EditSlotCommand;
import seedu.tracker.logic.commands.slot.FindSlotCommand;
import seedu.tracker.logic.parser.general.exceptions.ParseException;
import seedu.tracker.model.Model;
import seedu.tracker.model.slot.Slot;
import seedu.tracker.testutil.slot.EditSlotDescriptorBuilder;
import seedu.tracker.testutil.slot.SlotBuilder;
import seedu.tracker.testutil.slot.SlotUtil;

class ScheduleParserTest {

    private final Model model = getTypicalModelManager();
    private final ScheduleParser parser = new ScheduleParser(model);

    @Test
    public void parseCommand_add() throws Exception {
        Slot slot = new SlotBuilder(model).build();
        AddSlotCommand command = (AddSlotCommand) parser.parseCommand(SlotUtil.getAddSlotCommand(slot));
        assertEquals(new AddSlotCommand(slot), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteSlotCommand command = (DeleteSlotCommand) parser.parseCommand(
                DeleteSlotCommand.COMMAND_WORD + " " + INDEX_FIRST_SLOT.getOneBased());
        assertEquals(new DeleteSlotCommand(INDEX_FIRST_SLOT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Slot slot = new SlotBuilder(model).build();
        EditSlotCommand.EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder(slot).build();
        EditSlotCommand command = (EditSlotCommand) parser.parseCommand(EditSlotCommand.COMMAND_WORD + " "
                + INDEX_FIRST_SLOT.getOneBased() + " " + SlotUtil.getEditSlotDescriptorDetails(descriptor));
        assertEquals(new EditSlotCommand(INDEX_FIRST_SLOT, descriptor), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        StringBuilder sb = new StringBuilder();
        keywords.forEach(x -> sb.append(PREFIX_NAME).append(x).append(" "));
        FindSlotCommand command = (FindSlotCommand) parser.parseCommand(
                FindSlotCommand.COMMAND_WORD + " " + sb.toString());
        assertEquals(new FindSlotCommand(FindSlotParser.getPredicates(sb.toString())), command);
    }

    // Test for help already done in PetTrackerParserTest

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
