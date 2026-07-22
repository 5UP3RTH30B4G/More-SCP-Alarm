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

import net.mcreator.scpalarm.world.inventory.ASelectorMenu;
import net.mcreator.scpalarm.network.ASelectorButtonMessage;
import net.mcreator.scpalarm.MoreScpAlarmMod;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class ASelectorScreen extends AbstractContainerScreen<ASelectorMenu> {
	private final static HashMap<String, Object> guistate = ASelectorMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox aselector;
	Button button_ok;
	Button button_go_on_var_mod;
	Button button_empty;
	Button button_empty1;

	public ASelectorScreen(ASelectorMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 160;
		this.imageHeight = 163;
	}

	private static final ResourceLocation texture = new ResourceLocation("more_scp_alarm:textures/screens/a_selector.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		aselector.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (aselector.isFocused())
			return aselector.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
		aselector.tick();
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.more_scp_alarm.a_selector.label_enter_the_id_of_the_alarm"), 16, 5, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.more_scp_alarm.a_selector.label_volume"), 64, 74, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.more_scp_alarm.a_selector.label_bnbtnumbervolume"), 78, 90, -1, false);
	}

	@Override
	public void init() {
		super.init();
		aselector = new EditBox(this.font, this.leftPos + 17, this.topPos + 24, 118, 18, Component.translatable("gui.more_scp_alarm.a_selector.aselector"));
		aselector.setMaxLength(32767);
		guistate.put("text:aselector", aselector);
		this.addWidget(this.aselector);
		button_ok = Button.builder(Component.translatable("gui.more_scp_alarm.a_selector.button_ok"), e -> {
			if (true) {
				MoreScpAlarmMod.PACKET_HANDLER.sendToServer(new ASelectorButtonMessage(0, x, y, z));
				ASelectorButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 61, this.topPos + 50, 35, 20).build();
		guistate.put("button:button_ok", button_ok);
		this.addRenderableWidget(button_ok);
		button_go_on_var_mod = Button.builder(Component.translatable("gui.more_scp_alarm.a_selector.button_go_on_var_mod"), e -> {
			if (true) {
				MoreScpAlarmMod.PACKET_HANDLER.sendToServer(new ASelectorButtonMessage(1, x, y, z));
				ASelectorButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 34, this.topPos + 122, 90, 20).build();
		guistate.put("button:button_go_on_var_mod", button_go_on_var_mod);
		this.addRenderableWidget(button_go_on_var_mod);
		button_empty = Button.builder(Component.translatable("gui.more_scp_alarm.a_selector.button_empty"), e -> {
			if (true) {
				MoreScpAlarmMod.PACKET_HANDLER.sendToServer(new ASelectorButtonMessage(2, x, y, z));
				ASelectorButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 106, this.topPos + 86, 30, 20).build();
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
		button_empty1 = Button.builder(Component.translatable("gui.more_scp_alarm.a_selector.button_empty1"), e -> {
			if (true) {
				MoreScpAlarmMod.PACKET_HANDLER.sendToServer(new ASelectorButtonMessage(3, x, y, z));
				ASelectorButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}).bounds(this.leftPos + 25, this.topPos + 86, 30, 20).build();
		guistate.put("button:button_empty1", button_empty1);
		this.addRenderableWidget(button_empty1);
	}
}
