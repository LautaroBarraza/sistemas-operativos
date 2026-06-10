package edu.isistan.buffer;
/** Este código es distribuido como parte de un trabajo práctico de
 *  la materia Sistemas Operativos I dictada por la de Ciencias Exactas de
 *  la Universidad nacional del centro de la provincia de Buenos Aires (UNICEN).
 *  El código no debe usarse en ningún otro proyecto debido a que contiene o 
 *  puede contener malas prácticas y errores introducidos intencionalmente con 
 *  fines didácticos. Así mismo el código carece de cualquier tipo de optimización
 *  primando la legibilidad del mismo.
 *  @author Dr. Juan Manuel Rodriguez
*/

public class OneElementBuffer<T> implements IBuffer<T> {

	private volatile T element;
	
	@Override
	public synchronized T next() {
		try{
			while(this.element==null){
				this.wait();
				//Esperar
			}
			T res = this.element;
			this.element = null;
			this.notifyAll();
			return res;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		}
	}

	@Override
	public synchronized void add(T data) {
		
		try{
			while(this.element!=null){
				this.wait();
				//Esperar
			}
			this.element = data;
			this.notifyAll();
		} catch (InterruptedException e) {
		Thread.currentThread().interrupt();
		}
		
	}

	@Override
	public int size() {
		return this.element==null ? 0:1;
	}

	@Override
	public int maxElements() {
		return 1;
	}

}
