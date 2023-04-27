package deque;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
    @Test
    public void addFirstTest() {
        ArrayDeque<Integer> adq1= new ArrayDeque<>();
        adq1.addFirst(1);
        adq1.addFirst(2);
        Integer first = 3;
        adq1.addFirst(first);

        assertEquals(first, adq1.get(0));
    }
    @Test
    public void addFirstResize() {
        ArrayDeque<Integer> adq1= new ArrayDeque<>();

        for (int i = 0; i < 10; i++){
            adq1.addFirst(i);
        }
        Integer first = 9;
        assertEquals(first, adq1.get(0));
    }

    @Test
    public void addLastResize(){
        ArrayDeque<Integer> adq1= new ArrayDeque<>();

        for (int i = 0; i < 10; i++){
            adq1.addLast(i);
        }
        Integer last = 9;
        assertEquals(last, adq1.get(0));
    }
}
