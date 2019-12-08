interface ICoordinate<T>{
	T getX() throws CordinateNotSupportedException;
	T getY() throws CordinateNotSupportedException;
	T getZ() throws CordinateNotSupportedException;
}
class TwoDCordinate<T> implements ICoordinate<T>{

	T x;
	T y;
	
	TwoDCordinate(T... value){
		this.x=value[0];
		this.y=value[1];
	}

	@Override
	public T getX() {
		return x;
	}

	@Override
	public T getY() {
		return y;
	}

	@Override
	public T getZ() throws CordinateNotSupportedException {
		throw new CordinateNotSupportedException();
	}
}