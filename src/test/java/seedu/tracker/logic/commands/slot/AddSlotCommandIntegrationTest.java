package seedu.tracker.logic.commands.slot;

import static seedu.tracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tracker.testutil.pet.TypicalPets.getTypicalModelManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.tracker.model.Model;
import seedu.tracker.model.ModelManager;
import seedu.tracker.model.UserPrefs;
import seedu.tracker.model.slot.Slot;
import seedu.tracker.testutil.slot.SlotBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddSlotCommand}.
 */
public class AddSlotCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = getTypicalModelManager();
    }

    @Test
    public void execute_newSlot_success() {
        Slot validSlot = new SlotBuilder(model).build();

        Model expectedModel = new ModelManager(model.getPetTracker(), new UserPrefs());
        expectedModel.addSlot(validSlot);

        assertCommandSuccess(new AddSlotCommand(validSlot), model,
                String.format(AddSlotCommand.MESSAGE_SUCCESS, validSlot), expectedModel);
    }

}
