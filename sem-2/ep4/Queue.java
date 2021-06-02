/*

class LinkedList<T>
{
    // stores the current value + next
    // so we need not have a bounded list (and it can start anywhere)

    // e.g.
    // - = links
    // * = deleted links
    // a * b - c - d - e - ... (can be as long as we want)
    // x   4   2   5   8

    LinkedList<T> next = null;
    T value;

    public void LinkedList(T val)
    {
        this.value = val;
    }

    public T getValue(int index)
    {
        LinkedList<T> current = this;
        while (index)
        {
            current = current.getNext();
            index--;
        }

        return current.value;
    }

    public int length()
    {
        if (this.endsList())
            return 1;

        return 1 + this.next.length();
    }

    public void addNewLink(LinkedList<T> newLink)
    {
        this.getLast().next = newLink;
    }

    LinkedList<T> getLast()
    {
        if (this.endsList())
            return this;
        
        return this.next.getLast();
    }

    public LinkedList<T> getNext()
    {
        return this.next;
    }

    public boolean endsList()
    {
        return this.next == null;
    }
}


abstract class GenericQueue<T>
{
    int cap;

    // start of linked list
    LinkedList<T> list = null;
    int smallest, largest;
    
    public void GenericQueue(int cap)
    {
        this.cap = cap;
    }

    public abstract int compare(T a, T b);

    public void enqueue (T s)
    {
        if (this.size() == this.cap)
            throw new Exception("Queue out of bounds!");

        if (this.isEmpty())
        {
            this.largest = 0;
            this.smallest = 0;
        }
        else
        {
            if (this.compare(
                this.list.getValue(this.largest), s
                ) < 0)
                this.largest = this.size() + 1;
            else if (this.compare(
                this.list.getValue(this.smallest), s
                ) > 0)
                this.smallest = this.size() + 1;
        }

        this.list.addNewLink(new LinkedList<T> s);
    }

    public T dequeue()
    {
        if (this.list == null)
            throw new Exception("Queue out of bounds!");

        T _return = this.pick();

        this.list = this.list.getValue(0);

        this.smallest--;
        this.largest--;

        if (this.smallest < 0)
            this.smallest = this.recalculate((a) -> a < 0);

        if (this.largest < 0)
            this.largest = this.recalculate((a) -> a > 0);

        return _return;
    }

    int recalculate(Function<int> comparison)
    {
        T _value = this.list.getValue(0);

        int _return = 0;

        for (int i = 1; i < this.size(); i++)
        {
            T current_value = this.list.getValue(i);
            if (comparison(
                    this.compare(current_value, _value)
            ))
            {
                _value = current_value;
                _return = i;
            }
        }

        return _return;
    }
    
    public boolean isEmpty()
    {
        return this.size() == 0;
    }

    public int size()
    {
        if (this.list == null)
            return 0;

        return this.list.length();
    }

    public T pick()
    {
        if (this.isEmpty())
            throw new Exception("Queue out of bounds!");

        return this.list.getValue();
    }

    public T maior()
    {
        return this.list.getValue(this.largest);
    }

    public T menor()
    {
        return this.list.getValue(this.smallest);
    }
}

*/
// nem testei o que tá em cima ok?

// To use custom comparison lambdas
interface IComparator
{
    boolean comparator(int result);
}


// Declare a new exception type
class QueueOutOfBoundsException extends Exception { 
    QueueOutOfBoundsException (String errorMessage) {
        super(errorMessage);
    }
}


abstract class StupidGenericQueue<T>
{
    // stupid cap for this THING
    private int stupidCap;

    // then two stupid indices
    private int stupidTopIndex = -1;
    private int stupidBottomIndex = -1;

    // and a f*ing stupid array
    private T[] stupidArray;

    // with two stupid indices for largest and smallest elements
    private int stupidSmallest, stupidLargest;
    
