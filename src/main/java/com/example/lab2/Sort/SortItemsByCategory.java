package com.example.lab2.Sort;

import java.util.List;

public interface SortItemsByCategory<T> {
    List<T> sortByCategory(List<T> items);
}