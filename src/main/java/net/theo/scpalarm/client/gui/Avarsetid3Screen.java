package net.theo.scpalarm.client.gui;

import net.theo.scpalarm.world.inventory.Avarsetid3Menu;
import net.theo.scpalarm.network.Avarsetid3ButtonMessage;
import net.theo.scpalarm.MoreScpAlarmMod;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class Avarsetid3Screen extends AbstractContainerScreen<Avarsetid3Menu> {
	private final static HashMap<String, Object> guistate = Avarsetid3Menu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox avaralarm3;
	Button button_ok;

	public Avarsetid3Screen(Avarsetid3Menu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 160;
		this.imageHeight = 163;
	}

	private static final ResourceLocation texture = new ResourceLocation("more_scp_alarm:textures/screens/avarsetid_3.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		avaralarm3.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		if (avaralarm3.isFocused())
			return avaralarm3.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
		avaralarm3.tick();
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.more_scp_alarm.avarsetid_3.label_enter_the_id_of_the_alarm"), 16, 5, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		avaralarm3 = new EditBox(this.font, this.leftPos + 17, this.topPos + 60, 118, 18, Component.translatable("gui.more_scp_alarm.avarsetid_3.avaralarm3"));
		avaralarm3.setMaxLength(32767);
		guistate.put("text:avaralarm3", avaralarm3);
		this.addWidget(this.avaralarm3);
		button_ok = Button.builder(Component.translatable("gui.more_scp_alarm.avarsetid_3.button_ok"), e -> {
			if (true) {
				MoreScpAlarmMod.PACKET_HANDLER.sendToServer(new Avarsetid3ButtonMessage(0, x, y, z));
				Avarsetid3ButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 61, this.topPos + 131, 35, 20).build();
		guistate.put("button:button_ok", button_ok);
		this.addRenderableWidget(button_ok);
	}
}
