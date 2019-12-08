import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String c[]) throws IOException{
		try{
//	     Console console = System.console();
	     
	     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//	     String console = br.readLine();
	     String[] inputArgs=null;
	     String console=null;
	     Command cmd=null;
	     ICanvas canvas=null;
	    	 do{
	    		 console = br.readLine();
	    		 //Create Command
	    		 if(null!=console){
	    			 inputArgs=console.split(" "); 
	    		 	 try {
						cmd = ICommandFactory.getCommand(inputArgs);
					 } catch (InCorrectCommandInput e) {
						System.out.println("Incorrect Command Input. Quitting.");
						cmd=Command.QUIT;
					 }
		     	 }
		     	 else{
	    			 System.out.println("Error reading console. Quitting.");
	    			 cmd=Command.QUIT;
	    		 }
	    		 // Create canvas
	    		 if(cmd==Command.QUIT)
	    			 canvas=null;
	    		 else
	    			 canvas=MatrixCanvas2D.getInstance(cmd,new TwoDCordinate<Integer>(new Integer(inputArgs[1]),new Integer(inputArgs[2])));
	    		 
	    		 //Command Execution
	    		 if(!CommandServiceImpl.executeService(cmd,canvas)){
	    			 System.out.println("Error Executing Command");
	    		 }else{
	    			 canvas.drawCanvas();
	    		 }
	    	 }while(cmd!=Command.QUIT);
	    	 
		}catch(Exception e){
			System.out.println("Error executing...");
			e.printStackTrace();
		}
	     
	}
}




