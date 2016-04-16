package pl.edu.agh.marcskow.ftpclient.layout;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.File;

public class SimpleFileTreeItem extends TreeItem<File> {

    public SimpleFileTreeItem(File f) {
        super(f);
    }

    @Override
    public ObservableList<TreeItem<File>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;
            super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        if (isFirstTimeLeaf) {
            isFirstTimeLeaf = false;
            File f = (File) getValue();
            isLeaf = f.isFile();
        }
        return isLeaf;
    }

    private ObservableList<TreeItem<File>> buildChildren(TreeItem<File> TreeItem) {
        File f = TreeItem.getValue();
        if (f != null && f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                ObservableList<TreeItem<File>> children = FXCollections
                        .observableArrayList();

                for (File childFile : files) {
                    SimpleFileTreeItem simpleFileTreeItem = new SimpleFileTreeItem(childFile);
                    simpleFileTreeItem.setGraphic(ImageService.checkImage(childFile));
                    children.add(simpleFileTreeItem);
                }

                return children;
            }
        }

        return FXCollections.emptyObservableList();
    }

    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;
    private boolean isLeaf;
}

