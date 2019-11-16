package com.utils;


/**
 * 
 * @author RuiMenoita
 */
public class ArrayUtil {





	/**
	 * 
	 * @param array array that have the item
	 * @param item	item to be searched for
	 * @return 
	 * returns the first occurrence index in array, returns -1 if item is not present in array
	 */
	public static <T> int getIndex(T[] array , T item) {
		for (int i = 0; i < array.length; i++) {
			if(array[i] != null) {
				if(array[i].equals(item))
					return i;
			}
		}
		return -1;
	}




	/**
	 * 
	 * @param array array that have the item
	 * @param item	item to be searched for
	 * @return 
	 * returns the first occurrence index in array, returns -1 if item is not present in array
	 */
	public static int getIndex(int[] array , int item) {
		for (int i = 0; i < array.length; i++) {
			if(array[i]==(item))
				return i;
		}
		return -1;
	}





	/**
	 * 
	 * @param array array that have the item
	 * @param item	item to be searched for
	 * @return 
	 * returns the first occurrence index in array, returns -1 if item is not present in array
	 */
	public static int getIndex(String[] array , String item) {
		for (int i = 0; i < array.length; i++) {
			if(array[i] != null) {
				if(array[i].equals(item))
					return i;
			}
		}
		return -1;
	}





	/**
	 * @param array array to be checked
	 * @param value value to be checked
	 * @return return true if the array given as the value given
	 */
	public static boolean contains(String[] array , String value) {
		boolean has = false;
		for (String string : array) 
			if(string != null)
				if(string.equals(value))
					has = true;
		return has;
	}






	/**
	 * @param array array to be checked
	 * @param value value to be checked
	 * @return return true if the array given as the value given
	 */
	public static boolean contains(int[] array , int value) {
		boolean has = false;
		for (int string : array) 
			if(string == value)
				has = true;
		return has;
	}






	/**
	 * @param array array to be checked
	 * @param value value to be checked
	 * @return return true if the array given as the value given
	 */
	public static boolean contains(double[] array , double value) {
		boolean has = false;
		for (double string : array) 
			if(string == value)
				has = true;
		return has;
	}






	/**
	 * @param array array to be checked
	 * @param value value to be checked
	 * @return return true if the array given as the value given
	 */
	public static <T> boolean contains(T[] array , T value) {
		boolean has = false;
		for (T string : array) 
			if(string != null)
				if(string.equals(value))
					has = true;
		return has;
	}







	/**
	 * This method shifts right the given array 
	 * the last position became the first 
	 * 
	 * @param arr array to be shifted
	 */
	public static <T> void shiftRight(T[] arr) {
		T buffer = arr[arr.length-1];
		for (int i = arr.length-1; i >= 0; i--) {

			if(i>0)  arr[i] = arr[i-1];
			else arr[0] = buffer;
		}
	}







	/**
	 * This method shifts right the given array 
	 * the last position became the first from the given index
	 * 
	 * @param arr array to be shifted
	 * @param index index to star shifting
	 * 
	 * @throws IllegalArgumentExecption if index is less then 0 or bigger then array length
	 */
	public static <T> void shiftRight(T[] arr, int index) {
		if(index>=arr.length || index<0) throw new IllegalArgumentException("Inalid index value : "+ index);
		
		T buffer = arr[arr.length-1];
		for (int i = arr.length-1; i >= index; i--) {

			if(i>index)  arr[i] = arr[i-1];
			else arr[index] = buffer;
		}
	}

	
	



	/**
	 * This method shifts right the given array 
	 * the last position became the first from the given index
	 * 
	 * @param arr array to be shifted
	 * @param index index to star shifting
	 * 
	 * @throws IllegalArgumentExecption if index is less then 0 or bigger then array length
	 */
	public static void shiftRight(int[] arr, int index) {
		int buffer = arr[arr.length-1];
		for (int i = arr.length-1; i >= index; i--) {
			if(i>index)  arr[i] = arr[i-1];
			else arr[index] = buffer;
		}
	}
	

	



	/**
	 * This method shifts right the given array 
	 * the last position became the first 
	 * 
	 * @param arr array to be shifted
	 */
	public static void shiftRight(int[] arr) {
		int buffer = arr[arr.length-1];
		for (int i = arr.length-1; i >= 0; i--) {

			if(i>0)  arr[i] = arr[i-1];
			else arr[0] = buffer;
		}
	}





	/**
	 * This method shifts right the given array 
	 * the last position became the first 
	 * 
	 * @param arr array to be shifted
	 */
	public static void shiftRight(String[] arr) {
		String buffer = arr[arr.length-1];
		for (int i = arr.length-1; i >= 0; i--) {

			if(i>0)  arr[i] = arr[i-1];
			else arr[0] = buffer;
		}
	}





	/**
	 * This method shifts right the given array 
	 * the last position became the first 
	 * 
	 * @param arr array to be shifted
	 */
	public static void shiftRight(double[] arr) {
		double buffer = arr[arr.length-1];
		for (int i = arr.length-1; i >= 0; i--) {

			if(i>0)  arr[i] = arr[i-1];
			else arr[0] = buffer;
		}
	}





	/**
	 * This method shifts right the given array 
	 * the last position became the first 
	 * 
	 * @param arr array to be shifted
	 */
	public static void shiftRight(long[] arr) {
		long buffer = arr[arr.length-1];
		for (int i = arr.length-1; i >= 0; i--) {

			if(i>0)  arr[i] = arr[i-1];
			else arr[0] = buffer;
		}
	}








	/**
	 * Checks if the array give is empty
	 * 
	 * @param array array to be checked
	 * @return return true if array is empty otherwise returns false
	 */
	public static <T> boolean isEmpty(T[] array) {
		for (T t : array) 
			if(t != null)
				return false;
		return true;
	}







	/**
	 * 
	 * This method puts array2 after array1 and output a single array with 
	 * values from the 2 arrays
	 */
	public static <T> Object[] fusion(T[] array1, T[] array2) {
		Object[] output =  new Object[array1.length+array2.length];

		for (int i = 0; i < array1.length; i++) 
			output[i] = array1[i];

		for (int i = array1.length; i-array1.length < array2.length; i++) 
			output[i] = array2[i-array1.length];

		return output;
	}
}
