/*
 * Copyright 2012 <a href="mailto:rui.menoita@gmail.com"></a>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.utils;

import java.util.Collection;


/**
 * Utility methods for making precondition/postcondition assertions.
 * 
 * @author RuiMenoita
 */
public final class Assert{


	/**
	 * Throw an exception if the given {@link collection} is empty or null.
	 */
	public static <T> void isEmptyOrNull(Collection<T> collection, String message) throws IllegalAssertionException  {
		if (collection != null)  
			if (!collection.isEmpty())  
				return;
		throw new IllegalAssertionException(message);
	}






	/**
	 * Throw an exception if the given {@link string} is blank or null.
	 */
	public static void isBlanckOrNull(String string, String message) throws IllegalAssertionException  {
		if (string != null)  
			if (!string.isBlank())  
				return;
		throw new IllegalAssertionException(message);
	}





	/**
	 * Throw an exception if the given {@link Object} is not null.
	 */
	public static void isNull(  Object object,   String message) throws IllegalAssertionException {
		if (object != null)  throw new IllegalAssertionException(message);
	}





	/**
	 * Throw an exception if the given {@link Object} is null.
	 */
	public static void notNull(  Object object,   String message) throws IllegalAssertionException {
		if (object == null)  throw new IllegalAssertionException(message);
	}





	/**
	 * Throw an exception if the given value is not true.
	 */
	public static void assertTrue(  boolean value,   String message) throws IllegalAssertionException {
		if (value != true) throw new IllegalAssertionException(message);
	}





	/**
	 * Throw an exception if the given value is not false.
	 */
	public static void assertFalse( boolean value, String message) throws IllegalAssertionException {
		if (value != false) throw new IllegalAssertionException(message);
	}
}
