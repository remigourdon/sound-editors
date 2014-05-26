package framework.util;

/**
 * Utility class representing a ring buffer.
 */
public class RingBuffer {
    /**
     * Create an empty RingBuffer object of the specified capacity.
     * @param  c the capacity
     * @postcondition size == 0 && first == 0 && last == 0
     */
    public RingBuffer(int c) {
        capacity    = c;
        size        = 0;
        first       = 0;
        last        = 0;
        buffer      = new Double[capacity];
        assert size == 0 && first == 0 && last == 0:
            "violated postcondition: size == 0 && first == 0 && last == 0";
    }

    /**
     * Get the current number of items stored in the buffer.
     * @return the number of items
     */
    public int size() {
        return size;
    }

    /**
     * Test if the buffer is empty.
     * @return true if is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Test if the buffer is full.
     * @return true if it is full, false otherwise
     */
    public boolean isFull() {
        return size == capacity;
    }

    /**
     * Add item at the end of the list.
     * @param x the item to be added
     * @precondition    size < capacity
     * @postcondition   size > 0 && size <= capacity
     * @postcondition   last >= 0 && last <= capacity
     */
    public void enqueue(Double x) {
        assert size < capacity: "violated precondition: size < capacity";

        buffer[last]    = x;
        last            = (last + 1) % capacity;
        size++;

        assert size > 0 && size <= capacity:
            "violated postcondition: size > 0 && size <= capacity";
        assert last >= 0 && last <= capacity:
            "violated postcondition: last >= 0 && last <= capacity";
    }

    /**
     * Delete and return the item at the front of the list.
     * @return the deleted item
     * @precondition size > 0
     * @postcondition size >= 0 && size < capacity
     * @postcondition first >= 0 && first <= capacity
     */
    public Double dequeue() {
        assert size > 0: "violated precondition: size > 0";

        Double item = buffer[first];
        first       = (first + 1) % capacity;
        size--;

        assert size >= 0 && size < capacity:
            "violated postcondition: size >= 0 && size < capacity";
        assert first >= 0 && first <= capacity:
            "violated postcondition: first >= 0 && first <= capacity";
        return item;
    }

    /**
     * Return but do not delete the item at the front of the list.
     * @return the item at the front
     */
    public Double peek() {
        return buffer[first];
    }

    Double[]    buffer;
    int         capacity;
    int         first;
    int         last;
    int         size;
}