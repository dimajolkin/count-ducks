package ru.jolkin.countDucks.duck;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DuckManager {
    private List<Duck> list = new ArrayList<>();
    private HashMap<Type, Integer> groupByType = new HashMap<>();

    public DuckManager() {}

    public DuckManager(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        list = (List<Duck>)ois.readObject();
        ois.close();
    }

    public void add(Duck duck)
    {
        list.add(duck);
        groupByType.put(duck.getType(), groupByType.getOrDefault(duck.getType(), 0) + 1);
    }

    public List<Duck> fetchAll() {
        return list;
    }

    public HashMap<Type, Integer> groupByType()
    {
        return groupByType;
    }

    public void save(String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(list);
        oos.close();
    }
}
