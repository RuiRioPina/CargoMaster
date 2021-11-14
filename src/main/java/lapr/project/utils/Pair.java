package lapr.project.utils;


import java.util.Objects;

public class Pair<T, U> {
    /**
     * Creator of the pair
     * @param first first element of the pair
     * @param second second element of the pair
     */
    public Pair(T first, U second) {
        this.second = second;
        this.first = first;
    }

    private final T first;
    private final  U second;

    /**
     * get method for the first element of the pair
     * @return first element of the pair
     */
    public T getFirst(){
        return this.first;
    }

    /**
     * get method for the second element of the pair
     * @return first second of the pair
     */
    public  U getSecond(){
        return this.second;
    }

    /**
     * equals method to see if a Pair is equal
     * @param o object to be compared
     * @return boolean value saying whether the ship is equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }


}