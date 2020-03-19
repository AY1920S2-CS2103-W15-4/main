package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of display items.
 */
public class DisplayListPanel extends UiPart<Region> {
    private static final String FXML = "DisplayListPanel.fxml";

    @FXML
    private ListView<DisplayItem> displayListView;

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
                setGraphic(item.getDisplayCard(getIndex() + 1).getRoot());
            }
        }
    }
}
