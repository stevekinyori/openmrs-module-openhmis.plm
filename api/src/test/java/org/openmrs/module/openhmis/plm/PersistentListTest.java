/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.openhmis.plm;

import liquibase.util.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.openhmis.plm.impl.ListEventListenerAdapter;

import java.util.Arrays;
import java.util.List;

import static org.junit.matchers.JUnitMatchers.hasItems;

public abstract class PersistentListTest {
	protected PersistentList list;

	protected abstract PersistentList loadList();

	@Before
	public void before() {
		list = loadList();
	}

	/**
	 * @verifies Add a single item
	 * @see PersistentList#add(PersistentListItem...)
	 */
	@Test
	public void add_shouldAddASingleItem() throws Exception {
		PersistentListItem item = new PersistentListItem("1", null);
		list.add(item);

		junit.framework.Assert.assertEquals(1, list.getSize());

		PersistentListItem[] items = list.getItems();
		Assert.assertNotNull(items);
		Assert.assertEquals(1, items.length);
		Assert.assertEquals(item.getKey(), items[0].getKey());
	}

	/**
	 * @verifies Add multiple items
	 * @see PersistentList#add(PersistentListItem...)
	 */
	@Test
	public void add_shouldAddMultipleItems() throws Exception {
		PersistentListItem item = new PersistentListItem("1", null);
		PersistentListItem item2 = new PersistentListItem("2", null);
		list.add(item, item2);

		junit.framework.Assert.assertEquals(2, list.getSize());

		PersistentListItem[] items = list.getItems();
		Assert.assertNotNull(items);
		Assert.assertEquals(2, items.length);

		// Just check that the items are in the list, order is not important
		List<PersistentListItem> itemList = Arrays.asList(items);
		Assert.assertThat(itemList, hasItems(item, item2));
	}

	/**
	 * @verifies throw PersistentListException when duplicate items are added
	 * @see PersistentList#add(PersistentListItem...)
	 */
	@Test(expected = PersistentListException.class)
	public void add_shouldThrowPersistentListExceptionWhenDuplicateItemsAreAdded() throws Exception {
		PersistentListItem item1 = new PersistentListItem("1", null);
		PersistentListItem item2 = new PersistentListItem("1", null);

		list.add(item1);
		list.add(item2);
	}

	/**
	 * @verifies Fire the itemAdded event
	 * @see PersistentList#add(PersistentListItem...)
	 */
	@Test
	public void add_shouldFireTheItemAddedEvent() throws Exception {
		TestListEventListener listener = new TestListEventListener();
		list.addEventListener(listener);

		PersistentListItem item = new PersistentListItem("key", null);
		list.add(item);

		Assert.assertEquals(1, listener.added);
		Assert.assertEquals(0, listener.removed);
		Assert.assertEquals(0, listener.cleared);
	}

	/**
	 * @verifies Fire the itemAdded event for each item added
	 * @see PersistentList#add(PersistentListItem...)
	 */
	@Test
	public void add_shouldFireTheItemAddedEventForEachItemAdded() throws Exception {
		TestListEventListener listener = new TestListEventListener();
		list.addEventListener(listener);

		PersistentListItem item = new PersistentListItem("key", null);
		PersistentListItem item2 = new PersistentListItem("key2", null);
		PersistentListItem item3 = new PersistentListItem("key3", null);
		list.add(item, item2, item3);

		Assert.assertEquals(3, listener.added);
		Assert.assertEquals(0, listener.removed);
		Assert.assertEquals(0, listener.cleared);
	}

	/**
	 * @verifies Reference the correct list and item when firing the itemAdded event
	 * @see PersistentList#add(PersistentListItem...)
	 */
	@Test
	public void add_shouldReferenceTheCorrectListAndItemWhenFiringTheItemAddedEvent() throws Exception {
		final PersistentListItem item = new PersistentListItem("key", null);

		list.addEventListener(new ListEventListenerAdapter() {
			@Override
			public void itemAdded(ListEvent event) {
				Assert.assertEquals(list, event.getSource());
				Assert.assertEquals(item, event.getItem());
				Assert.assertEquals(ListEvent.ListOperation.ADDED, event.getOperation());
			}
		});

		list.add(item);
	}

	/**
	 * @verifies Allow a key that is less than 250 characters
	 * @see PersistentList#add(PersistentListItem...)
	 */
	@Test
	public void add_shouldAllowAKeyThatIsLessThan250Characters() throws Exception {
		PersistentListItem item1 = new PersistentListItem("A", null);
		PersistentListItem item2 = new PersistentListItem(StringUtils.repeat("A", 250), null);

		list.add(item1);
		list.add(item2);
	}

