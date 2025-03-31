
package net.mcreator.scpalarm.gui;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import net.mcreator.scpalarm.MoreScpAlarmMod;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class TestpaneldynGuiWindow extends ContainerScreen<TestpaneldynGui.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	private final static HashMap guistate = TestpaneldynGui.guistate;
	CheckboxButton channel1;
	CheckboxButton channel2;
	CheckboxButton channel3;
	TextFieldWidget soundid;

	public TestpaneldynGuiWindow(TestpaneldynGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 300;
		this.ySize = 200;
	}

	private static final ResourceLocation texture = new ResourceLocation("more_scp_alarm:textures/screens/testpaneldyn.png");

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
		soundid.render(ms, mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.blit(ms, k, l, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeScreen();
			return true;
		}
		if (soundid.isFocused())
			return soundid.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void tick() {
		super.tick();
		soundid.tick();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
		this.font.drawString(ms, "Dynamic Alarm Panel", 95, 6, -12829636);
		this.font.drawString(ms, "How to get the ID's ?", 32, 96, -13395457);
		this.font.drawString(ms, "Select Channel", 194, 42, -12829636);
		this.font.drawString(ms, "Enter a sound ID", 50, 42, -12829636);
		this.font.drawString(ms, "Go to the Wiki of ", 41, 114, -12829636);
		this.font.drawString(ms, "More SCP Alarm", 50, 132, -65536);
		this.font.drawString(ms, "To play/save the sound, please close the GUI.", 32, 177, -12829636);
	}

	@Override
	public void onClose() {
		super.onClose();
		Minecraft.getInstance().keyboardListener.enableRepeatEvents(false);
	}

	@Override
	public void init(Minecraft minecraft, int width, int height) {
		super.init(minecraft, width, height);
		minecraft.keyboardListener.enableRepeatEvents(true);
		channel1 = new CheckboxButton(this.guiLeft + 185, this.guiTop + 60, 20, 20, new StringTextComponent("Channel 1"), false);
		TestpaneldynGui.guistate.put("checkbox:channel1", channel1);
		this.addButton(channel1);
		channel2 = new CheckboxButton(this.guiLeft + 185, this.guiTop + 96, 20, 20, new StringTextComponent("Channel 2"), false);
		TestpaneldynGui.guistate.put("checkbox:channel2", channel2);
		this.addButton(channel2);
		channel3 = new CheckboxButton(this.guiLeft + 185, this.guiTop + 132, 20, 20, new StringTextComponent("Channel 3"), false);
		TestpaneldynGui.guistate.put("checkbox:channel3", channel3);
		this.addButton(channel3);
		soundid = new TextFieldWidget(this.font, this.guiLeft + 32, this.guiTop + 60, 120, 20, new StringTextComponent(""));
		guistate.put("text:soundid", soundid);
		soundid.setMaxStringLength(32767);
		this.children.add(this.soundid);
		this.addButton(new Button(this.guiLeft + 50, this.guiTop + 150, 72, 20, new StringTextComponent("Wiki"), e -> {
			if (true) {
				MoreScpAlarmMod.PACKET_HANDLER.sendToServer(new TestpaneldynGui.ButtonPressedMessage(0, x, y, z));
				TestpaneldynGui.handleButtonAction(entity, 0, x, y, z);
			}
		}));
	}
}
