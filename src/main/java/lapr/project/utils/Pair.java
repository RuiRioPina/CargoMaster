package lapr.project.utils;



public class Pair<T, U> {

    public Pair(T first, U second) {
        this.second = second;
        this.first = first;
    }

    private final T first;
    private final  U second;


    public T getFirst(){
        return this.first;
    }
    public  U getSecond(){
        return this.second;
    }



}