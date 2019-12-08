interface ICommandInput<C>{
	public static final String DASH_PIXEL="-";
	public static final String PIPE_PIXEL="|";
	public static final String STAR_PIXEL="*";
	public static final String COLOR_PIXEL="o";
	void setInputDimension(C[] inputArgs);
	void processInputDimension(C[] inputArgs) throws InCorrectCommandInput;
	boolean validateInput(C[] inputArgs);
	ICoordinate<C>[] getInputDimensions();
}
abstract class ICommandInputAbstract<C> implements ICommandInput<C>{
	
	public abstract boolean validateInput(C[] inputArgs);
	public abstract void setInputDimension(C[] inputArgs);
	ICommandInputAbstract(C[] inputArgs) throws InCorrectCommandInput{
		processInputDimension(inputArgs);
	}
	public void processInputDimension(C[] inputArgs) throws InCorrectCommandInput{
		if(validateInput(inputArgs)){
			setInputDimension(inputArgs);
		}else{
			throw new InCorrectCommandInput();
		}
	}
}
class CanvasDrawInput<C extends Comparable<C>> extends ICommandInputAbstract<C>{
	
	private ICoordinate<C>[] points;
	
	CanvasDrawInput(C[] inputArgs) throws InCorrectCommandInput{
		super(inputArgs);
	}
	
	@Override
	public ICoordinate<C>[] getInputDimensions() {
		return points;
	}
	
	@Override
	public void setInputDimension(C[] inputArgs) {
		points=new ICoordinate[1];
		points[0]=new TwoDCordinate<C>(inputArgs[1],inputArgs[2]);
	}

	@Override
	public boolean validateInput(C[] inputArgs) {
		if(inputArgs==null){
			return false; // Incorrect input received.
		}else if(inputArgs.length!=3){
			return false; // Incorrect inputs for canvas draw.
		}else if(inputArgs[1].equals("0") || inputArgs[2].equals("0")){
			return false;
		}
		return true;
	}
}

class LineDrawInput<C extends Comparable<C>> extends ICommandInputAbstract<C>{
	
	private ICoordinate<C>[] points;
	
	LineDrawInput(C[] inputArgs) throws InCorrectCommandInput{
		super(inputArgs);
	}
	
	@Override
	public void setInputDimension(C[] inputArgs) {
		points=new ICoordinate[2];
		points[0]=new TwoDCordinate<C>(inputArgs[1],inputArgs[2]);
		points[1]=new TwoDCordinate<C>(inputArgs[3],inputArgs[4]);
	}

	@Override
	public ICoordinate<C>[] getInputDimensions() {
		return points;
	}

	@Override
	public boolean validateInput(C[] inputArgs) {
		if(inputArgs==null){
			return false; // Incorrect input received.
		}else if(inputArgs.length!=5){
			return false; // Incorrect inputs for canvas draw.
		}
		return true;
	}
	
}

class RectangeDrawInput<C extends Comparable<C>> extends ICommandInputAbstract<C>{
	
	private ICoordinate<C>[] points;
	
	RectangeDrawInput(C[] inputArgs) throws InCorrectCommandInput{
		super(inputArgs);
	}
	
	@Override
	public void setInputDimension(C[] inputArgs) {
		points=new ICoordinate[4];
		points[0]=new TwoDCordinate<C>(inputArgs[1],inputArgs[2]);
		points[1]=new TwoDCordinate<C>(inputArgs[3],inputArgs[4]);
	}

	@Override
	public ICoordinate<C>[] getInputDimensions() {
		return points;
	}

	@Override
	public boolean validateInput(C[] inputArgs) {
		if(inputArgs==null){
			return false; // Incorrect input received.
		}else if(inputArgs.length!=5){
			return false; // Incorrect inputs for canvas draw.
		}
		return true;
	}
}

class ColorFillInput<C extends Comparable<C>> extends ICommandInputAbstract<C>{
	
	private ICoordinate<C>[] points;
	private final String colorPixel; 
	
	public String getColorPixel() {
		return colorPixel;
	}

	ColorFillInput(C[] inputArgs) throws InCorrectCommandInput{
		super(inputArgs);
		colorPixel=(String)inputArgs[3];
	}
	
	@Override
	public void setInputDimension(C[] inputArgs) {
		points=new ICoordinate[1];
		points[0]=new TwoDCordinate<C>(inputArgs[1],inputArgs[2]);
	}

	@Override
	public ICoordinate<C>[] getInputDimensions() {
		return points;
	}

	@Override
	public boolean validateInput(C[] inputArgs) {
		if(inputArgs==null){
			return false; // Incorrect input received.
		}else if(inputArgs.length!=4){
			return false; // Incorrect inputs for canvas draw.
		}
		return true;
	}
	
}