	/**
	 * @verifies Throw IllegalArgumentException if item key is longer than 250 characters
	 * @see PersistentList#add(PersistentListItem...)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void add_shouldThrowIllegalArgumentExceptionIfItemKeyIsLongerThan250Characters() throws Exception {
		String key = StringUtils.repeat("A", 251);
		PersistentListItem item1 = new PersistentListItem(key, null);

		list.add(item1);
	}

	/**
	 * @verifies Block list operations on other threads until complete
	 * @see PersistentList#add(PersistentListItem...)
	 */
	@Test
	public void add_shouldBlockListOperationsOnOtherThreadsUntilComplete() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies throw IllegalArgumentException if the index is less than zero
	 * @see PersistentList#insert(PersistentListItem, int)
	 */
	@Test
	public void insert_shouldThrowIllegalArgumentExceptionIfTheIndexIsLessThanZero() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies insert the item at the end of the list if the index is larger than the list size
	 * @see PersistentList#insert(PersistentListItem, int)
	 */
	@Test
	public void insert_shouldInsertTheItemAtTheEndOfTheListIfTheIndexIsLargerThanTheListSize() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies insert the item at the specified index and move the existing items
	 * @see PersistentList#insert(PersistentListItem, int)
	 */
	@Test
	public void insert_shouldInsertTheItemAtTheSpecifiedIndexAndMoveTheExistingItems() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies update each moved item via the list provider
	 * @see PersistentList#insert(PersistentListItem, int)
	 */
	@Test
	public void insert_shouldUpdateEachMovedItemViaTheListProvider() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies throw PersistentListException when a duplicate item is inserted
	 * @see PersistentList#insert(PersistentListItem, int)
	 */
	@Test
	public void insert_shouldThrowPersistentListExceptionWhenADuplicateItemIsInserted() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Fire the itemAdded event with the index
	 * @see PersistentList#insert(PersistentListItem, int)
	 */
	@Test
	public void insert_shouldFireTheItemAddedEventWithTheIndex() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Reference the correct list and item when firing the itemAdded event
	 * @see PersistentList#insert(PersistentListItem, int)
	 */
	@Test
	public void insert_shouldReferenceTheCorrectListAndItemWhenFiringTheItemAddedEvent() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Allow a key that is less than 250 characters
	 * @see PersistentList#insert(PersistentListItem, int)
	 */
	@Test
	public void insert_shouldAllowAKeyThatIsLessThan250Characters() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Throw IllegalArgumentException if item key is longer than 250 characters
	 * @see PersistentList#insert(PersistentListItem, int)
	 */
	@Test
	public void insert_shouldThrowIllegalArgumentExceptionIfItemKeyIsLongerThan250Characters() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Block list operations on other threads until complete
	 * @see PersistentList#insert(PersistentListItem, int)
	 */
	@Test
	public void insert_shouldBlockListOperationsOnOtherThreadsUntilComplete() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Remove the item
	 * @see PersistentList#remove(PersistentListItem)
	 */
	@Test
	public void remove_shouldRemoveTheItem() throws Exception {
		PersistentListItem item = new PersistentListItem("1", null);
		list.add(item);

		PersistentListItem[] items = list.getItems();

		Assert.assertNotNull(items);
		Assert.assertEquals(1, items.length);
		Assert.assertEquals(item, items[0]);

		list.remove(item);

		Assert.assertEquals(0, list.getSize());
		items = list.getItems();

		Assert.assertNotNull(items);
		Assert.assertEquals(0, items.length);
	}

	/**
	 * @verifies Return true if the item was removed
	 * @see PersistentList#remove(PersistentListItem)
	 */
	@Test
	public void remove_shouldReturnTrueIfTheItemWasRemoved() throws Exception {
		PersistentListItem item = new PersistentListItem("1", null);
		list.add(item);

		PersistentListItem[] items = list.getItems();

		Assert.assertNotNull(items);
		Assert.assertEquals(1, items.length);

		boolean result = list.remove(item);

		Assert.assertEquals(true, result);
	}

	/**
	 * @verifies Return false if the item was not removed
	 * @see PersistentList#remove(PersistentListItem)
	 */
	@Test
	public void remove_shouldReturnFalseIfTheItemWasNotRemoved() throws Exception {
		boolean result = list.remove(new PersistentListItem("1", null));

		Assert.assertEquals(0, list.getSize());
		Assert.assertEquals(false, result);
	}