    StupidGenericQueue(int cap)
    {
        this.stupidCap = cap;
        // as java doesn't allow a generic array we have to type cast an object array
        this.stupidArray = (T[])new Object[this.stupidCap];
    }

    // this is needed as we don't really know what we're ordering
    public abstract int compare(T a, T b);

    public void enqueue(T s) throws QueueOutOfBoundsException
    {
        if (this.size() == this.stupidCap)
            throw new QueueOutOfBoundsException("Stupid queue out of bounds!");

        // if it's empty we set all entries to the utmost item
        if (this.isEmpty())
        {
            this.stupidLargest = this.stupidTopIndex + 1;
            this.stupidSmallest = this.stupidTopIndex + 1;
        }
        else
        {
            // else we test
            if (this.compare( // if largest < s...
                this.stupidArray[this.stupidLargest], s
                ) < 0)
                this.stupidLargest = this.stupidTopIndex + 1;
            else if (this.compare( // if smallest > s
                this.stupidArray[this.stupidSmallest], s
                ) > 0)
                this.stupidSmallest = this.stupidTopIndex + 1;
        }

        // then we check if the new item is in the STUPID array bounds
        if (++this.stupidTopIndex == this.stupidCap)
        {
            // and if it isn't we should start a new one
            T[] _temp = (T[])new Object[this.stupidCap];
            
            int i = 0;
            int j = this.stupidBottomIndex + 1;
            this.stupidTopIndex -= j;
            this.stupidSmallest -= j;
            this.stupidLargest  -= j;

            // populate it
            while (j < this.stupidCap)
                _temp[i++] = this.stupidArray[j++];

            this.stupidArray = _temp;
            
            // and move all indices (+1 as the bottom index always points to one less than we have in the array)
            this.stupidBottomIndex = -1;
        }

        // now we finally set this thing
        this.stupidArray[this.stupidTopIndex] = s;
    }

    public T dequeue() throws QueueOutOfBoundsException
    {
        if (this.size() == 0)
            throw new QueueOutOfBoundsException("Stupid queue out of bounds!");

        // get the first item
        T _return = this.pick();

        this.stupidBottomIndex++;

        if (!this.isEmpty())
        {
            // and now we check if we've lost the largest element
            if (this.stupidSmallest <= this.stupidBottomIndex)
                this.stupidSmallest = this.recalculate((a) -> a < 0); // in case we did, we should look for the smallest element
    
            if (this.stupidLargest <= this.stupidBottomIndex)
                this.stupidLargest = this.recalculate((a) -> a > 0);
        }

        return _return;
    }

    // this method receives a comparison lambda
    private int recalculate(IComparator comparison) throws QueueOutOfBoundsException
    {
        T _value = this.pick(); // initialize with the lowest value

        int _return = this.stupidBottomIndex + 1; // and with the lowest index

        for (int i = _return + 1; i < this.stupidTopIndex; i++) // then we traverse all elements
        {
            T current_value = this.stupidArray[i];
            if (comparison.comparator( // and check if, say, x < y (x - y = a < 0)
                    this.compare(current_value, _value)
            ))
            { // update our values
                _value = current_value;
                _return = i;
            }
        }

        return _return;
    }
    
    public boolean isEmpty()
    {
        return this.size() == 0;
    }

    public int size()
    { // as the bottom index is always one below the last item, we can just take the difference between the two indices
        return this.stupidTopIndex - this.stupidBottomIndex;
    }

    public T pick() throws QueueOutOfBoundsException
    {
        if (this.isEmpty())
            throw new QueueOutOfBoundsException("Queue out of bounds!");

        // blablabla
        return this.stupidArray[this.stupidBottomIndex + 1];
    }

    public T maior()
    {
        return this.stupidArray[this.stupidLargest];
    }

    public T menor()
    {
        return this.stupidArray[this.stupidSmallest];
    }

