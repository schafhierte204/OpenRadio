package at.chaosfield.openradio.common.container;

import at.chaosfield.openradio.common.tileentity.LaserTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Jakob Riepler (XDjackieXD)
 */
public class LaserContainer extends Container{
    protected LaserTileEntity tileEntity;

    public LaserContainer(InventoryPlayer inventoryPlayer, LaserTileEntity te){
        tileEntity = te;

        addSlotToContainer(new Slot(tileEntity, 0, 33, 18));    //DSP
        addSlotToContainer(new Slot(tileEntity, 1, 56, 18));    //PhotoReceptor
        addSlotToContainer(new Slot(tileEntity, 2, 89, 18));    //SemiReflectiveMirror
        addSlotToContainer(new Slot(tileEntity, 3, 127, 18));   //Lens
        addSlotToContainer(new Slot(tileEntity, 4, 89, 51));    //Laser

        //commonly used vanilla code that adds the player's inventory
        bindPlayerInventory(inventoryPlayer);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileEntity.isUseableByPlayer(player);
    }


    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                        8 + j * 18, 92 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 150));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            //Item is in Container. Transfer to Player inventory
            if (slot < tileEntity.getSizeInventory()) {
                if (!this.mergeItemStack(stackInSlot, tileEntity.getSizeInventory(), 36+tileEntity.getSizeInventory(), true)) {
                    return null;
                }
            }
            //Item is in Player inventory. Transfer into container
            //TODO Shift-click deletes all items over containers maximum stack size
            else if (!this.mergeItemStack(stackInSlot, 0, tileEntity.getSizeInventory(), false)) {
                return null;
            }

            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }
}