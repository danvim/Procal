package dcheungaa.procal.Func;

import java.io.File;

import dcheungaa.procal.ProcalDocParser.ProcalDoc;

/**
 * Created by Bryan on 1/21/2017.
 */

public class FuncItem {
    private String title, description, procalContentString, contentURI;
    private File procalContentFile;
    private ProcalDoc procalDoc;
    public boolean isPreset;

    public FuncItem(String title, String description, String procalContentString, File procalContentFile, String contentURI, ProcalDoc procalDoc, boolean isPreset) {
        this.title = title;
        this.description = description;
        this.procalContentString = procalContentString;
        this.procalContentFile = procalContentFile;
        this.contentURI = contentURI;
        this.procalDoc = procalDoc;
        this.isPreset = isPreset;
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

    public String getPath() {
        return contentURI;
    }

    public ProcalDoc getProcalDoc() {
        return procalDoc;
    }
}