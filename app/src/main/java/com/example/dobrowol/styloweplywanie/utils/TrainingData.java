package com.example.dobrowol.styloweplywanie.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dobrowol on 08.04.17.
 */
public class TrainingData implements Serializable{
    public String name;
    public Date time;
    public ArrayList<TrainingSet> trainingSets;

}
