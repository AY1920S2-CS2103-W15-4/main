package seedu.address.logic.petCommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.petParser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.petParser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.petParser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.petParser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.petParser.CliSyntax.PREFIX_FOODLIST;
import static seedu.address.logic.petParser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.PshModel.PREDICATE_SHOW_ALL_PETS;

import java.util.*;

import seedu.address.commons.core.PshMessages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.generalCommands.Command;
import seedu.address.logic.generalCommands.CommandResult;
import seedu.address.logic.generalCommands.exceptions.CommandException;
import seedu.address.model.PshModel;
import seedu.address.model.pet.*;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditPetCommand extends Command {

    public static final String COMMAND_WORD = "editpet";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the pets identified "
            + "by the index number used in the displayed pets list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_DOB + "DATE OF BIRTH] "
            + "[" + PREFIX_SPECIES + "SPECIES] "
            + "[" + PREFIX_FOODLIST + "LIST OF FOOD AND AMOUNT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GENDER + "female "
            + PREFIX_DOB + "01-02-2013";

    public static final String MESSAGE_EDIT_PET_SUCCESS = "Edited Pet: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PET = "This pet already exists in the pet store helper.";

    private final Index index;
    private final EditPetDescriptor editPetDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPetDescriptor details to edit the person with
     */
    public EditPetCommand(Index index, EditPetDescriptor editPetDescriptor) {
        requireNonNull(index);
        requireNonNull(editPetDescriptor);

        this.index = index;
        this.editPetDescriptor = new EditPetDescriptor(editPetDescriptor);
    }

    @Override
        public CommandResult execute(PshModel model) throws CommandException {
        requireNonNull(model);
        List<Pet> lastShownList = model.getFilteredPetList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(PshMessages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
        }

        Pet petToEdit = lastShownList.get(index.getZeroBased());
        Pet editedPet = createEditedPet(petToEdit, editPetDescriptor);

        if (!petToEdit.isSamePet(editedPet) && model.hasPet(editedPet)) {
            throw new CommandException(MESSAGE_DUPLICATE_PET);
        }

        model.setPet(petToEdit, editedPet);
        model.updateFilteredPetList(PREDICATE_SHOW_ALL_PETS);
        return new CommandResult(String.format(MESSAGE_EDIT_PET_SUCCESS, editedPet));

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Pet createEditedPet(Pet petToEdit, EditPetDescriptor editPetDescriptor) {
        assert petToEdit != null;

        Name updatedName = editPetDescriptor.getName().orElse(petToEdit.getName());
        Gender updatedGender = editPetDescriptor.getGender().orElse(petToEdit.getGender());
        DateOfBirth updatedDateOfBirth = editPetDescriptor.getDateOfBirth().orElse(petToEdit.getDateOfBirth());
        Species updatedSpecies = editPetDescriptor.getSpecies().orElse(petToEdit.getSpecies());
        Set<Food> updatedFoodList = editPetDescriptor.getFoodList().orElse(petToEdit.getFoodList());
        Set<Tag> updatedTags = editPetDescriptor.getTags().orElse(petToEdit.getTags());

        return new Pet(updatedName, updatedGender, updatedDateOfBirth, updatedSpecies, updatedFoodList, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPetCommand)) {
            return false;
        }

        // state check
        EditPetCommand e = (EditPetCommand) other;
        return index.equals(e.index)
                && editPetDescriptor.equals(e.editPetDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPetDescriptor {
        private Name name;
        private Gender gender;
        private DateOfBirth dateOfBirth;
        private Species species;
        private Set<Food> foodList;
        private Set<Tag> tags;

        public EditPetDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPetDescriptor(EditPetDescriptor toCopy) {
            setName(toCopy.name);
            setGender(toCopy.gender);
            setDateOfBirth(toCopy.dateOfBirth);
            setSpecies(toCopy.species);
            setFoodList(toCopy.foodList);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, gender, dateOfBirth, species, foodList, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setDateOfBirth(DateOfBirth dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Optional<DateOfBirth> getDateOfBirth() {
            return Optional.ofNullable(dateOfBirth);
        }

        public void setSpecies(Species species) {
            this.species = species;
        }

        public Optional<Species> getSpecies() {
            return Optional.ofNullable(species);
        }

        public void setFoodList(Set<Food> foodList) {
            this.foodList = (foodList != null) ? new HashSet<>(foodList) : null;
        }

        public Optional<Set<Food>> getFoodList() {
            return (foodList != null) ? Optional.of(Collections.unmodifiableSet(foodList)) : Optional.empty();
        }
        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPetDescriptor)) {
                return false;
            }

            // state check
            EditPetDescriptor e = (EditPetDescriptor) other;

            return getName().equals(e.getName())
                    && getGender().equals(e.getGender())
                    && getDateOfBirth().equals(e.getDateOfBirth())
                    && getSpecies().equals(e.getSpecies())
                    && getFoodList().equals(e.getFoodList())
                    && getTags().equals(e.getTags());
        }
    }
}
