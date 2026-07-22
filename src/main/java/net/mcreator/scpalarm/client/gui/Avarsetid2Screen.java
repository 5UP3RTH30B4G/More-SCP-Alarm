package net.mcreator.scpalarm.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.scpalarm.world.inventory.Avarsetid2Menu;
import net.mcreator.scpalarm.network.Avarsetid2ButtonMessage;
import net.mcreator.scpalarm.MoreScpAlarmMod;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class Avarsetid2Screen extends AbstractContainerScreen<Avarsetid2Menu> {
	private final static HashMap<String, Object> guistate = Avarsetid2Menu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox avaralarm2;
	Button button_ok;

	public Avarsetid2Screen(Avarsetid2Menu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 160;
		this.imageHeight = 163;
	}

	private static final ResourceLocation texture = new ResourceLocation("more_scp_alarm:textures/screens/avarsetid_2.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		avaralarm2.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (avaralarm2.isFocused())
			return avaralarm2.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
		avaralarm2.tick();
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.more_scp_alarm.avarsetid_2.label_enter_the_id_of_the_alarm"), 16, 5, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		avaralarm2 = new EditBox(this.font, this.leftPos + 17, this.topPos + 60, 118, 18, Component.translatable("gui.more_scp_alarm.avarsetid_2.avaralarm2"));
		avaralarm2.setMaxLength(32767);
		guistate.put("text:avaralarm2", avaralarm2);
		this.addWidget(this.avaralarm2);
		button_ok = Button.builder(Component.translatable("gui.more_scp_alarm.avarsetid_2.button_ok"), e -> {
			if (true) {
				MoreScpAlarmMod.PACKET_HANDLER.sendToServer(new Avarsetid2ButtonMessage(0, x, y, z));
				Avarsetid2ButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 61, this.topPos + 131, 35, 20).build();
		guistate.put("button:button_ok", button_ok);
		this.addRenderableWidget(button_ok);
	}
}
