package codemasters.lambda.streams;

import java.util.Iterator;

public interface Streamable<E> {
	Iterator<E> iterator();
}
