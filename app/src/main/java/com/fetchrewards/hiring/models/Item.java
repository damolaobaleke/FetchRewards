package com.fetchrewards.hiring.models;

import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;

public class Item implements Comparator<Item> {
    int id;
    Integer listId;
    String name;

    public Item() {
    }

    public Item(int id, Integer listId, String name) {
        this.id = id;
        this.listId = listId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Integer getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compare(Item obj1, Item obj2) {
        int result = obj1.getListId().compareTo(obj2.getListId());

        if (result == 0) {
            //String Utils takes care of null-safety, as null is acceptable to compare in this case
            result =  StringUtils.compare(obj1.getName(), obj2.getName(), true);

        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}


//if(obj1.getName() == null && obj2.getName() != null) {
// result = -1;
// }else if(obj1.getName() != null && obj2.getName() == null){
// result = 1;
//}else { result = obj1.getName().compareTo(obj2.getName());}