package seedu.tracker.logic.commands.slot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tracker.logic.commands.CommandTestUtil.VALID_DATETIME_COCO;
import static seedu.tracker.logic.commands.CommandTestUtil.VALID_DURATION_COCO;
import static seedu.tracker.logic.commands.CommandTestUtil.VALID_NAME_COCO;
import static seedu.tracker.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tracker.logic.commands.CommandTestUtil.getSlotDescCoco;
import static seedu.tracker.logic.commands.CommandTestUtil.getSlotDescGarfield;
import static seedu.tracker.logic.commands.CommandTestUtil.showSlotAtIndex;
import static seedu.tracker.testutil.TypicalIndexes.INDEX_FIRST_SLOT;
import static seedu.tracker.testutil.TypicalIndexes.INDEX_SECOND_SLOT;
import static seedu.tracker.testutil.pet.TypicalPets.getTypicalModelManager;

import org.junit.jupiter.api.Test;

import seedu.tracker.commons.core.Messages;
import seedu.tracker.commons.core.index.Index;
import seedu.tracker.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;
import seedu.tracker.model.Model;
import seedu.tracker.model.ModelManager;
import seedu.tracker.model.PetTracker;
import seedu.tracker.model.UserPrefs;
import seedu.tracker.model.pet.Name;
import seedu.tracker.model.slot.Slot;
import seedu.tracker.testutil.slot.EditSlotDescriptorBuilder;
import seedu.tracker.testutil.slot.SlotBuilder;

class EditSlotCommandTest {

    private Model model = getTypicalModelManager();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Slot editedSlot = new SlotBuilder(model).build();
        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder(editedSlot).build();
        EditSlotCommand editCommand = new EditSlotCommand(INDEX_FIRST_SLOT, descriptor);

        String expectedMessage = String.format(EditSlotCommand.MESSAGE_EDIT_SLOT_SUCCESS, editedSlot);

        Model expectedModel = new ModelManager(new PetTracker(model.getPetTracker()), new UserPrefs());
        expectedModel.setSlot(model.getFilteredSlotList().get(0), editedSlot);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastSlot = Index.fromOneBased(model.getFilteredSlotList().size());
        Slot lastSlot = model.getFilteredSlotList().get(indexLastSlot.getZeroBased());

        SlotBuilder slotInList = new SlotBuilder(lastSlot);
        Slot editedSlot = slotInList.withPet(model.getPet(new Name(VALID_NAME_COCO))).withDateTime(VALID_DATETIME_COCO)
                .withDuration(VALID_DURATION_COCO).build();

        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder().withPet(
                model.getPet(new Name(VALID_NAME_COCO)))
                .withDateTime(VALID_DATETIME_COCO).withDuration(VALID_DURATION_COCO).build();
        EditSlotCommand editCommand = new EditSlotCommand(indexLastSlot, descriptor);

        String expectedMessage = String.format(EditSlotCommand.MESSAGE_EDIT_SLOT_SUCCESS, editedSlot);

        Model expectedModel = new ModelManager(new PetTracker(model.getPetTracker()), new UserPrefs());
        expectedModel.setSlot(lastSlot, editedSlot);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditSlotCommand editCommand = new EditSlotCommand(INDEX_FIRST_SLOT, new EditSlotDescriptor());
        Slot editedSlot = model.getFilteredSlotList().get(INDEX_FIRST_SLOT.getZeroBased());

        String expectedMessage = String.format(EditSlotCommand.MESSAGE_EDIT_SLOT_SUCCESS, editedSlot);

        Model expectedModel = new ModelManager(new PetTracker(model.getPetTracker()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showSlotAtIndex(model, INDEX_FIRST_SLOT);

        Slot slotInFilteredList = model.getFilteredSlotList().get(INDEX_FIRST_SLOT.getZeroBased());
        Slot editedSlot = new SlotBuilder(slotInFilteredList).withPet(model.getPet(new Name(VALID_NAME_COCO))).build();
        EditSlotCommand editCommand = new EditSlotCommand(INDEX_FIRST_SLOT,
                new EditSlotDescriptorBuilder().withPet(model.getPet(new Name(VALID_NAME_COCO))).build());

        String expectedMessage = String.format(EditSlotCommand.MESSAGE_EDIT_SLOT_SUCCESS, editedSlot);

        Model expectedModel = new ModelManager(new PetTracker(model.getPetTracker()), new UserPrefs());
        expectedModel.setSlot(model.getFilteredSlotList().get(0), editedSlot);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSlotIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSlotList().size() + 1);
        EditSlotDescriptor descriptor = new EditSlotDescriptorBuilder()
                .withPet(model.getPet(new Name(VALID_NAME_COCO))).build();
        EditSlotCommand editCommand = new EditSlotCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_SLOT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidSlotIndexFilteredList_failure() {
        showSlotAtIndex(model, INDEX_FIRST_SLOT);
        Index outOfBoundIndex = INDEX_SECOND_SLOT;

        // ensures that outOfBoundIndex is still in bounds of pet tracker list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPetTracker().getSlotList().size());

        EditSlotCommand editCommand = new EditSlotCommand(outOfBoundIndex,
                new EditSlotDescriptorBuilder().withPet(model.getPet(new Name(VALID_NAME_COCO))).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_SLOT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditSlotCommand standardCommand = new EditSlotCommand(INDEX_FIRST_SLOT, getSlotDescCoco(model));

        // same values -> returns true
        EditSlotDescriptor copyDescriptor = new EditSlotDescriptor(getSlotDescCoco(model));
        EditSlotCommand commandWithSameValues = new EditSlotCommand(INDEX_FIRST_SLOT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditSlotCommand(INDEX_SECOND_SLOT, getSlotDescCoco(model))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditSlotCommand(INDEX_FIRST_SLOT, getSlotDescGarfield(model))));
    }
}
