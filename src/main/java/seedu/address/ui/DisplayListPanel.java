package seedu.address.ui;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.pet.FoodCollection;
import seedu.address.model.pet.Pet;
import seedu.address.model.slot.Slot;

/**
 * Panel containing the list of display items.
 */
public class DisplayListPanel extends UiPart<Region> {
    private static final String FXML = "DisplayListPanel.fxml";

    @FXML
    private ListView<DisplayItem> displayListView;
    @FXML
    private ListView<DisplayItem> displayInformationView;

    public DisplayListPanel(ObservableList<DisplayItem> displayList) {
        super(FXML);
        displayListView.setItems(displayList);
        displayListView.setCellFactory(listView -> new DisplayListViewCell());
    }

    /**
     * Changes the backing list of display items to {@code newDisplayList}.
     */
    public final void updateWith(ObservableList<DisplayItem> newDisplayList) {
        displayListView.setItems(newDisplayList);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code DisplayItem}.
     */
    class DisplayListViewCell extends ListCell<DisplayItem> {
        @Override
        protected void updateItem(DisplayItem item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                switch (item.getDisplaySystemType()) {
                case PETS:
                    setGraphic(new PetCard((Pet) item, getIndex() + 1).getRoot());
                    break;
                case SCHEDULE:
                    List<Slot> allSlots = displayListView.getItems()
                            .stream()
                            .map(slot -> (Slot) slot)
                            .collect(Collectors.toList());
                    setGraphic(new SlotCard((Slot) item, getIndex() + 1, allSlots).getRoot());
                    break;
                case INVENTORY:
                    setGraphic(new FoodCollectionCard((FoodCollection) item, getIndex() + 1).getRoot());
                    break;
                default:
                    setGraphic(null);
                    setText(null);
                }
            }
        }
    }
}
