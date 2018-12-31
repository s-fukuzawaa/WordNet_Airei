import java.util.Iterator;

public class SAP
{
	private Digraph copy;
    public SAP(Digraph G)
    {
    	this.copy=new Digraph(G);
    }

    private int[] commonancestor(int v, int w)
    {
    	int[] result= new int[2];
    	int length=-1;
    	int common=-1;
    	
    	BreadthFirstDirectedPaths vpath= new BreadthFirstDirectedPaths(copy,v);
    	BreadthFirstDirectedPaths wpath= new BreadthFirstDirectedPaths(copy,w);
    	
    	for(int i=0; i<copy.V(); i++)
    	{
    		if(vpath.hasPathTo(i)&&wpath.hasPathTo(i))
    		{
    			if(common<0)
    			{
    				common=i;
    				length=vpath.distTo(common)+wpath.distTo(common);
    			}
    			else if(vpath.distTo(i)+wpath.distTo(i)<length)
    			{
    				common=i;
    				length=vpath.distTo(i)+wpath.distTo(i);
    			}
    		}
    	}
    	result[0]=common;
    	result[1]=length;
    	
    	return result;
    	
    }
    
    //use breadthFirst method with 1 int
    public int length(int v, int w)
    {
    	return commonancestor(v,w)[1];
    }

    public int ancestor(int v, int w)
    {
    	return commonancestor(v,w)[0];
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
