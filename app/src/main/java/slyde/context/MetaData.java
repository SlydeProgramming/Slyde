package slyde.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetaData {
    public HandleProtocol hp;
    public String requestedName;
    public Map<String, List<Integer>> returnIndex = new HashMap<>();
    public List<String> returnValues = new ArrayList<>();
    public List<String> contextNames = new ArrayList<>();
}
