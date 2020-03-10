package seedu.address.logic.petcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.petparser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.petparser.CliSyntax.PREFIX_FOODLIST;
import static seedu.address.logic.petparser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.petparser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.petparser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.petparser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.generalcommands.Command;
import seedu.address.logic.generalcommands.CommandResult;
import seedu.address.logic.generalcommands.exceptions.CommandException;
import seedu.address.model.PshModel;
import seedu.address.model.pet.Pet;

/**
 * Adds a person to the address book.
 */
public class AddPetCommand extends Command {

    public static final String COMMAND_WORD = "addpet";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a pet to the pet store helper. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_GENDER + "GENDER "
            + PREFIX_DOB + "DATE OF BIRTH "
            + PREFIX_SPECIES + "SPECIES "
            + PREFIX_FOODLIST + "LIST OF FOOD AND AMOUNT "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Teddy "
            + PREFIX_GENDER + "male "
            + PREFIX_DOB + "01-01-2019 "
            + PREFIX_SPECIES + "dog "
            + PREFIX_FOODLIST + "brand A: 15 "
            + PREFIX_FOODLIST + "brand B: 20 "
            + PREFIX_TAG + "small size "
            + PREFIX_TAG + "lazy ";

    public static final String MESSAGE_SUCCESS = "New pet added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This pet already exists in the pet store helper";

    private final Pet toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddPetCommand(Pet pet) {
        requireNonNull(pet);
        toAdd = pet;
    }

    @Override
    public CommandResult execute(PshModel model) throws CommandException {
        requireNonNull(model);
        if (model.hasPet(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.addPet(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPetCommand // instanceof handles nulls
                && toAdd.equals(((AddPetCommand) other).toAdd));
    }
}
