package dcheungaa.procal.Func;

import java.io.File;

/**
 * Created by Bryan on 1/21/2017.
 */

public class FuncItem {
    private String title, description, procalContentString;
    private File procalContentFile;

    public FuncItem(String title, String description, String procalContentString, File procalContentFile) {
        this.title = title;
        this.description = description;
        this.procalContentString = procalContentString;
        this.procalContentFile = procalContentFile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProcalContentString() {
        return procalContentString;
    }

    public void setProcalContentString(String procalContent) {
        this.procalContentString = procalContentString;
    }

    public File getProcalContentFile() {
        return procalContentFile;
    }

    public void setProcalContentFile(File procalContentFile) {
        this.procalContentFile = procalContentFile;
    }

}