package ru.silhin.lab_econom.model;

import java.util.Arrays;

public class WorkModel extends ModelObject {
    private String name;
    private String index;

    public WorkModel() {
        super();
    }

    public WorkModel(String index, String name) {
        super();
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return index + " " + name;
    }

    public static int compare(WorkModel work1, WorkModel work2) {
        String[] work1Indexes = work1.index.split("\\.");
        String[] work2Indexes = work2.index.split("\\.");

        int chars = Math.max(work1Indexes.length, work2Indexes.length);
        if(work1Indexes.length < chars) {
            int length = work1Indexes.length;
            work1Indexes = Arrays.copyOf(work1Indexes, chars);
            Arrays.fill(work1Indexes, length, work1Indexes.length, "0");
        } else if(work2Indexes.length < chars) {
            int length = work2Indexes.length;
            work2Indexes = Arrays.copyOf(work2Indexes, chars);
            Arrays.fill(work2Indexes, length, work2Indexes.length, "0");
        }

        for(int i = 0; i < chars; ++i) {
            int index1 = Integer.parseInt(work1Indexes[i]);
            int index2 = Integer.parseInt(work2Indexes[i]);
            int intCompare = Integer.compare(index1, index2);
            if(intCompare != 0) {
                return intCompare;
            }
        }
        return 0;
    }
}
