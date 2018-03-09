package ru.jolkin.countDucks.duck;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DuckManager {
    private List<Duck> list = new ArrayList<>();

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
    }


    public List<Duck> fetchAll() {
        return list;
    }


    public void save(String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(list);
        oos.close();
    }


}
