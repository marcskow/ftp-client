package pl.edu.agh.marcskow.ftpclient.layout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;


public class SimpleStringTreeItem extends TreeItem<String> {
    public String[] children;
    public String name;
    public String root;
    public boolean isFile;

    public SimpleStringTreeItem(String f, String[] files) {
        super(f.substring(0,f.length() - 2));
        name = f.substring(0,f.length() - 2);
        isFile = f.substring(f.length() - 1).equals("f");
        this.children = files;
    }


    @Override
    public ObservableList<TreeItem<String>> getChildren() {
//        super.getChildren().setAll(buildChildren(this));
        return buildChildren(this);
        //    return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        if(isFile) return true;
        else return false;
    }

    private ObservableList<TreeItem<String>> buildChildren(TreeItem<String> TreeItem) {
        ObservableList<TreeItem<String>> children = FXCollections.observableArrayList();
        for (String childFile : this.children) {
            SimpleStringTreeItem simpleStringTreeItem = new SimpleStringTreeItem(childFile, null);
            simpleStringTreeItem.setGraphic(ImageService.checkImageToString(childFile));
            children.add(simpleStringTreeItem);
        }
        return children;
    }

}
