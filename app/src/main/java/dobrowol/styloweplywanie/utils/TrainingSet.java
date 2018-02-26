package dobrowol.styloweplywanie.utils;

import java.io.Serializable;

public class TrainingSet implements Serializable{
    public int distance;
    public SwimmingStyles style;
    public String excercise;
    public String description;
    public TrainingSet(int dst, SwimmingStyles st, String desc)
    {
        distance = dst;
        style = st;
        description = desc;
    }
}
