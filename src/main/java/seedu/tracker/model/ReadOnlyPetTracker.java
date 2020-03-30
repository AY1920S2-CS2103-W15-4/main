package seedu.tracker.model;

import javafx.collections.ObservableList;
import seedu.tracker.model.pet.FoodCollection;
import seedu.tracker.model.pet.Pet;
import seedu.tracker.model.slot.Slot;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyPetTracker {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Pet> getPetList();

    /**
     * Returns an unmodifiable view of the slots list.
     */
    ObservableList<Slot> getSlotList();

    /**
     * Returns an unmodifiable view of the food collection list.
     */
    ObservableList<FoodCollection> getFoodCollectionList();
}
