/*
 * Javolution - Java(TM) Solution for Real-Time and Embedded Systems
 * Copyright (C) 2012 - Javolution (http://javolution.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package javolution.util.internal.collection;

import java.util.Comparator;

import javolution.util.FastCollection;
import javolution.util.FastIterator;
import javolution.util.FastTable;
import javolution.util.function.Equality;

/**
 * A sorted view over a collection.
 */
public final class SortedCollectionImpl<E> extends FastCollection<E> {

	private static class IteratorImpl<E> implements FastIterator<E> {
		private final FastIterator<E> sorted;
		private final FastCollection<E> collection;
		private E next;

		public IteratorImpl(FastIterator<E> sorted, FastCollection<E> collection) {
			this.sorted = sorted;
			this.collection = collection;
		}

		@Override
		public boolean hasNext() {
			return sorted.hasNext();
		}

		@Override
		public E next() {
			next = sorted.next();
			return next;
		}

		@Override
		public void remove() {
			sorted.remove();
			collection.remove(next);
		}

		@Override
		public FastIterator<E> reversed() {
			return new IteratorImpl<E>(sorted.reversed(), collection);
		}

		@Override
		public FastIterator<E>[] split(FastIterator<E>[] subIterators) {
			sorted.split(subIterators);
			int i = 0;
			for (FastIterator<E> itr : subIterators)
				if (itr != null)
					subIterators[i++] = new IteratorImpl<E>(itr, collection);
			return subIterators;
		}
	}

	private static final long serialVersionUID = 0x700L; // Version.
	private final FastCollection<E> inner;
	private final Comparator<? super E> cmp;

	public SortedCollectionImpl(FastCollection<E> inner,
			Comparator<? super E> cmp) {
		this.inner = inner;
		this.cmp = cmp;
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
	public SortedCollectionImpl<E> clone() {
		return new SortedCollectionImpl<E>(inner.clone(), cmp);
	}

	@Override
	public boolean contains(Object searched) { // Optimization.
		return inner.contains(searched);
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
		FastTable<E> sorted = FastTable.newTable();
		sorted.addAll(inner);
		sorted.sort(cmp);
		return new IteratorImpl<E>(sorted.iterator(), this);
	}

	@Override
	public boolean remove(Object searched) { // Optimization.
		return inner.remove(searched);
	}

	@Override
	public int size() { // Optimization.
		return inner.size();
	}
}
