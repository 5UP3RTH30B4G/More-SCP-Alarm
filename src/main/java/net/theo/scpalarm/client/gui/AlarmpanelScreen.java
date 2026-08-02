package net.theo.scpalarm.client.gui;

import net.theo.scpalarm.world.inventory.AlarmpanelMenu;
import net.theo.scpalarm.network.AlarmpanelButtonMessage;
import net.theo.scpalarm.MoreScpAlarmMod;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class AlarmpanelScreen extends AbstractContainerScreen<AlarmpanelMenu> {
	private final static HashMap<String, Object> guistate = AlarmpanelMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox panel_soundid;
	Checkbox panel_playsound;
	Checkbox channel1;
	Checkbox channel2;
	Checkbox channel3;
	Button button_wiki;
	Button button_save;

	public AlarmpanelScreen(AlarmpanelMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 300;
		this.imageHeight = 200;
	}

	private static final ResourceLocation texture = new ResourceLocation("more_scp_alarm:textures/screens/alarmpanel.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		panel_soundid.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (panel_soundid.isFocused())
			return panel_soundid.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
		panel_soundid.tick();
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String panel_soundidValue = panel_soundid.getValue();
		super.resize(minecraft, width, height);
		panel_soundid.setValue(panel_soundidValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.more_scp_alarm.alarmpanel.label_dynamic_alarm_panel"), 95, 6, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.more_scp_alarm.alarmpanel.label_enter_a_sound_id"), 41, 24, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.more_scp_alarm.alarmpanel.label_how_to_get_the_ids"), 32, 96, -16737793, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.more_scp_alarm.alarmpanel.label_go_to_the_wiki_of"), 41, 114, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.more_scp_alarm.alarmpanel.label_more_scp_alarm"), 50, 132, -65536, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.more_scp_alarm.alarmpanel.label_select_channel"), 194, 24, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		panel_soundid = new EditBox(this.font, this.leftPos + 24, this.topPos + 43, 118, 18, Component.translatable("gui.more_scp_alarm.alarmpanel.panel_soundid"));
		panel_soundid.setMaxLength(32767);
		guistate.put("text:panel_soundid", panel_soundid);
		this.addWidget(this.panel_soundid);
		button_wiki = Button.builder(Component.translatable("gui.more_scp_alarm.alarmpanel.button_wiki"), e -> {
			if (true) {
				MoreScpAlarmMod.PACKET_HANDLER.sendToServer(new AlarmpanelButtonMessage(0, x, y, z));
				AlarmpanelButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 59, this.topPos + 150, 46, 20).build();
		guistate.put("button:button_wiki", button_wiki);
		this.addRenderableWidget(button_wiki);
		button_save = Button.builder(Component.translatable("gui.more_scp_alarm.alarmpanel.button_save"), e -> {
			if (true) {
				MoreScpAlarmMod.PACKET_HANDLER.sendToServer(new AlarmpanelButtonMessage(1, x, y, z));
				AlarmpanelButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 239, this.topPos + 168, 46, 20).build();
		guistate.put("button:button_save", button_save);
		this.addRenderableWidget(button_save);
		panel_playsound = new Checkbox(this.leftPos + 23, this.topPos + 69, 20, 20, Component.translatable("gui.more_scp_alarm.alarmpanel.panel_playsound"), false);
		guistate.put("checkbox:panel_playsound", panel_playsound);
		this.addRenderableWidget(panel_playsound);
		channel1 = new Checkbox(this.leftPos + 185, this.topPos + 60, 20, 20, Component.translatable("gui.more_scp_alarm.alarmpanel.channel1"), false);
		guistate.put("checkbox:channel1", channel1);
		this.addRenderableWidget(channel1);
		channel2 = new Checkbox(this.leftPos + 185, this.topPos + 96, 20, 20, Component.translatable("gui.more_scp_alarm.alarmpanel.channel2"), false);
		guistate.put("checkbox:channel2", channel2);
		this.addRenderableWidget(channel2);
		channel3 = new Checkbox(this.leftPos + 185, this.topPos + 132, 20, 20, Component.translatable("gui.more_scp_alarm.alarmpanel.channel3"), false);
		guistate.put("checkbox:channel3", channel3);
		this.addRenderableWidget(channel3);
	}
}
