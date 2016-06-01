package net.es.test.pathfinding.graph;

import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author hacksaw
 */
public class SortedGraphObject implements Comparator<SortedGraphObject>, Comparable<SortedGraphObject> {
    private final String id;
    private final long sortValue;
    private static final Random random = new Random();

    public SortedGraphObject(String id) {
        this.id = id;
        sortValue = random.nextLong();
    }

    public SortedGraphObject(String id, long sortValue) {
        this.id = id;
        this.sortValue = sortValue;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the sortValue
     */
    public long getSortValue() {
        return sortValue;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (! (other instanceof SortedGraphObject)) return false;

        SortedGraphObject that = (SortedGraphObject) other;
        return this.getSortValue() == that.getSortValue();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (int) (this.sortValue ^ (this.sortValue >>> 32));
        return hash;
    }

    @Override
    public int compare(SortedGraphObject o1, SortedGraphObject o2) {
        if (o1 == null || o2 == null) {
            throw new NullPointerException();
        }

        if (o1.getSortValue() == o2.getSortValue()) {
            return 0;
        }

        if (o1.getSortValue() > o2.getSortValue()) {
            return 1;
        }

        return -1;
    }

    @Override
    public int compareTo(SortedGraphObject that) {
        if (this == that) return 0;

        if (this.getSortValue() == that.getSortValue()) {
            return 0;
        }

        if (this.getSortValue() > that.getSortValue()) {
            return 1;
        }

        return -1;
    }
}
