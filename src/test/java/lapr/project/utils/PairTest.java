package lapr.project.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PairTest {
    private double first;
    private double second;
    private Pair<Double,Double> pair;
    @BeforeEach
    void setUp(){
        this.first=3;
        this.second=2;
        pair=new Pair<>(first,second);
    }
    @Test
    public void getFirst(){
        assertEquals(pair.getFirst(),3);
    }
    @Test
    public void getSecond(){
        assertEquals(pair.getSecond(),2);
    }
    @Test
    public void equals(){
        assertEquals(pair,pair);
        Pair <Double,Double> doublePair= pair;
        assertEquals(pair,doublePair);
        assertNotEquals(pair,null);
        List<Double> list= new ArrayList<>();
        assertNotEquals(pair,list);
    }
}
