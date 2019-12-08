public interface ICommand{
	void setInput(ICommandInput input);
	ICommandInput getInput();
}

enum Command implements ICommand{
	
	CANVAS_DRAW,LINE_DRAW,RECTANGLE_DRAW,COLOR,QUIT;
	
	public ICommandInput input;
	
	@Override
	public void setInput(ICommandInput input) {
		this.input=input;
	}

	@Override
	public ICommandInput getInput() {
		return input;
	}
}