	/**
	 * @verifies Fire the itemRemoved event
	 * @see PersistentList#remove(PersistentListItem)
	 */
	@Test
	public void remove_shouldFireTheItemRemovedEvent() throws Exception {
		PersistentListItem item = new PersistentListItem("key", null);
		list.add(item);

		TestListEventListener listener = new TestListEventListener();
		list.addEventListener(listener);

		list.remove(item);

		Assert.assertEquals(0, listener.added);
		Assert.assertEquals(1, listener.removed);
		Assert.assertEquals(0, listener.cleared);
	}

	/**
	 * @verifies Not fire the itemRemoved event for items not found in the list
	 * @see PersistentList#remove(PersistentListItem)
	 */
	@Test
	public void remove_shouldNotFireTheItemRemovedEventForItemsNotFoundInTheList() throws Exception {
		TestListEventListener listener = new TestListEventListener();
		list.addEventListener(listener);

		PersistentListItem item = new PersistentListItem("key", null);
		list.remove(item);

		Assert.assertEquals(0, listener.added);
		Assert.assertEquals(0, listener.removed);
		Assert.assertEquals(0, listener.cleared);
	}

	/**
	 * @verifies Reference the correct list and item when firing the itemRemoved event
	 * @see PersistentList#remove(PersistentListItem)
	 */
	@Test
	public void remove_shouldReferenceTheCorrectListAndItemWhenFiringTheItemRemovedEvent() throws Exception {
		final PersistentListItem item = new PersistentListItem("key", null);

		list.addEventListener(new ListEventListenerAdapter() {
			@Override
			public void itemRemoved(ListEvent event) {
				Assert.assertEquals(list, event.getSource());
				Assert.assertEquals(item, event.getItem());
				Assert.assertEquals(ListEvent.ListOperation.REMOVED, event.getOperation());
			}
		});

		list.remove(item);
	}

	/**
	 * @verifies Block list operations on other threads until complete
	 * @see PersistentList#remove(PersistentListItem)
	 */
	@Test
	public void remove_shouldBlockListOperationsOnOtherThreadsUntilComplete() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Remove all items
	 * @see PersistentList#clear()
	 */
	@Test
	public void clear_shouldRemoveAllItems() throws Exception {
		PersistentListItem item = new PersistentListItem("1", null);
		PersistentListItem item2 = new PersistentListItem("2", null);
		list.add(item);
		list.add(item2);

		Assert.assertEquals(2, list.getSize());

		list.clear();

		Assert.assertEquals(0, list.getSize());
	}

	/**
	 * @verifies Not throw an exception when list is empty
	 * @see PersistentList#clear()
	 */
	@Test
	public void clear_shouldNotThrowAnExceptionWhenListIsEmpty() throws Exception {
		Assert.assertEquals(0, list.getSize());
		list.clear();
		Assert.assertEquals(0, list.getSize());
	}

	/**
	 * @verifies Fire the listCleared event
	 * @see PersistentList#clear()
	 */
	@Test
	public void clear_shouldFireTheListClearedEvent() throws Exception {
		TestListEventListener listener = new TestListEventListener();
		list.addEventListener(listener);

		list.clear();

		Assert.assertEquals(0, listener.added);
		Assert.assertEquals(0, listener.removed);
		Assert.assertEquals(1, listener.cleared);
	}

	/**
	 * @verifies Reference the correct list when firing the listCleared event
	 * @see PersistentList#clear()
	 */
	@Test
	public void clear_shouldReferenceTheCorrectListWhenFiringTheListClearedEvent() throws Exception {
		list.addEventListener(new ListEventListenerAdapter() {
			@Override
			public void listCleared(ListEvent event) {
				Assert.assertEquals(list, event.getSource());
				Assert.assertNull(event.getItem());
				Assert.assertEquals(ListEvent.ListOperation.CLEARED, event.getOperation());
			}
		});

		list.clear();
	}

	/**
	 * @verifies Block list operations on other threads until complete
	 * @see PersistentList#clear()
	 */
	@Test
	public void clear_shouldBlockListOperationsOnOtherThreadsUntilComplete() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Return items that have been added
	 * @see PersistentList#getItems()
	 */
	@Test
	public void getItems_shouldReturnItemsThatHaveBeenAdded() throws Exception {
		PersistentListItem item = new PersistentListItem("1", null);
		list.add(item);

		Assert.assertEquals(1, list.getSize());

		PersistentListItem[] items = list.getItems();
		Assert.assertNotNull(items);
		Assert.assertEquals(1, items.length);
		Assert.assertEquals(item, items[0]);
	}