    public String toString()
    {
        String _return = "";

        int i = this.stupidBottomIndex;
        while (true)
        {
            _return += this.stupidArray[++i].toString();

            if (i < this.stupidTopIndex)
                _return += " ";
            else break;
        }

        return _return;
    }
}


class Pessoa
{
    private boolean isOld = false;
    private int arrival, passedBy;

    Pessoa(int arrival, boolean isOld)
    {
        this.arrival = arrival;
        this.isOld = isOld;
        this.passedBy = 0;
    }

    int chegada()
    {
        return this.arrival;
    }

    int passadoPor()
    {
        return this.passedBy;
    }

    void darVez()
    {
        this.passedBy++;
    }

    boolean idoso()
    {
        return this.isOld;
    }

    public String toString()
    {
        return Integer.toString(this.arrival);
    }
}


public class Queue extends StupidGenericQueue<Pessoa>
{
    Queue(int cap)
    {
        super(cap);
    }

    public int compare(Pessoa a, Pessoa b)
    {
        int arrival_a = a.chegada();
        int arrival_b = b.chegada();

        return (arrival_a == arrival_b) ? 0 : // if a == b -> a - b = 0 so we have a /0 which is not allowed
            (arrival_a - arrival_b)/Math.abs(arrival_a - arrival_b); // x/|x| gives us the sign of a - b
            
    }

    public static void main(String[] args) throws QueueOutOfBoundsException
    {
        int cap = Integer.parseInt(args[0]);

        Queue queue = new Queue(cap);

        while (!StdIn.isEmpty())
        {
            Pessoa new_person = new Pessoa(StdIn.readInt(), StdIn.readString().compareTo("idoso") == 0);

            // if they're not old
            if (!new_person.idoso())
            {
                // just put them in queue
                queue.enqueue(new_person);
            }
            else // if they're old...
            {
                // we must save this size as it changes during runtime but we
                // still must traverse the queue
                int totalLen = queue.size();

                Pessoa[] buffer = new Pessoa[totalLen];
                int count = 0;
                boolean requeued = false; // keeps track if we've requeued or not

                // as we have to access every index until we find
                // someone who has given way for two old people

                for (int i = 0; i < totalLen; i++)
                {
                    Pessoa current = queue.pick();
                    if (current.idoso() || current.passadoPor() < 2)
                    {
                        buffer[count++] = queue.dequeue();
                        //if (!queue.isEmpty())
                        //{
                        //    StdOut.println("\nlargest element: " + queue.maior());
                        //    StdOut.println("smallest element: " + queue.menor());
                        //    StdOut.println("current queue: " + queue);
                        //}
                    }
                    else // then we've finished our traversal
                    {
                        while (i < totalLen)
                        {
                            buffer[count++] = queue.dequeue();
                            i++;
                        }
                        requeue(queue, buffer, count, new_person);
                        requeued = true;
                        break;
                    }
                }

                if (!requeued)
                    requeue(queue, buffer, count, new_person);

                //StdOut.println(queue);
            }
        }
        
        StdOut.println("\nlargest element: " + queue.maior());
        StdOut.println("smallest element: " + queue.menor());
        StdOut.println("final queue: " + queue);
    }

    // requeues people that have been dequeued according to priority
    private static void requeue(Queue queue, Pessoa[] list, int count,
    Pessoa new_person) throws QueueOutOfBoundsException
    {
        // this keeps track of whether the new person has been queued
        boolean queued = false;

        for (int i = 0; i < count; i++)
        {
            Pessoa current = list[i];
            if (!current.idoso())
            {
                // if they've been queued...
                if (queued)
                    current.darVez();
                // if they haven't AND the current person has not been surpassed >= 2
                else if (current.passadoPor() < 2)
                {
                    current.darVez();
                    queue.enqueue(new_person);
                    queued = true;
                }
            }

            queue.enqueue(current);
        }
        // if the new person has to be added as the last
        if (!queued)
            queue.enqueue(new_person);
    }
}
