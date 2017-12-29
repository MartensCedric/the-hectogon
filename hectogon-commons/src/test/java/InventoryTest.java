import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.commons.storage.inventory.InventoryOperationException;
import com.cedricmartens.commons.storage.inventory.Item;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Cedric Martens on 05/03/17.
 */
public class InventoryTest {

    @Test(timeout = 1000)
    public void testAddItem()
    {
        Inventory inventory = new Inventory(4);
        assertEquals(0, inventory.getQuantity(Item.arr_wood));

        inventory.addItem(Item.arr_wood);
        assertEquals(1, inventory.getQuantity(Item.arr_wood));

        inventory.addItem(Item.arr_wood, 5);
        assertEquals(6, inventory.getQuantity(Item.arr_wood));
    }

    @Test (timeout = 1000)
    public void testAddItemOnMultipleStacks()
    {
        Inventory inventory = new Inventory(5);
        inventory.addItem(Item.arr_wood, 18);
        assertEquals(2, inventory.stackCount(Item.arr_wood));

        inventory.addItem(Item.arr_wood, 14);
        assertEquals(2, inventory.stackCount(Item.arr_wood));

        inventory.addItem(Item.arr_wood);
        assertEquals(3, inventory.stackCount(Item.arr_wood));
    }

    @Test(timeout = 1000)
    public void testAddItemOnIncompleteStacks()
    {
        Inventory inventory = new Inventory(5);
        inventory.addItem(Item.arr_wood, 18);
        inventory.removeItem(Item.arr_wood, 3);
        inventory.addItem(Item.arr_wood, 20);
        assertEquals(3, inventory.stackCount(Item.arr_wood));
    }

    @Test(expected = InventoryOperationException.class, timeout = 1000)
    public void testAddItemInventoryFullKO()
    {
        Inventory inventory = new Inventory(5);
        inventory.addItem(Item.arr_wood, 81);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidAmount()
    {
        Inventory inventory = new Inventory(3);
        inventory.addItem(Item.arr_wood, 0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testEmptyInventoryKO()
    {
        new Inventory(0);
    }

    @Test (timeout = 1000)
    public void testCapacityForItemOK()
    {
        Inventory inventory = new Inventory(5);
        assertEquals(80, inventory.getCapacityForItem(Item.arr_wood));

        inventory.addItem(Item.arr_wood, 18);
        assertEquals(62, inventory.getCapacityForItem(Item.arr_wood));
        assertEquals(3, inventory.getCapacityForItem(Item.swd_steel));
    }

    @Test (timeout = 1000)
    public void testRemoveItemSimpleOK()
    {
        Inventory inventory = new Inventory(5);
        inventory.addItem(Item.arr_wood, 15);
        inventory.removeItem(Item.arr_wood, 4);
        assertEquals(11, inventory.getQuantity(Item.arr_wood));
    }

    @Test (timeout = 1000)
    public void testRemoveItemMultipleStackOK()
    {
        Inventory inventory = new Inventory(5);
        inventory.addItem(Item.arr_wood, 18);
        inventory.removeItem(Item.arr_wood, 3);
        assertEquals(15, inventory.getQuantity(Item.arr_wood));
    }

    @Test(timeout = 1000)
    public void testRemoveItemAtOK()
    {
        Inventory inventory = new Inventory(5);
        inventory.addItem(Item.arr_wood, 18);
        inventory.removeItemAt(1);
        Assert.assertEquals(16, inventory.getQuantity(Item.arr_wood), 0);
    }

    @Test(timeout = 1000, expected = InventoryOperationException.class)
    public void testRemoveItemAtKO()
    {
        Inventory inventory = new Inventory(5);
        inventory.removeItemAt(3);
    }

    @Test (timeout = 1000)
    public void testIsEmpty()
    {
        Assert.assertTrue(new Inventory(5).isEmpty());
    }

    @Test (timeout = 1000)
    public void testIsEmptyAfterManipulation()
    {
        Inventory inv = new Inventory(5);
        inv.addItem(Item.arr_wood, 50);
        inv.addItem(Item.bow_wood);

        Assert.assertFalse(inv.isEmpty());

        inv.removeItem(Item.arr_wood, 50);
        inv.removeItem(Item.bow_wood);
        Assert.assertTrue(inv.isEmpty());
    }
}