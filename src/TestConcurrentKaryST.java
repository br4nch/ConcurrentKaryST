import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class TestConcurrentKaryST extends ConcurrentKaryST implements Runnable
{
	int threadId;
	public static final int NUM_OF_THREADS=5;
	
	public TestConcurrentKaryST(int threadId)
	{
		this.threadId = threadId;
	}
	
	final void getUserInput(int fileNumber)
	{
		String in="";
		String operation="";
		StringTokenizer st;
		FileInputStream fs;
		try
		{
			fs = new FileInputStream("in" + fileNumber + ".txt");
			DataInputStream ds = new DataInputStream(fs);
			BufferedReader reader = new BufferedReader(new InputStreamReader(ds));
			while(!(in = reader.readLine()).equalsIgnoreCase("quit"))
			{
				st = new StringTokenizer(in);
				operation = st.nextToken();
				if(operation.equalsIgnoreCase("Find"))
				{
					obj.lookup(grandParentHead,Long.parseLong(st.nextToken()));
				}
				else if(operation.equalsIgnoreCase("Insert"))
				{
					obj.insert(grandParentHead,Long.parseLong(st.nextToken()),threadId);
				}
				else if(operation.equalsIgnoreCase("Delete"))
				{
					//delete(parentHead.c0,parentHead,grandParentHead,Long.parseLong(st.nextToken()));
				}

			}
			ds.close();
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	
	public void run()
	{
		getUserInput(this.threadId);
	}
	
	public static void main(String[] args)
	{


		obj = new ConcurrentKaryST();
		obj.createHeadNodes();

		Thread[] arrayOfThreads = new Thread[NUM_OF_THREADS];

		for(int i=0;i<NUM_OF_THREADS;i++)
		{
			arrayOfThreads[i] = new Thread(  new TestConcurrentKaryST(i));
			arrayOfThreads[i].start();
		}
		
		try
		{
			for(int i=0;i<NUM_OF_THREADS;i++)
			{
				arrayOfThreads[i].join();
				System.out.println("Thread " + i + " is done");
			}
			//obj.printPreorder(ConcurrentKaryST.grandParentHead);
			//obj.printOnlyKeysPreorder(ConcurrentKaryST.grandParentHead);
			obj.nodeCount(ConcurrentKaryST.grandParentHead);
			System.out.println(ConcurrentKaryST.nodeCount);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}

	}
}
