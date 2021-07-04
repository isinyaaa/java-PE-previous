public class LinkedStackString
{
    private int n;
    private Node first;
    private Node max;

    private class Node
    {
        private String item;
        private Node next;
    }

    public LinkedStackString()
    {
        first = null;
        max = null;
        n = 0;
    }

    public boolean isEmpty() {
        return (first == null);
    }

    public int size() {
        return n;
    }

    public String peek() {
        return first.item;
    }

    public void push(String novo)
    {
        Node oldfirst = first;
        first = new Node();
        first.next = oldfirst;

        first.item = novo;
        pushMax(novo);

        n++;
    }

    public void pushMax(String item)
    {
        Node oldmax = max;
        max = new Node();
        max.next = oldmax;

        if (n > 0)
        {
            if (item.compareTo(oldmax.item) > 0)
            max.item = item;
            else
            max.item = oldmax.item;
        }
        else
            max.item = item;
    }

    public String getMax()
    {
        return max.item;
    }

    public String pop()
    {
        String item = first.item;
        first = first.next;
        max = max.next;
        n--;

        return item;
    }

    public String toString()
    {
        String s="";

        for (Node x = first; x!= null; x = x.next)
            s += x.item + " ";

        return s;
    }

    public static void main(String[] args){

        LinkedStackString stack = new LinkedStackString();

        while(!StdIn.isEmpty())
        {
            String item = StdIn.readString();

            if (!item.equals("-"))
                stack.push(item);
            else if (!stack.isEmpty())
                StdOut.print(stack.pop() + " ");
        }

        StdOut.println();
        StdOut.println("------");
        StdOut.println(stack.size() + " sobraram na pilha: ");
        StdOut.println(stack.toString());
        StdOut.println("Max = " + stack.getMax());

    }
}
