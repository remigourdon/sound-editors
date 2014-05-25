package framework.util;

/**
 * Utility class representing a ring buffer.
 */
public class RingBuffer {
    /**
     * Create an empty RingBuffer object of the specified capacity.
     * @param  c the capacity
     */
    public RingBuffer(int c) {
        capacity    = c;
        size        = 0;
        first       = 0;
        last        = 0;
        buffer      = new Double[capacity];
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
     */
    public void enqueue(Double x) {
        buffer[last]    = x;
        last            = (last + 1) % capacity;
        size++;
    }

    /**
     * Delete and return the item at the front of the list.
     * @return the deleted item
     */
    public Double dequeue() {
        Double item = buffer[first];
        first       = (first + 1) % capacity;
        size--;
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