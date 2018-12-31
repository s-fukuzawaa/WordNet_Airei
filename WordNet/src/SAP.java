import java.util.Iterator;

public class SAP
{
	private Digraph copy;
    public SAP(Digraph G)
    {
    	this.copy=new Digraph(G);
    }

    private int commonancestor(int v, int w, int index)
    {
    	BreadthFirstDirectedPaths help= new BreadthFirstDirectedPaths(copy,w);
    	Iterator<Integer> temp=copy.adj(index).iterator();
    	while(temp.hasNext())
    	{
    		if(help.hasPathTo(temp.next()))
    		{
    			return temp.next();
    		}
    		else
    		{

    	    	commonancestor(v,w,temp.next());
    		}
    	}
    	return -1;
    }
    
    //use breadthFirst method with 1 int
    public int length(int v, int w)
    {
    	int a=commonancestor(v,w,v);
    	if(a==-1)
    	{
    		return a;
    	}
    	BreadthFirstDirectedPaths vpath= new BreadthFirstDirectedPaths(copy,v);
    	BreadthFirstDirectedPaths wpath= new BreadthFirstDirectedPaths(copy,w);
    	
    	return vpath.distTo(a)+wpath.distTo(a);
    }

    public int ancestor(int v, int w)
    {
    	return commonancestor(v,w,v);
    }
    
    
    //use breadthFirst method with integer iterable

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
//        while (!StdIn.isEmpty())
  //      {
            int v = 1;//StdIn.readInt();
            int w = 2;//StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    //    }
    }
}
