package dcheungaa.procal.Func;

/**
 * Created by Bryan on 1/21/2017.
 */

public class FuncItem {
    private String title, description, procalContent;

    public FuncItem(String title, String description, String procalContent) {
        this.title = title;
        this.description = description;
        this.procalContent = procalContent;
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

    public String getProcalContent() {
        return procalContent;
    }

    public void setProcalContent(String procalContent) {
        this.procalContent = procalContent;
    }
}