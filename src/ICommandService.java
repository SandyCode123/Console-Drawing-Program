import java.util.Stack;

interface ICommandService<C,D>{
	boolean validate(ICommandInput<C> input,ICanvas<C, D> canvas);	
	boolean execute(ICommandInput<C> input, ICanvas<C, D> canvas);
	default boolean executeCommand(ICommandInput<C> input,ICanvas<C,D> canvas){
		if(validate(input,canvas)){
			return execute(input,canvas);
		}else{
			return false;
		}
	}
}
enum CommandServiceImpl implements ICommandService{
	CANVAS_DRAW_IMPL{
		@Override
		public boolean execute(ICommandInput input, ICanvas canvas) {
			boolean status=true;
			try{
				Object[][] data = canvas.getCanvasData();
				int height=data.length;
				int width=data[0].length;
					for(int i=0;i< width;i++){
						data[0][i]=ICommandInput.DASH_PIXEL;
						data[height-1][i]=ICommandInput.DASH_PIXEL;
					}
					for(int i=1;i<height-1;i++){
						data[i][0]=ICommandInput.PIPE_PIXEL;
						data[i][width-1]=ICommandInput.PIPE_PIXEL;
					}
			}catch(Exception e){
				// log exception somewhere
				status=false;
			}
			return status;
		}

		@Override
		public boolean validate(ICommandInput input, ICanvas canvas) {
			boolean status=true;
			try{
				ICoordinate dim=input.getInputDimensions()[0];
				int width = Integer.parseInt((String)dim.getX());
				int height = Integer.parseInt((String)dim.getY());
				if(width<0 || height<0)
					status= false;
			}catch(CordinateNotSupportedException e){
				status= false;
			}
			return status;
		}

		
	}
	,LINE_DRAW_IMPL{
		@Override
		public boolean validate(ICommandInput input, ICanvas canvas) {
			try{
			if(canvas==null){
				System.out.println("Draw Canvas First...");
				return false;
			}
			ICoordinate startPoint=input.getInputDimensions()[0];
			int xStart = Integer.parseInt((String)startPoint.getX());
			int yStart = Integer.parseInt((String)startPoint.getY());
			
			ICoordinate endPoint=input.getInputDimensions()[1];
			int xEnd = Integer.parseInt((String)endPoint.getX());
			int yEnd = Integer.parseInt((String)endPoint.getY());
			
			TwoDCordinate dim = (TwoDCordinate)canvas.getCanvasDimension();
			int width=(Integer)(dim.getX());
			int height=(Integer)(dim.getY());
			if(xStart<=0 || xStart>= (width-1) || yStart<=0 || yStart>= (height-1)){
				System.out.println("Provide Inputs within canvas boundary. Inputs on Canvas Boundary not legal.");
				return false;
			}
			if(xEnd<=0 || xEnd>= (width-1) || yEnd<=0 || yEnd>= (height-1)){
				System.out.println("Provide Inputs within canvas boundary. Inputs on Canvas Boundary not legal.");
				return false;
			}
		} catch (NumberFormatException | CordinateNotSupportedException e) {
			System.out.println("");
			return false;
		}
			return true;
		}
		@Override
		public boolean execute(ICommandInput input, ICanvas canvas) {
				boolean status=true;
				try{
				ICoordinate startPoint=input.getInputDimensions()[0];
				int xStart = Integer.parseInt((String)startPoint.getX());
				int yStart = Integer.parseInt((String)startPoint.getY());
				
				ICoordinate endPoint=input.getInputDimensions()[1];
				int xEnd = Integer.parseInt((String)endPoint.getX());
				int yEnd = Integer.parseInt((String)endPoint.getY());
				int slope=0;
				boolean isInfiniteSlope=false;
				if(xEnd==xStart)// Infinite slope. Verticle Line
				{
					isInfiniteSlope=true;
				}else{
					slope=(yEnd-yStart)
					/
					(xEnd-xStart);
				}
				int x1=xStart;
				int y1=yStart;
				int s1=0;
				canvas.updateCanvasBase(new TwoDCordinate<Integer>(x1, y1), ICommandInput.STAR_PIXEL);
				while(x1!=xEnd || y1!=yEnd){
					if(isInfiniteSlope){
						y1=moveCordinate(yStart,yEnd,y1);
						canvas.updateCanvasBase(new TwoDCordinate<Integer>(x1, y1), ICommandInput.STAR_PIXEL);
					}
					else{
						x1=moveCordinate(xStart,xEnd,x1);
						s1=calculateSlope(xStart,x1,yStart,y1);
						if(s1!=slope){
							y1=moveCordinate(yStart,yEnd,y1);
							s1=calculateSlope(xStart,x1,yStart,y1);
							if(s1!=slope){
								System.out.println("Could not identify next point of line");
								break;
							}else{
//								System.out.println(x1+","+y1);
								canvas.updateCanvasBase(new TwoDCordinate<Integer>(x1, y1), ICommandInput.STAR_PIXEL);
								//update matrix
							}
						}else{
//							System.out.println(x1+","+y1);
							canvas.updateCanvasBase(new TwoDCordinate<Integer>(x1, y1), ICommandInput.STAR_PIXEL);
							
							// update matrix
						}
					}
				}
				}catch(Exception e){
					// log exception somewhere
					System.out.println("Not possible to draw for a given input.");
					status=false;
				}
				return status;
		}
	},RECTANGLE_DRAW_IMPL{
		@Override
		public boolean execute(ICommandInput input, ICanvas canvas) {
			boolean status=true;
			try{
			ICoordinate cornerPoint1=input.getInputDimensions()[0];
			int Inputpoint1X = Integer.parseInt((String)cornerPoint1.getX());
			int Inputpoint1Y = Integer.parseInt((String)cornerPoint1.getY());
			
			ICoordinate cornerPoint2=input.getInputDimensions()[1];
			int Inputpoint2X = Integer.parseInt((String)cornerPoint2.getX());
			int Inputpoint2Y = Integer.parseInt((String)cornerPoint2.getY());
			
			int Intersectpoint1X = Inputpoint2X;
			int Intersectpoint1Y = Inputpoint1Y;
			
			int Intersectpoint2X = Inputpoint1X;
			int Intersectpoint2Y = Inputpoint2Y;
			
			ICommand lineCommand1,lineCommand2,lineCommand3,lineCommand4;
			try {
				lineCommand1 = ICommandFactory.getCommand(new String[]{ICommandFactory.LINE_DRAW,Inputpoint1X+"",Inputpoint1Y+"",Intersectpoint1X+"",Intersectpoint1Y+""});
				CommandServiceImpl.executeService(lineCommand1,canvas);
				lineCommand2 = ICommandFactory.getCommand(new String[]{ICommandFactory.LINE_DRAW,Inputpoint1X+"",Inputpoint1Y+"",Intersectpoint2X+"",Intersectpoint2Y+""});
				CommandServiceImpl.executeService(lineCommand2,canvas);
				lineCommand3 = ICommandFactory.getCommand(new String[]{ICommandFactory.LINE_DRAW,Inputpoint2X+"",Inputpoint2Y+"",Intersectpoint1X+"",Intersectpoint1Y+""});
				CommandServiceImpl.executeService(lineCommand3,canvas);
				lineCommand4 = ICommandFactory.getCommand(new String[]{ICommandFactory.LINE_DRAW,Inputpoint2X+"",Inputpoint2Y+"",Intersectpoint2X+"",Intersectpoint2Y+""});
				CommandServiceImpl.executeService(lineCommand4,canvas);
			} catch (InCorrectCommandInput e) {
				status=false;
				System.out.println("Incorrect Command Input while Executing.");
			}
			}catch(Exception e){
				System.out.println("Not possible to draw for a given input.");
				status=false;
			}
			return status;
		}

		@Override
		public boolean validate(ICommandInput input, ICanvas canvas) {
			try {
			if(canvas==null){
				System.out.println("Draw Canvas First...");
				return false;
			}
			ICoordinate startPoint=input.getInputDimensions()[0];
			int xStart;
			
				xStart = Integer.parseInt((String)startPoint.getX());
			
			int yStart = Integer.parseInt((String)startPoint.getY());
			
			ICoordinate endPoint=input.getInputDimensions()[1];
			int xEnd = Integer.parseInt((String)endPoint.getX());
			int yEnd = Integer.parseInt((String)endPoint.getY());
			
			TwoDCordinate dim = (TwoDCordinate)canvas.getCanvasDimension();
			int width=(Integer) dim.getX();
			int height=(Integer)(dim.getY());
			if(xStart<=0 || xStart>= (width-1) || yStart<=0 || yStart>= (height-1)){
				System.out.println("Provide Inputs within canvas boundary. Inputs on Canvas Boundary not legal.");
				return false;
			}
			if(xEnd<=0 || xEnd>= (width-1) || yEnd<=0 || yEnd>= (height-1)){
				System.out.println("Provide Inputs within canvas boundary. Inputs on Canvas Boundary not legal.");
				return false;
			}
			} catch (NumberFormatException | CordinateNotSupportedException e) {
				System.out.println("");
				return false;
			}
			return true;
		}
	},COLOR{

		@Override
		public boolean validate(ICommandInput cmd, ICanvas canvas) {
			return true;
		}

		@Override
		public boolean execute(ICommandInput input, ICanvas canvas) {
			try{
			ICoordinate[] dim=input.getInputDimensions();
			TwoDCordinate canvasDim = (TwoDCordinate)canvas.getCanvasDimension();
			int x=Integer.parseInt((String)(dim[0].getX()));
			int y=Integer.parseInt((String)(dim[0].getY()));
			String color=((ColorFillInput)input).getColorPixel();
			int w=(Integer)canvasDim.getX();
			int h=(Integer)canvasDim.getY();
			
		    Stack s= new Stack<ICoordinate>();
		    s.add(new TwoDCordinate(x,y));
		    while(!s.isEmpty()){
		    	ICoordinate cordinate = (ICoordinate) s.pop();
		        int iX=(Integer)cordinate.getX();
		        int jY=(Integer)cordinate.getY();
		        
		        if(canvas.getDataAt(iX,jY) != null)
		            continue;
	
		        // find left boundary
		        while ( iX > 0){
		        	if(canvas.getDataAt(iX,jY)==null)
		        		iX -= 1;
		        	else{
		        		iX+=1;
		        		break;
		        	}
		        }
		        // scan to right
		        boolean up=true;
		        boolean down = true;
		        for(int horizontal=iX+1;horizontal < w-1 && canvas.getDataAt(horizontal,jY)==null;horizontal++)
		        {
		        	canvas.updateCanvasBase(new TwoDCordinate(horizontal,jY),color);
		        
		            // detect color change down
		            if (jY + 1 < h-1){
		                if (down && canvas.getDataAt(horizontal,jY+1)==null)
		                	s.add(new TwoDCordinate(horizontal,jY+1));
		                down = canvas.getDataAt(horizontal,jY+1)!=null;
		            }
		            
		            // detect color change above
		            if( horizontal > 0)
		                if (up && canvas.getDataAt(horizontal,jY-1)==null)
		                    s.add(new TwoDCordinate(horizontal,jY-1));
		                up = canvas.getDataAt(horizontal,jY-1)!=null;
	
		        }
		    }
			} catch (NumberFormatException | CordinateNotSupportedException e) {
				System.out.println("Error while coloring");
				return false;
			}
			return true;
		}
		
	},
	QUIT_IMPL{
		@Override
		public boolean execute(ICommandInput cmd, ICanvas canvas) {
			return true;
		}

		@Override
		public boolean validate(ICommandInput cmd, ICanvas canvas) {
			return true;
		}
	};
	
	public static boolean executeService(ICommand cmd, ICanvas canvas) {
		boolean status=true;
		if(Command.CANVAS_DRAW==cmd){
			status=CANVAS_DRAW_IMPL.executeCommand(cmd.getInput(), canvas);
		}else if(Command.LINE_DRAW==cmd){
			status=LINE_DRAW_IMPL.executeCommand(cmd.getInput(), canvas);
		}else if(Command.RECTANGLE_DRAW==cmd){
			status=RECTANGLE_DRAW_IMPL.executeCommand(cmd.getInput(), canvas);
		}else if(Command.COLOR==cmd){
			status=COLOR.executeCommand(cmd.getInput(), canvas);
		}else if(Command.QUIT==cmd){
			status=QUIT_IMPL.executeCommand(cmd.getInput(), canvas);
		}
		return status;
	}
	public static int moveCordinate(int Start,int End, int c){
		if(End>Start){
			c=c+1;
		}else{
			c=c-1;
		}
		return c;
	}
	public static int calculateSlope(int x1,int x2, int y1, int y2){
		return (y2-y1)/(x2-x1);
	}
	
}