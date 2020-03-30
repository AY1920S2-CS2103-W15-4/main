package seedu.tracker.logic.commands.slot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tracker.commons.core.Messages.MESSAGE_SLOTS_LISTED_OVERVIEW;
import static seedu.tracker.logic.commands.CommandTestUtil.NAME_DESC_COCO;
import static seedu.tracker.logic.commands.CommandTestUtil.NAME_DESC_GARFIELD;
import static seedu.tracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tracker.logic.parser.general.CliSyntax.PREFIX_NAME;
import static seedu.tracker.testutil.pet.TypicalPets.getTypicalModelManager;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.tracker.logic.parser.general.exceptions.ParseException;
import seedu.tracker.logic.parser.slot.FindSlotParser;
import seedu.tracker.model.Model;
import seedu.tracker.model.slot.Slot;
import seedu.tracker.model.slot.SlotPetNamePredicate;
import seedu.tracker.testutil.slot.TypicalSlotsGenerator;

class FindSlotCommandTest {

    private Model model = getTypicalModelManager();
    private Model expectedModel = getTypicalModelManager();

    @Test
    public void equals() {
        Predicate<Slot> firstPredicate =
                new SlotPetNamePredicate(Arrays.asList("Coco"));
        Predicate<Slot> secondPredicate =
                new SlotPetNamePredicate(Arrays.asList("Garfield"));

        FindSlotCommand findFirstCommand = new FindSlotCommand(firstPredicate);
        FindSlotCommand findSecondCommand = new FindSlotCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // same values -> returns true
        FindSlotCommand findFirstCommandCopy = new FindSlotCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_throwParseException() throws ParseException {
        String expectedMessage = FindSlotCommand.MESSAGE_EMPTY_NAME_FIELD;
        FindSlotParser parser = new FindSlotParser();
        assertParseFailure(parser, " " + PREFIX_NAME, expectedMessage);
    }

    @Test
    public void execute_multipleKeywords_multipleSlotsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_SLOTS_LISTED_OVERVIEW, 3);
        Predicate<Slot> predicate = FindSlotParser.getPredicates(NAME_DESC_COCO + " " + NAME_DESC_GARFIELD);
        FindSlotCommand command = new FindSlotCommand(predicate);
        expectedModel.updateFilteredSlotList(predicate);
        TypicalSlotsGenerator slotsGen = new TypicalSlotsGenerator(model);
        assertEquals(slotsGen.getTypicalSlots(), model.getFilteredSlotList());
    }
}
