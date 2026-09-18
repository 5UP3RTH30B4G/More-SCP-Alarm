package net.theo.scpalarm.client.gui;

import net.theo.scpalarm.world.inventory.TVSelectorMenu;
import net.theo.scpalarm.network.TVSelectorButtonMessage;
import net.theo.scpalarm.MoreScpAlarmMod;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class TVSelectorScreen extends AbstractContainerScreen<TVSelectorMenu> {
	private final static HashMap<String, Object> guistate = TVSelectorMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	ImageButton imagebutton_lockdownscreen;
	ImageButton imagebutton_lockdownscreenlight;

	public TVSelectorScreen(TVSelectorMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 400;
		this.imageHeight = 165;
	}

	private static final ResourceLocation texture = new ResourceLocation("more_scp_alarm:textures/screens/tv_selector.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.more_scp_alarm.tv_selector.label_select_a_tv_screen_to_display"), 127, 6, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_lockdownscreen = new ImageButton(this.leftPos + 28, this.topPos + 24, 150, 113, 0, 0, 113, new ResourceLocation("more_scp_alarm:textures/screens/atlas/imagebutton_lockdownscreen.png"), 150, 226, e -> {
			if (true) {
				MoreScpAlarmMod.PACKET_HANDLER.sendToServer(new TVSelectorButtonMessage(0, x, y, z));
				TVSelectorButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		});
		guistate.put("button:imagebutton_lockdownscreen", imagebutton_lockdownscreen);
		this.addRenderableWidget(imagebutton_lockdownscreen);
		imagebutton_lockdownscreenlight = new ImageButton(this.leftPos + 226, this.topPos + 24, 150, 112, 0, 0, 112, new ResourceLocation("more_scp_alarm:textures/screens/atlas/imagebutton_lockdownscreenlight.png"), 150, 224, e -> {
			if (true) {
				MoreScpAlarmMod.PACKET_HANDLER.sendToServer(new TVSelectorButtonMessage(1, x, y, z));
				TVSelectorButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		});
		guistate.put("button:imagebutton_lockdownscreenlight", imagebutton_lockdownscreenlight);
		this.addRenderableWidget(imagebutton_lockdownscreenlight);
	}
}
