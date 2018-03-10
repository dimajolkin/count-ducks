package ru.jolkin.countDucks.duck;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DuckManager {
    private List<Duck> list = new ArrayList<>();

    private transient HashMap<Type, Integer> groupByType = new HashMap<>();

    public DuckManager() {
    }

    public static DuckManager createByJson(String json) {
        List<Duck> ducks = new Gson().fromJson(json, DuckManager.class).fetchAll();
        DuckManager manager = new DuckManager();
        for (Duck d : ducks) {
            manager.add(d);
        }

        return manager;
    }

    public void add(Duck duck) {
        list.add(duck);
        groupByType.put(duck.getType(), groupByType.getOrDefault(duck.getType(), 0) + 1);
    }

    public List<Duck> fetchAll() {
        return list;
    }

    public HashMap<Type, Integer> groupByType() {
        return groupByType;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public void back() {
        if (list != null && !list.isEmpty()) {
            Duck duck = list.get(list.size() - 1);
            list.remove(duck);
            groupByType.put(duck.getType(), groupByType.getOrDefault(duck.getType(), 0) - 1);
        }
    }
}
