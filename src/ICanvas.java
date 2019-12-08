import java.lang.reflect.Array;

public interface ICanvas<C,D>{
	void drawCanvas();
	D[][] getCanvasData();
	ICoordinate<C> getCanvasDimension();
	void updateCanvasBase(ICoordinate<C> point, D value) throws CordinateNotSupportedException;
	D getDataAt(C i, C j);
}
/*abstract class ICanvasImpl<D> implements ICanvas{
	// Forcing Child Canvas implementations to accept cordinates.
	ICanvasImpl(ICoordinate<Integer> base){
	}
}*/


class MatrixCanvas2D<C,D> implements ICanvas<C,D>{
	
	D[][] canvasData;
	private static Object mutex=new Object();
	private static MatrixCanvas2D INSTANCE;
	
	public static<C,D> ICanvas<C,D> getInstance(ICommand cmd,ICoordinate<Integer> dimension) throws CordinateNotSupportedException{
		if(null==INSTANCE && cmd==Command.CANVAS_DRAW)
		{
			synchronized (mutex) {
				if(null==INSTANCE){
					INSTANCE=new MatrixCanvas2D<C,D>(dimension);
				}
			}
		}
		return INSTANCE;
	}
	
	private MatrixCanvas2D(ICoordinate<Integer> dimension) throws CordinateNotSupportedException {
			canvasData=(D[][])(new Object[dimension.getY()+2][dimension.getX()+2]);
	}
	
	@Override
	public void updateCanvasBase(ICoordinate point, D value) throws CordinateNotSupportedException {
		canvasData[(int) point.getY()][(int) point.getX()]=value;
		
	}
	
	@Override
	public void drawCanvas() {
		int width=canvasData[0].length;
		int height=canvasData.length;
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				System.out.print(canvasData[i][j]==null?" ":canvasData[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	@Override
	public D[][] getCanvasData() {
		return canvasData;
	}

	@Override
	public ICoordinate getCanvasDimension() {
		return new TwoDCordinate<Integer>(new Integer(canvasData[0].length),new Integer(canvasData.length));
	}

	@Override
	public D getDataAt(C x, C y) {
		return canvasData[(int)y][(int)x];
	}


}
