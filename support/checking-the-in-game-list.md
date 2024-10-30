---
description: >-
  This guide will show you how to access the list of sounds available in the
  More SCP Alarm mod so you can use them in commands or configurations.
---

# 📜 Checking the In-game list

## Requirements

1. **Minecraft** (Java Edition) with the _More SCP Alarm_ mod installed.
2. Access to the game console or command terminal.

#### Steps

**1. Open Minecraft and Start Your World (or a Server with the Mod Installed)**

Make sure the _More SCP Alarm_ mod is loaded in your instance of Minecraft. You can verify this in the **Mods** list on the main menu.

**2. Use the `/playsound` Command to Find Available Sounds**

In Minecraft, the `/playsound` command allows you to access sounds by their identifier. Here’s how to do it:

* Open the command console by pressing `T` (or `/` directly).
*   Type the following command to play a sound from the _More SCP Alarm_ mod:

    ```plaintext
    /playsound more_scp_alarm:sound_name @p
    ```

    Replace `sound_name` with the specific sound name you want to try.

**Tip**: You can try auto-completing the sound names by typing the first few letters after `more_scp_alarm:` and pressing `Tab`. This is very usefull to find the list!

**3. Check the `sounds.json` File for a Full List of Sounds (Optional)**

If you’d like a complete list of all sounds included in the mod, you can find it in the mod’s files:

1. Go to your `.minecraft` folder.
2. Open the `mods` folder and find the `more_scp_alarm.jar` file.
3. Extract the `.jar` file using a tool like WinRAR or 7-Zip.
4. Navigate to the extracted folder and locate `assets/more_scp_alarm/sounds.json`.

The `sounds.json` file contains all the available sound identifiers in the mod, listed as paths like `more_scp_alarm:sound_name`.

**4. Testing a Sound**

To test a sound, return to Minecraft and use the `/playsound` command with one of the identifiers from the list. For example:

```plaintext
/playsound more_scp_alarm:alarm-1 @p
```

This will play the `alarm-1` sound near your character.

{% hint style="info" %}
If a sound doesn’t work, double-check the identifier and make sure the mod is loaded.
{% endhint %}
