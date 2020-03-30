package seedu.tracker.logic.commands.slot;

import static java.util.Objects.requireNonNull;
import static seedu.tracker.model.Model.PREDICATE_SHOW_ALL_SLOTS;

import seedu.tracker.commons.core.Messages;
import seedu.tracker.logic.commands.general.Command;
import seedu.tracker.logic.commands.general.CommandResult;
import seedu.tracker.model.Model;
import seedu.tracker.model.slot.SlotConflictPredicate;

/**
 * Finds and lists all slots in the schedule which have conflicts.
 */
public class ConflictCommand extends Command {
    public static final String COMMAND_WORD = "conflicts";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all slots that have conflicts.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSlotList(PREDICATE_SHOW_ALL_SLOTS);
        model.updateFilteredSlotList(new SlotConflictPredicate(model.getFilteredSlotList()));
        return new CommandResult(
                String.format(Messages.MESSAGE_SLOTS_LISTED_OVERVIEW, model.getFilteredSlotList().size()));
    }
}
