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
        assertEquals(adq1.size(), 10);
    }

    @Test
    public void addLastResize(){
        ArrayDeque<Integer> adq1= new ArrayDeque<>();

        for (int i = 0; i < 10; i++){
            adq1.addLast(i);
        }
        Integer last = 9;
        assertEquals(last, adq1.get(adq1.size()-1));
    }
    @Test
    public void printDequeTest(){
        ArrayDeque<Integer> adq1= new ArrayDeque<>();
        for (int i = 0; i < 100; i++){
            adq1.addFirst(i);

        }
        adq1.printDeque();

    }

    @Test
    public void removeFirstTest(){
        ArrayDeque<Integer> adq1= new ArrayDeque<>();
        for (int i = 0; i < 10; i++){
            adq1.addFirst(i);

        }
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.addFirst(101);
        adq1.addFirst(102);
        adq1.addFirst(103);
        adq1.addFirst(104);
        adq1.addFirst(105);
        adq1.addFirst(106);
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.addLast(1001);
        adq1.addLast(1002);
        adq1.addLast(1003);
        adq1.addLast(1004);
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeLast();
        adq1.removeLast();
        adq1.removeLast();adq1.removeLast();

        adq1.removeLast();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();
        adq1.removeFirst();


    }
}
