package at.chaosfield.openradio.common.item;

import at.chaosfield.openradio.CreativeTab;
import at.chaosfield.openradio.OpenRadio;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import java.util.List;

/**
 * Created by Jakob Riepler (XDjackieXD)
 */
public class LensItem extends Item{

    public LensItem(){
        maxStackSize = 1;
        setUnlocalizedName(OpenRadio.MODID + ":lens");
        setCreativeTab(CreativeTab.instance);
    }


    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        icons = new IIcon[3];
        icons[0] = par1IconRegister.registerIcon(OpenRadio.MODID + ":glasslens");
        icons[1] = par1IconRegister.registerIcon(OpenRadio.MODID + ":quartzlens");
        icons[2] = par1IconRegister.registerIcon(OpenRadio.MODID + ":diamondlens");
    }

    public static final String[] names = new String[] { "glasslens", "quartzlens", "diamondlens" };

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 2);
        return super.getUnlocalizedName() + "." + names[i];
    }

    @Override
    public IIcon getIconFromDamage(int par1)
    {
        return icons[par1];
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List itemList) {

        for (int i = 0; i < 3; ++i) {
            itemList.add(new ItemStack(item, 1, i));
        }
    }
  }
