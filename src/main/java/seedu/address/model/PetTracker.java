package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.UniquePetList;
import seedu.address.model.slot.Schedule;
import seedu.address.model.slot.Slot;

/**
 * Wraps all pet system data at the pet-tracker level
 * Duplicates are not allowed (by .isSamePet comparison)
 */
public class PetTracker implements ReadOnlyPetTracker {
    private final UniquePetList pets;
    private final Schedule slots;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        pets = new UniquePetList();
        slots = new Schedule();
    }

    public PetTracker() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public PetTracker(ReadOnlyPetTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the pet list with {@code pets}.
     * {@code pets} must not contain duplicate persons.
     */
    public void setPets(List<Pet> pets) {
        this.pets.setPets(pets);
    }

    /**
     * Replaces the contents of the schedule with {@code slots}.
     */
    public void setSlots(List<Slot> slots) {
        this.slots.setSlots(slots);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyPetTracker newData) {
        requireNonNull(newData);

        setPets(newData.getPetList());
        setSlots(newData.getSlotList());
    }

    //// pet-level operations

    /**
     * Returns true if a pet with the same identity as {@code pet} exists in the pet tracker.
     */
    public boolean hasPet(Pet pet) {
        requireNonNull(pet);
        return pets.contains(pet);
    }

    /**
     * Returns true if a pet with the same identity as {@code pet} exists in the pet tracker.
     */
    public Pet getPet(Name name) {
        requireNonNull(name);
        return pets.getPet(name);
    }

    /**
     * Adds a pet to the pet tracker.
     * The pet must not already exist in the pet tracker.
     */
    public void addPet(Pet p) {
        pets.add(p);
    }

    /**
     * Replaces the given pet {@code target} in the list with {@code editedPet}.
     * {@code target} must exist in the pet tracker.
     * The pet identity of {@code editedPet} must not be the same as another existing pet in the pet tracker.
     */
    public void setPet(Pet target, Pet editedPet) {
        requireNonNull(editedPet);

        pets.setPet(target, editedPet);
    }

    /**
     * Removes {@code key} from this {@code PetTracker}.
     * {@code key} must exist in the pet tracker.
     */
    public void removePet(Pet key) {
        pets.remove(key);
    }

    //// slot-level operations

    /**
     * Adds a slot to the schedule.
     */
    public void addSlot(Slot p) {
        slots.add(p);
    }

    /**
     * Replaces the given slot {@code target} in the list with {@code editedSlot}.
     * {@code target} must exist in the pet tracker.
     */
    public void setSlot(Slot target, Slot editedSlot) {
        requireNonNull(editedSlot);
        slots.setSlot(target, editedSlot);
    }

    /**
     * Removes {@code key} from this {@code PetTracker}.
     * {@code key} must exist in the pet tracker.
     */
    public void removeSlot(Slot key) {
        slots.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        int numPets = pets.asUnmodifiableObservableList().size();
        int numSlots = slots.asUnmodifiableObservableList().size();
        return String.format("%d pets, %d slots", numPets, numSlots);
    }

    @Override
    public ObservableList<Pet> getPetList() {
        return pets.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Slot> getSlotList() {
        return slots.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && pets.equals(((PetTracker) other).pets)
                && slots.equals(((PetTracker) other).slots));
    }

    @Override
    public int hashCode() {
        return Objects.hash(pets, slots);
    }
}
