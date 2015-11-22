/*
 * Javolution - Java(TM) Solution for Real-Time and Embedded Systems
 * Copyright (C) 2012 - Javolution (http://javolution.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package javolution.util.internal.collection;

import javolution.util.FastCollection;
import javolution.util.FastIterator;
import javolution.util.function.Equality;

/**
 * A reversed view over a collection (copy-on-write).
 */
public final class ReversedCollectionImpl<E> extends FastCollection<E> {

	private static final long serialVersionUID = 0x700L; // Version.
	private final FastCollection<E> inner;

	public ReversedCollectionImpl(FastCollection<E> inner) {
		this.inner = inner;
	}

	@Override
	public boolean add(E element) {
		return inner.add(element);
	}

	@Override
	public void clear() { // Optimization.
		inner.clear();
	}

	@Override
	public FastCollection<E> clone() {
		return new ReversedCollectionImpl<E>(inner.clone());
	}

	@Override
	public Equality<? super E> equality() {
		return inner.equality();
	}

	@Override
	public boolean isEmpty() { // Optimization.
		return inner.isEmpty();
	}

	@Override
	public FastIterator<E> iterator() {
		return inner.iterator().reversed();
	}

	@Override
	public int size() { // Optimization.
		return inner.size();
	}

}