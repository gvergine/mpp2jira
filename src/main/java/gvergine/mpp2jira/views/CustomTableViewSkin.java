package gvergine.mpp2jira.views;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.skin.NestedTableColumnHeader;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.control.skin.TreeTableViewSkin;

public class CustomTableViewSkin<T> extends TreeTableViewSkin<T> {

    private class CustomTableColumnHeader extends TableColumnHeader {
        /**
         * Creates a new TableColumnHeader instance to visually represent the given
         * {@link TableColumnBase} instance.
         *
         * @param tc The table column to be visually represented by this instance.
         */
        @SuppressWarnings("rawtypes")
		public CustomTableColumnHeader(TableColumnBase tc) {
            super(tc);
        }

        public void resizeColumnToFitContent() {
            super.resizeColumnToFitContent(-1);
        }
    }

    public CustomTableViewSkin(TreeTableView<T> tableView) {
        super(tableView);
    }

    @Override
    protected TableHeaderRow createTableHeaderRow() {
        return new TableHeaderRow(this) {
            @Override
            protected NestedTableColumnHeader createRootHeader() {
                return new NestedTableColumnHeader(null) {
                    @SuppressWarnings("rawtypes")
					@Override
                    protected TableColumnHeader createTableColumnHeader(TableColumnBase col) {
                        CustomTableColumnHeader columnHeader = new CustomTableColumnHeader(col);

                        columnHeadersList().add(columnHeader);

                        return columnHeader;
                    }
                };
            }
        };
    }

    @SuppressWarnings("unchecked")
	public void resizeColumnToFit() {
        if (!columnHeadersList().isEmpty()) {
            for (TableColumnHeader columnHeader : columnHeadersList()) {
                ((CustomTableColumnHeader)columnHeader).resizeColumnToFitContent();
            }
        }
    }
    
    private ObservableList<TableColumnHeader> columnHeadersList() {
    	return getTableHeaderRow().getRootHeader().getColumnHeaders();
    }
}