	/**
	 * @verifies Return all list items
	 * @see PersistentList#getItems()
	 */
	@Test
	public void getItems_shouldReturnAllListItems() throws Exception {
		PersistentListItem item1 = new PersistentListItem("1", null);
		PersistentListItem item2 = new PersistentListItem("2", null);
		PersistentListItem item3 = new PersistentListItem("3", null);

		list.add(item1, item2, item3);

		Assert.assertEquals(3, list.getSize());

		PersistentListItem[] items = list.getItems();
		Assert.assertNotNull(items);
		Assert.assertEquals(3, items.length);

		// Just check that the items are in the list, order is not important
		List<PersistentListItem> itemList = Arrays.asList(items);
		Assert.assertThat(itemList, hasItems(item1, item2, item3));
	}

	/**
	 * @verifies Block list operations on other threads until complete
	 * @see PersistentList#getItems()
	 */
	@Test
	public void getItems_shouldBlockListOperationsOnOtherThreadsUntilComplete() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Return the item at the specified index
	 * @see PersistentList#getItemAt(int)
	 */
	@Test
	public void getItemAt_shouldReturnTheItemAtTheSpecifiedIndex() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Return null when there is no item at the specified index
	 * @see PersistentList#getItemAt(int)
	 */
	@Test
	public void getItemAt_shouldReturnNullWhenThereIsNoItemAtTheSpecifiedIndex() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Throw IllegalArgumentException when the index is less than zero
	 * @see PersistentList#getItemAt(int)
	 */
	@Test
	public void getItemAt_shouldThrowIllegalArgumentExceptionWhenTheIndexIsLessThanZero() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Return null if the index is larger than or equal to the list size
	 * @see PersistentList#getItemAt(int)
	 */
	@Test
	public void getItemAt_shouldReturnNullIfTheIndexIsLargerThanOrEqualToTheListSize() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Block list operations on other threads until complete
	 * @see PersistentList#getItemAt(int)
	 */
	@Test
	public void getItemAt_shouldBlockListOperationsOnOtherThreadsUntilComplete() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Not remove item from list
	 * @see PersistentList#getNext()
	 */
	@Test
	public void getNext_shouldNotRemoveItemFromList() throws Exception {
		PersistentListItem item = new PersistentListItem("1", null);
		list.add(item);

		Assert.assertEquals(1, list.getSize());
		item = list.getNext();

		Assert.assertNotNull(item);
		Assert.assertEquals(1, list.getSize());
	}

	/**
	 * @verifies Return null when list is empty
	 * @see PersistentList#getNext()
	 */
	@Test
	public void getNext_shouldReturnNullWhenListIsEmpty() throws Exception {
		PersistentListItem item = list.getNext();

		Assert.assertNull(item);
	}

	/**
	 * @verifies Block list operations on other threads until complete
	 * @see PersistentList#getNext()
	 */
	@Test
	public void getNext_shouldBlockListOperationsOnOtherThreadsUntilComplete() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Return and remove item
	 * @see PersistentList#getNextAndRemove()
	 */
	@Test
	public void getNextAndRemove_shouldReturnAndRemoveItem() throws Exception {
		PersistentListItem item = new PersistentListItem("1", null);
		list.add(item);

		Assert.assertEquals(1, list.getSize());

		item = list.getNextAndRemove();

		Assert.assertNotNull(item);
		Assert.assertEquals(0, list.getSize());
	}

	/**
	 * @verifies Return null when list is empty
	 * @see PersistentList#getNextAndRemove()
	 */
	@Test
	public void getNextAndRemove_shouldReturnNullWhenListIsEmpty() throws Exception {
		PersistentListItem item = list.getNextAndRemove();

		Assert.assertNull(item);
	}

	/**
	 * @verifies Fire the itemRemoved event
	 * @see PersistentList#getNextAndRemove()
	 */
	@Test
	public void getNextAndRemove_shouldFireTheItemRemovedEvent() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Block list operations on other threads until complete
	 * @see PersistentList#getNextAndRemove()
	 */
	@Test
	public void getNextAndRemove_shouldBlockListOperationsOnOtherThreadsUntilComplete() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Return the number of items
	 * @see PersistentList#getSize()
	 */
	@Test
	public void getSize_shouldReturnTheNumberOfItems() throws Exception {
		Assert.assertEquals(0, list.getSize());

		list.add(new PersistentListItem("1", null));
		Assert.assertEquals(1, list.getSize());

		list.add(new PersistentListItem("2", null));
		Assert.assertEquals(2, list.getSize());

		list.add(new PersistentListItem("3", null));
		Assert.assertEquals(3, list.getSize());
	}

