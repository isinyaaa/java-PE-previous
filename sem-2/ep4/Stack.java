// Declare a new exception type
class StackOutOfBoundsException extends Exception { 
    StackOutOfBoundsException (String errorMessage) {
        super(errorMessage);
    }
}

abstract class GenericStack<T>
{
    // records the total size of the structure
    private int size;

    // holds the elements
    private T[] elements;
    
    // records the last element's index
    private int topmost = -1;

    // records the largest/smallest elements
    // updates at every push
    private T[] largest;
    private T[] smallest;

    GenericStack(int cap)
    {
        this.size = cap;
        // as java doesn't allow a generic array we have to type cast an object array
        this.elements = (T[])new Object[this.size];
        this.largest  = (T[])new Object[this.size];
        this.smallest = (T[])new Object[this.size];
    }

    // this is needed as we don't really know what we're ordering
    abstract int compare(T a, T b);

    public void push(T s) throws StackOutOfBoundsException
    {
        if (this.topmost == this.size - 1)
            throw new StackOutOfBoundsException("No more room!");

        if (this.isEmpty())
        {
            this.topmost++;
            this.largest[this.topmost] = s;
            this.smallest[this.topmost] = s;
        }
        else
        {
            T _largest = this.largest[this.topmost];
            T _smallest = this.smallest[this.topmost];

            if (this.compare(_largest, s) < 0) // largest < s
                _largest = s;
            else if (this.compare(_smallest, s) > 0) // smallest > s
                _smallest = s;

            this.topmost++;

            this.smallest[this.topmost] = _smallest;
            this.largest[this.topmost] = _largest;
        }

        this.elements[this.topmost] = s;
    }

    public T pop() throws StackOutOfBoundsException
    {
        if (this.isEmpty())
            throw new StackOutOfBoundsException("No more room!");

        return this.elements[this.topmost--];
    }

    public boolean isEmpty()
    {
        return this.topmost == -1;
    }

    public int size()
    {
        return this.topmost + 1;
    }

    public T pick() throws StackOutOfBoundsException
    {
        if (this.isEmpty())
            throw new StackOutOfBoundsException("No more room!");

        return this.elements[this.topmost];
    }

    public T maior() throws StackOutOfBoundsException
    {
        if (this.isEmpty())
            throw new StackOutOfBoundsException("No more room!");

        return this.largest[this.topmost];
    }

    public T menor() throws StackOutOfBoundsException
    {
        if (this.isEmpty())
            throw new StackOutOfBoundsException("No more room!");

        return this.smallest[this.topmost];
    }

}


public class Stack extends GenericStack<String>
{
    Stack(int cap)
    {
        super(cap);
    }

    int compare(String a, String b)
    {
        return a.compareToIgnoreCase(b);
    }

    public static void main(String[] args) throws StackOutOfBoundsException
    {
        int cap = Integer.parseInt(args[0]);

        Stack stack = new Stack(cap);

        while (!StdIn.isEmpty())
        {
            stack.push(StdIn.readString());
        }

        int size = stack.size();
        
        Stack stack2 = new Stack(size);

        // just like flipping a pile of books
        for (int i = 0; i < size; i++)
        {
            String s = stack.pop();
            StdOut.println(s);
            stack2.push(s);
        }

        StdOut.println();

        // then we flip it in reverse
        for (int i = 0; i < size; i++)
        {
            String s = stack2.pop();
            StdOut.println(s);
            stack.push(s);
        }

        StdOut.println("\nmaior: " + stack.maior());
        StdOut.println("menor: " + stack.menor());
    }
}
