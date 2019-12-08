interface ICommandFactory{
		// accepting string command type to enable multi character commands in future.
		String CANVAS_DRAW="C";
		String LINE_DRAW="L";
		String RECTANGLE_DRAW="R";
		String COLOR="B";
		String QUIT="Q";
		
		public static Command getCommand(String[] inputArgs) throws InCorrectCommandInput { 
			Command cmd=null;
			ICommandInput input=null;
				switch(inputArgs[0]){
					case CANVAS_DRAW:
						cmd = Command.CANVAS_DRAW;
						input = new CanvasDrawInput(inputArgs);
						cmd.setInput(input);
						break;
					case LINE_DRAW:
						cmd = Command.LINE_DRAW;
						input = new LineDrawInput(inputArgs);
						cmd.setInput(input);
						break;
					case RECTANGLE_DRAW:
						cmd = Command.RECTANGLE_DRAW;
						input = new RectangeDrawInput(inputArgs);
						cmd.setInput(input);
						break;
					case COLOR:
						cmd = Command.COLOR;
						input = new ColorFillInput(inputArgs);
						cmd.setInput(input);
						break;
					case QUIT:
						cmd = Command.QUIT;
						break;
					default:
						throw new InCorrectCommandInput();
				}
			return cmd;
		}

}