	/**
	 * @verifies Return an empty array when there are no items
	 * @see PersistentList#getSize()
	 */
	@Test
	public void getSize_shouldReturnAnEmptyArrayWhenThereAreNoItems() throws Exception {
		Assert.assertEquals(0, list.getSize());

		PersistentListItem[] items =  list.getItems();
		Assert.assertNotNull(items);
		Assert.assertEquals(0, items.length);
	}

	/**
	 * @verifies Not block list operations on other threads
	 * @see PersistentList#getSize()
	 */
	@Test
	public void getSize_shouldNotBlockListOperationsOnOtherThreads() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies Fire events for added listeners
	 * @see PersistentList#addEventListener(ListEventListener)
	 */
	@Test
	public void addEventListener_shouldFireEventsForAddedListeners() throws Exception {
		TestListEventListener listener1 = new TestListEventListener();
		TestListEventListener listener2 = new TestListEventListener();

		list.addEventListener(listener1);
		list.addEventListener(listener2);

		PersistentListItem item = new PersistentListItem("key", null);
		list.add(item);

		Assert.assertEquals(1, listener1.added);
		Assert.assertEquals(1, listener2.added);
		Assert.assertEquals(0, listener1.removed);
		Assert.assertEquals(0, listener2.removed);
		Assert.assertEquals(0, listener1.cleared);
		Assert.assertEquals(0, listener2.cleared);

		list.remove(item);

		Assert.assertEquals(1, listener1.added);
		Assert.assertEquals(1, listener2.added);
		Assert.assertEquals(1, listener1.removed);
		Assert.assertEquals(1, listener2.removed);
		Assert.assertEquals(0, listener1.cleared);
		Assert.assertEquals(0, listener2.cleared);

		list.clear();

		Assert.assertEquals(1, listener1.added);
		Assert.assertEquals(1, listener2.added);
		Assert.assertEquals(1, listener1.removed);
		Assert.assertEquals(1, listener2.removed);
		Assert.assertEquals(1, listener1.cleared);
		Assert.assertEquals(1, listener2.cleared);
	}

	/**
	 * @verifies Not fire events for removed listeners
	 * @see PersistentList#removeEventListener(ListEventListener)
	 */
	@Test
	public void removeEventListener_shouldNotFireEventsForRemovedListeners() throws Exception {
		TestListEventListener listener1 = new TestListEventListener();
		TestListEventListener listener2 = new TestListEventListener();

		list.addEventListener(listener1);
		list.addEventListener(listener2);

		PersistentListItem item = new PersistentListItem("key", null);
		list.add(item);

		Assert.assertEquals(1, listener1.added);
		Assert.assertEquals(1, listener2.added);
		Assert.assertEquals(0, listener1.removed);
		Assert.assertEquals(0, listener2.removed);
		Assert.assertEquals(0, listener1.cleared);
		Assert.assertEquals(0, listener2.cleared);

		list.removeEventListener(listener1);

		list.remove(item);

		Assert.assertEquals(1, listener1.added);
		Assert.assertEquals(1, listener2.added);
		Assert.assertEquals(0, listener1.removed);
		Assert.assertEquals(1, listener2.removed);
		Assert.assertEquals(0, listener1.cleared);
		Assert.assertEquals(0, listener2.cleared);

		list.removeEventListener(listener2);

		list.clear();

		Assert.assertEquals(1, listener1.added);
		Assert.assertEquals(1, listener2.added);
		Assert.assertEquals(0, listener1.removed);
		Assert.assertEquals(1, listener2.removed);
		Assert.assertEquals(0, listener1.cleared);
		Assert.assertEquals(0, listener2.cleared);
	}

	public class TestListEventListener implements ListEventListener {
		public int added;
		public int removed;
		public int cleared;

		public void reset() {
			added = 0;
			removed = 0;
			cleared = 0;
		}

		@Override
		public void itemAdded(ListEvent event) {
			added++;
		}

		@Override
		public void itemRemoved(ListEvent event) {
			removed++;
		}

		@Override
		public void listCleared(ListEvent event) {
			cleared++;
		}
	}
}
