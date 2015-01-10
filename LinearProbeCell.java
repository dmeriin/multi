
public class LinearProbeCell<T>  {

	public int StepSize = 0;
	public int key;
	public T item;

	public LinearProbeCell(int key, T item, int stepSize) {
		this.key = key;
		this.item = item;
		this.StepSize = stepSize;
	}
	
	

}
