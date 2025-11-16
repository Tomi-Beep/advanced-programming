package laboratory_problems.problems_02;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

class ResizableArray<T> {
    private T[] array;
    private int count;
    private static final int DEFAULT_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    ResizableArray() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
        count = 0;
    }

    public void addElement(T element) {
        if (count == array.length)
            expand();
        array[count++] = element;
    }

    public boolean removeElement(T element) {
        int idx = -1;
        for (int i = 0; i < count; i++) {
            if (array[i].equals(element)) {
                idx = i;
                break;
            }
        }

        if (idx == -1)
            return false;

        for (int i = idx; i < count - 1; i++) {
            array[i] = array[i + 1];
        }

        array[--count] = null;

        if (count < array.length / 4 && array.length > DEFAULT_CAPACITY) {
            shrink();
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    private void shrink() {
        T[] newArray = (T[]) new Object[Math.max(DEFAULT_CAPACITY, array.length / 2)];
        System.arraycopy(array, 0, newArray, 0, count);
        array = newArray;
    }

    @SuppressWarnings("unchecked")
    private void expand() {
        int newCapacity = array.length * 2;
        T[] newArray = (T[]) new Object[newCapacity];
        System.arraycopy(array, 0, newArray, 0, count);
        array = newArray;
    }

    public boolean contains(T element) {
        for (int i = 0; i < count; i++) {
            if (array[i].equals(element))
                return true;
        }
        return false;
    }


    public int count() {
        return count;
    }

    public Object[] toArray() {
        Object[] returnedArray = new Object[count];
        System.arraycopy(array, 0, returnedArray, 0, count);
        return returnedArray;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    T elementAt(int idx) throws ArrayIndexOutOfBoundsException {
        if (idx >= count)
            throw new ArrayIndexOutOfBoundsException();
        return array[idx];
    }

    @SuppressWarnings("unchecked")
    static <T> void copyAll(ResizableArray<? super T> dest, ResizableArray<T> src) {
        if (dest == src) {
            Object[] copyable = src.toArray();
            for (Object o : copyable) {
                dest.addElement((T) o);
            }
        } else
            for (int i = 0; i < src.count(); i++) {
                dest.addElement(src.elementAt(i));
            }
    }
}

class IntegerArray extends ResizableArray<Integer> {
    public double sum() {
        int sum = 0;
        for (int i = 0; i < count(); i++) {
            sum += elementAt(i);
        }
        return sum;
    }

    public double mean() {
        int sum = 0;
        for (int i = 0; i < count(); i++) {
            sum += elementAt(i);
        }
        return (double) sum / count();
    }

    public int countNonZero() {
        int counter = 0;
        for (int i = 0; i < count(); i++) {
            if (elementAt(i) != 0)
                counter++;
        }
        return counter;
    }

    public IntegerArray distinct() {
        IntegerArray returnable = new IntegerArray();
        int j = 0;
        for (int i = 0; i < count(); i++) {
            if (!returnable.contains(elementAt(i))) {
                returnable.addElement(elementAt(i));
            }
        }
        return returnable;
    }

    public IntegerArray increment(int offset) {
        IntegerArray returnable = new IntegerArray();
        for (int i = 0; i < count(); i++) {
            returnable.addElement(elementAt(i) + offset);
        }
        return returnable;
    }
}

class ArrayTransformer {
    public <T, R> ResizableArray<R> map(ResizableArray<T> source, Function<? super T, ? extends R> mapper) {
        ResizableArray<R> returnable = new ResizableArray<>();
        for (int i = 0; i < source.count(); i++) {
            returnable.addElement(mapper.apply(source.elementAt(i)));
        }
        return returnable;
    }

    public <T> ResizableArray<T> filter(ResizableArray<T> source, Predicate<? super T> predicate) {
        ResizableArray<T> returnable = new ResizableArray<>();
        for (int i = 0; i < source.count(); i++) {
            if (predicate.test(source.elementAt(i)))
                returnable.addElement(source.elementAt(i));
        }
        return returnable;
    }

}

public class ResizableArrayTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int test = jin.nextInt();
        if (test == 0) { //test ResizableArray on ints
            ResizableArray<Integer> a = new ResizableArray<Integer>();
            System.out.println(a.count());
            int first = jin.nextInt();
            a.addElement(first);
            System.out.println(a.count());
            int last = first;
            while (jin.hasNextInt()) {
                last = jin.nextInt();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
        }
        if (test == 1) { //test ResizableArray on strings
            ResizableArray<String> a = new ResizableArray<String>();
            System.out.println(a.count());
            String first = jin.next();
            a.addElement(first);
            System.out.println(a.count());
            String last = first;
            for (int i = 0; i < 4; ++i) {
                last = jin.next();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
            ResizableArray<String> b = new ResizableArray<String>();
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));

            System.out.println(a.removeElement(first));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
        }
        if (test == 2) { //test IntegerArray
            IntegerArray a = new IntegerArray();
            System.out.println(a.isEmpty());
            while (jin.hasNextInt()) {
                a.addElement(jin.nextInt());
            }
            jin.next();
            System.out.println(a.sum());
            System.out.println(a.mean());
            System.out.println(a.countNonZero());
            System.out.println(a.count());
            IntegerArray b = a.distinct();
            System.out.println(b.sum());
            IntegerArray c = a.increment(5);
            System.out.println(c.sum());
            if (a.sum() > 100)
                ResizableArray.copyAll(a, a);
            else
                ResizableArray.copyAll(a, b);
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.contains(jin.nextInt()));
            System.out.println(a.contains(jin.nextInt()));
        }
        if (test == 3) { //test insanely large arrays
            LinkedList<ResizableArray<Integer>> resizable_arrays = new LinkedList<ResizableArray<Integer>>();
            for (int w = 0; w < 500; ++w) {
                ResizableArray<Integer> a = new ResizableArray<Integer>();
                int k = 2000;
                int t = 1000;
                for (int i = 0; i < k; ++i) {
                    a.addElement(i);
                }

                a.removeElement(0);
                for (int i = 0; i < t; ++i) {
                    a.removeElement(k - i - 1);
                }
                resizable_arrays.add(a);
            }
            System.out.println("You implementation finished in less then 3 seconds, well done!");
        }
    }

}
