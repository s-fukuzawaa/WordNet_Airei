import java.util.ArrayList;
import java.util.Iterator;

public class WordNet
{    
	private LinearProbingHashST <String, ArrayList<Integer>> synsets;
	private LinearProbingHashST <Integer,ArrayList<String>> sap;
	private SAP hypernyms;
    public WordNet(String synsets, String hypernyms)
    {
    	// TODO:  You may use the code below to open and parse the
    	// synsets and hypernyms file.  However, you MUST add your
    	// own code to actually store the file contents into the
    	// data structures you create as fields of the WordNet class.
    	if(synsets.equals(null)||hypernyms.equals(null))
    	{
    		throw new java.lang.NullPointerException();
    	}
    	
    	this.synsets= new LinearProbingHashST<String, ArrayList<Integer>>();
    	this.sap= new LinearProbingHashST <Integer,ArrayList<String>>();
        // Parse synsets
        int largestId = -1;				// TODO: You might find this value useful 
        In inSynsets = new In(synsets);
        while (inSynsets.hasNextLine())
        {
            String line = inSynsets.readLine();
            String[] tokens = line.split(",");
            
            // Synset ID
            int id = Integer.parseInt(tokens[0]);
            if (id > largestId)
            {
                largestId = id;
            }
            // Nouns in synset
            String synset = tokens[1];
            String[] nouns = synset.split(" ");
            
            ArrayList<String> tem= new ArrayList<String>();
            
            for (String noun : nouns)
            {
            	tem.add(noun);
               // TODO: you should probably do something here
            	if(this.synsets.contains(noun))
            	{
            		ArrayList<Integer> temp=  this.synsets.get(noun);
            		temp.add(id);
            		this.synsets.put(noun,temp);
            	}
            	else
            	{
            		ArrayList<Integer> temp2= new ArrayList<Integer>();
            		temp2.add(id);
                	this.synsets.put(noun,temp2);
            	}
            	
            }
            this.sap.put(id, tem);
            
            // tokens[2] is gloss, but we're not using that
        }
        inSynsets.close();
        // Parse hypernyms
        
        Digraph temp= new Digraph(this.synsets.size());
        In inHypernyms = new In(hypernyms);
        while (inHypernyms.hasNextLine())
        {
            String line = inHypernyms.readLine();
            String[] tokens = line.split(",");
            
            int v = Integer.parseInt(tokens[0]);
            DirectedDFS check= new DirectedDFS(temp,v);
           
            for (int i=1; i < tokens.length; i++)
            {
            	DirectedDFS check2= new DirectedDFS(temp,Integer.parseInt(tokens[i]));
            	if(check.marked(Integer.parseInt(tokens[i]))||check2.marked(v))
            	{
            		throw new java.lang.IllegalArgumentException();
            	}
            	else
            	{
            		temp.addEdge(v, Integer.parseInt(tokens[i]));
            	}
            }
        }
        inHypernyms.close();
        
        
        this.hypernyms= new SAP(temp);
        
        Topological t= new Topological(temp);
        int root=0;
        ArrayList<Integer> toporder= new ArrayList<Integer>();
        Iterator<Integer> path= t.order().iterator();
        
        while(path.hasNext())
        {
        	int ordercheck=path.next();
        	root=ordercheck;
        	if(temp.adj(ordercheck).iterator().hasNext())
        	{
        		toporder.add(ordercheck);
        	}
        }
        
        DirectedDFS toptobottom= new DirectedDFS(temp,toporder.get(0));
        
        if(temp.adj(root).iterator().hasNext()==true||!toptobottom.marked(root))
        {
        	throw new java.lang.IllegalArgumentException();
        }
        
        
        // TODO: Remember to remove this when your constructor is done!
    }

    public Iterable<String> nouns()
    {
    	return this.synsets.keys();
    }

    public boolean isNoun(String word)
    {
    	if(word==null)
    	{
    		throw new java.lang.NullPointerException();
    	}
    	return synsets.contains(word);
    }

    public int distance(String nounA, String nounB)
    {
    	if(nounA==null||nounB==null)
    	{
    		throw new java.lang.NullPointerException();
    	}
    	if(this.synsets.contains(nounA)==false||this.synsets.contains(nounB)==false)
    	{
    		throw new java.lang.IllegalArgumentException();
    	}
    	Iterable<Integer> a=(Iterable<Integer>) this.synsets.get(nounA);
    	Iterable<Integer> b=(Iterable<Integer>) this.synsets.get(nounB);
    	int result= hypernyms.length(a, b);
    	
    	return result;
    }

    public String sap(String nounA, String nounB)
    {
    	if(nounA==null||nounB==null)
    	{
    		throw new java.lang.NullPointerException();
    	}
    	if(this.synsets.contains(nounA)==false||this.synsets.contains(nounB)==false)
    	{
    		throw new java.lang.IllegalArgumentException();
    	}
    	Iterable<Integer> a=(Iterable<Integer>) this.synsets.get(nounA);
    	Iterable<Integer> b=(Iterable<Integer>) this.synsets.get(nounB);
    	int result= hypernyms.ancestor(a, b);
    	String answer="";
    	answer=sap.get(result).get(0);
    	if(sap.get(result).size()>1)
    	{
    		for(int i=1; i<sap.get(result).size(); i++)
    		{
        		answer= answer+" "+sap.get(result).get(i);
        	}
        	
    	}
    	
    	
    	return answer;
    	
    }
    
    private void testNouns(String nounA, String nounB)
    {
        System.out.print("'" + nounA + "' and '" + nounB + "': ");
        System.out.print("sap: '" + sap(nounA, nounB));
        System.out.println("', distance=" + distance(nounA, nounB));
    }

    // for unit testing of this class
    public static void main(String[] args)
    {
		String synsetsFile = "testInput/synsets15.txt";
		String hypernymsFile = "testInput/hypernyms15Path.txt";

		WordNet wordnet = new WordNet(synsetsFile, hypernymsFile);
       /* wordnet.testNouns("municipality", "region");
        wordnet.testNouns("individual", "edible_fruit");
        wordnet.testNouns("Black_Plague", "black_marlin");
        wordnet.testNouns("American_water_spaniel", "histology");
        wordnet.testNouns("Brown_Swiss", "barrel_roll");
        
        wordnet.testNouns("chocolate", "brownie");
        wordnet.testNouns("cookie", "brownie");
        wordnet.testNouns("martini", "beer");*/
		wordnet.testNouns("a", "invalid");
    }
}