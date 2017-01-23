package dcheungaa.procal.ProcalDocParser;

import java.util.ArrayList;
import java.util.List;

public class ProcalDoc {
    public String title = "";
    public String desc = "";
    public String returnDesc = "";
    public boolean isDraft = false;
    public List<String> sampleIn = new ArrayList<>();
    public List<String> sampleOut = new ArrayList<>();
    public List<Param> params = new ArrayList<>();
}
