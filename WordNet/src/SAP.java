public class SAP
{
    public SAP(Digraph G)
    {
    	throw new UnsupportedOperationException();
    }

    public int length(int v, int w)
    {
    	throw new UnsupportedOperationException();
    }

    public int ancestor(int v, int w)
    {
    	throw new UnsupportedOperationException();
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w)
    {
    	throw new UnsupportedOperationException();
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
    	throw new UnsupportedOperationException();
    }

    public static void main(String[] args)
    {
    	String digraphFile = "testInput/digraph1.txt";
    	
        In in = new In(digraphFile);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty())
        {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
