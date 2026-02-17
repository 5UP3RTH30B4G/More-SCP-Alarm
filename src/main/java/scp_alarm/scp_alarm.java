package scp_alarm;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("scp_alarm")
public class scp_alarm {
    public static final Logger LOGGER = LogManager.getLogger();

    public scp_alarm() {
        LOGGER.info("More SCP Alarm mod loaded!");
    }
}
